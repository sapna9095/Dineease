package com.dineease.dao;

import com.dineease.db.DBConnection;
import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesData;
import com.dineease.model.SalesPoint;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardDAO {

    public DashboardStats getDashboardStats() {
        int totalUsers = 0;
        int usersThisMonth = 0;
        int totalMenuItems = 0;
        int menuItemsThisMonth = 0;
        int totalInventoryItems = 0;
        int inventoryItemsThisMonth = 0;
        int lowStockCount = 0;
        int lowStockThisWeek = 0;
        int todayOrdersCount = 0;
        int ordersDiffVsYesterday = 0;
        double todayRevenue = 0.0;
        double revenuePercentVsYesterday = 0.0;

        int availableTables = 0;
        int occupiedTables = 0;
        int reservedTables = 0;
        int totalTables = 0;

        Map<String, Integer> statusCounts = new LinkedHashMap<>();
        statusCounts.put("PENDING", 0);
        statusCounts.put("PREPARING", 0);
        statusCounts.put("READY", 0);
        statusCounts.put("SERVED", 0);
        statusCounts.put("CANCELLED", 0);

        try (Connection conn = DBConnection.getConnection()) {
            // 1. Total users
            totalUsers = querySingleInt(conn, "SELECT COUNT(*) FROM users");
            usersThisMonth = querySingleInt(conn,
                    "SELECT COUNT(*) FROM users WHERE created_at >= DATE_FORMAT(CURDATE(), '%Y-%m-01 00:00:00')");

            // 2. Menu items
            totalMenuItems = querySingleInt(conn, "SELECT COUNT(*) FROM menu_items");
            menuItemsThisMonth = querySingleInt(conn,
                    "SELECT COUNT(*) FROM menu_items WHERE created_at >= DATE_FORMAT(CURDATE(), '%Y-%m-01 00:00:00')");

            // 3. Inventory items
            totalInventoryItems = querySingleInt(conn, "SELECT COUNT(*) FROM inventory_items");
            inventoryItemsThisMonth = querySingleInt(conn,
                    "SELECT COUNT(*) FROM inventory_items WHERE created_at >= DATE_FORMAT(CURDATE(), '%Y-%m-01 00:00:00')");

            // 4. Low stock
            lowStockCount = querySingleInt(conn,
                    "SELECT COUNT(*) FROM inventory_items WHERE quantity <= reorder_level");
            int lowStockCreatedThisWeek = querySingleInt(conn,
                    "SELECT COUNT(*) FROM inventory_items WHERE quantity <= reorder_level AND created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)");
            // Subtext e.g. "-1 this week" or "+0 this week"
            lowStockThisWeek = lowStockCreatedThisWeek;

            // 5. Today's orders & diff vs yesterday
            todayOrdersCount = querySingleInt(conn,
                    "SELECT COUNT(*) FROM orders WHERE created_at >= CURDATE()");
            int yesterdayOrders = querySingleInt(conn,
                    "SELECT COUNT(*) FROM orders WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND created_at < CURDATE()");
            ordersDiffVsYesterday = todayOrdersCount - yesterdayOrders;

            // 6. Today's revenue & % vs yesterday
            todayRevenue = querySingleDouble(conn,
                    "SELECT COALESCE(SUM(total_amount), 0) FROM bills WHERE paid_at >= CURDATE() AND (payment_status = 'PAID' OR payment_status = 'Completed' OR payment_status = 'Paid')");
            double yesterdayRevenue = querySingleDouble(conn,
                    "SELECT COALESCE(SUM(total_amount), 0) FROM bills WHERE paid_at >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND paid_at < CURDATE() AND (payment_status = 'PAID' OR payment_status = 'Completed' OR payment_status = 'Paid')");

            if (yesterdayRevenue > 0) {
                revenuePercentVsYesterday = ((todayRevenue - yesterdayRevenue) / yesterdayRevenue) * 100.0;
            } else if (todayRevenue > 0) {
                revenuePercentVsYesterday = 100.0;
            } else {
                revenuePercentVsYesterday = 0.0;
            }

            // Tables
            totalTables = querySingleInt(conn, "SELECT COUNT(*) FROM restaurant_tables");
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT status, COUNT(*) as c FROM restaurant_tables GROUP BY status");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String st = rs.getString("status");
                    int count = rs.getInt("c");
                    if ("AVAILABLE".equalsIgnoreCase(st)) availableTables = count;
                    else if ("OCCUPIED".equalsIgnoreCase(st)) occupiedTables = count;
                    else if ("RESERVED".equalsIgnoreCase(st)) reservedTables = count;
                }
            }

            // Order statuses today
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT status, COUNT(*) as c FROM orders WHERE created_at >= CURDATE() GROUP BY status");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String st = rs.getString("status");
                    if (st != null) {
                        statusCounts.put(st.toUpperCase(), rs.getInt("c"));
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching dashboard stats: " + e.getMessage());
        }

        return new DashboardStats(
                totalUsers, usersThisMonth,
                totalMenuItems, menuItemsThisMonth,
                totalInventoryItems, inventoryItemsThisMonth,
                lowStockCount, lowStockThisWeek,
                todayOrdersCount, ordersDiffVsYesterday,
                todayRevenue, revenuePercentVsYesterday,
                availableTables, occupiedTables, reservedTables, totalTables,
                statusCounts
        );
    }

    public SalesData getSalesData(String period) {
        int days = 7;
        if ("Today".equalsIgnoreCase(period)) {
            days = 1;
        } else if ("Last 7 Days".equalsIgnoreCase(period)) {
            days = 7;
        } else if ("Last 30 Days".equalsIgnoreCase(period)) {
            days = 30;
        } else if ("This Month".equalsIgnoreCase(period)) {
            days = LocalDate.now().getDayOfMonth();
            if (days < 1) days = 1;
        }

        List<SalesPoint> points = new ArrayList<>();
        double totalSales = 0.0;
        double previousPeriodSales = 0.0;

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(days - 1);
        LocalDate prevStartDate = startDate.minusDays(days);
        LocalDate prevEndDate = startDate.minusDays(1);

        Map<LocalDate, Double> dailyMap = new HashMap<>();

        try (Connection conn = DBConnection.getConnection()) {
            // Current period sales daily
            String sql = "SELECT DATE(paid_at) as dt, COALESCE(SUM(total_amount), 0) as sm " +
                    "FROM bills " +
                    "WHERE paid_at >= ? AND paid_at <= ? AND (payment_status = 'PAID' OR payment_status = 'Paid' OR payment_status = 'Completed') " +
                    "GROUP BY DATE(paid_at)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, startDate.toString() + " 00:00:00");
                ps.setString(2, today.toString() + " 23:59:59");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        java.sql.Date d = rs.getDate("dt");
                        if (d != null) {
                            dailyMap.put(d.toLocalDate(), rs.getDouble("sm"));
                        }
                    }
                }
            }

            // Previous equal period total
            String prevSql = "SELECT COALESCE(SUM(total_amount), 0) " +
                    "FROM bills " +
                    "WHERE paid_at >= ? AND paid_at <= ? AND (payment_status = 'PAID' OR payment_status = 'Paid' OR payment_status = 'Completed')";
            try (PreparedStatement ps = conn.prepareStatement(prevSql)) {
                ps.setString(1, prevStartDate.toString() + " 00:00:00");
                ps.setString(2, prevEndDate.toString() + " 23:59:59");
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        previousPeriodSales = rs.getDouble(1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching sales data: " + e.getMessage());
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMM");
        for (int i = 0; i < days; i++) {
            LocalDate d = startDate.plusDays(i);
            double val = dailyMap.getOrDefault(d, 0.0);
            totalSales += val;
            points.add(new SalesPoint(d, d.format(fmt), val));
        }

        double percentChange = 0.0;
        if (previousPeriodSales > 0) {
            percentChange = ((totalSales - previousPeriodSales) / previousPeriodSales) * 100.0;
        } else if (totalSales > 0) {
            percentChange = 100.0;
        }

        return new SalesData(period, totalSales, percentChange, points);
    }

    public List<OrderStatusCount> getOrderStatuses() {
        List<OrderStatusCount> list = new ArrayList<>();
        Map<String, Integer> counts = new LinkedHashMap<>();
        counts.put("PENDING", 0);
        counts.put("PREPARING", 0);
        counts.put("READY", 0);
        counts.put("SERVED", 0);
        counts.put("CANCELLED", 0);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT status, COUNT(*) as c FROM orders WHERE created_at >= CURDATE() GROUP BY status");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String st = rs.getString("status");
                if (st != null) {
                    counts.put(st.toUpperCase(), rs.getInt("c"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching order statuses: " + e.getMessage());
        }

        list.add(new OrderStatusCount("Pending", counts.getOrDefault("PENDING", 0), new Color(237, 170, 58)));
        list.add(new OrderStatusCount("Preparing", counts.getOrDefault("PREPARING", 0), new Color(76, 141, 246)));
        list.add(new OrderStatusCount("Ready", counts.getOrDefault("READY", 0), new Color(46, 158, 91)));
        list.add(new OrderStatusCount("Served", counts.getOrDefault("SERVED", 0), new Color(124, 77, 190)));
        list.add(new OrderStatusCount("Cancelled", counts.getOrDefault("CANCELLED", 0), new Color(224, 79, 74)));

        return list;
    }

    public List<OrderRow> getRecentOrders() {
        List<OrderRow> list = new ArrayList<>();
        String sql = "SELECT o.id, o.status, o.total_amount, o.created_at, t.table_number " +
                "FROM orders o " +
                "LEFT JOIN restaurant_tables t ON o.table_id = t.id " +
                "ORDER BY o.created_at DESC, o.id DESC LIMIT 5";
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderIdText = "#" + id;
                int tableNum = rs.getInt("table_number");
                String tableText = tableNum > 0 ? ("Table " + tableNum) : "Table -";
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime ldt = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
                String timeText = ldt.format(timeFmt);
                double amt = rs.getDouble("total_amount");
                String rawStatus = rs.getString("status");
                String status = formatStatusTitle(rawStatus);

                list.add(new OrderRow(id, orderIdText, tableText, timeText, amt, status, ldt));
            }
        } catch (Exception e) {
            System.err.println("Error fetching recent orders: " + e.getMessage());
        }

        return list;
    }

    private static String formatStatusTitle(String raw) {
        if (raw == null) return "Pending";
        String lower = raw.trim().toLowerCase();
        if (lower.isEmpty()) return "Pending";
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private static int querySingleInt(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            // table may not exist yet or empty
        }
        return 0;
    }

    private static double querySingleDouble(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            // ignore
        }
        return 0.0;
    }
}
