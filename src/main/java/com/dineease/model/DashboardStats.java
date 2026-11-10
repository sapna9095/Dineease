package com.dineease.model;

import java.util.Map;

public class DashboardStats {
    // Stat Card 1: Total Users
    private final int totalUsers;
    private final int usersThisMonth;

    // Stat Card 2: Menu Items
    private final int totalMenuItems;
    private final int menuItemsThisMonth;

    // Stat Card 3: Inventory Items
    private final int totalInventoryItems;
    private final int inventoryItemsThisMonth;

    // Stat Card 4: Low Stock
    private final int lowStockCount;
    private final int lowStockThisWeek; // change vs last week

    // Stat Card 5: Today's Orders
    private final int todayOrdersCount;
    private final int ordersDiffVsYesterday; // difference vs yesterday's count

    // Stat Card 6: Today's Revenue
    private final double todayRevenue;
    private final double revenuePercentVsYesterday; // % change vs yesterday

    // Table Status
    private final int availableTables;
    private final int occupiedTables;
    private final int reservedTables;
    private final int totalTables;

    // Order Status counts today
    private final Map<String, Integer> orderStatusCounts;

    public DashboardStats(
            int totalUsers, int usersThisMonth,
            int totalMenuItems, int menuItemsThisMonth,
            int totalInventoryItems, int inventoryItemsThisMonth,
            int lowStockCount, int lowStockThisWeek,
            int todayOrdersCount, int ordersDiffVsYesterday,
            double todayRevenue, double revenuePercentVsYesterday,
            int availableTables, int occupiedTables, int reservedTables, int totalTables,
            Map<String, Integer> orderStatusCounts) {
        this.totalUsers = totalUsers;
        this.usersThisMonth = usersThisMonth;
        this.totalMenuItems = totalMenuItems;
        this.menuItemsThisMonth = menuItemsThisMonth;
        this.totalInventoryItems = totalInventoryItems;
        this.inventoryItemsThisMonth = inventoryItemsThisMonth;
        this.lowStockCount = lowStockCount;
        this.lowStockThisWeek = lowStockThisWeek;
        this.todayOrdersCount = todayOrdersCount;
        this.ordersDiffVsYesterday = ordersDiffVsYesterday;
        this.todayRevenue = todayRevenue;
        this.revenuePercentVsYesterday = revenuePercentVsYesterday;
        this.availableTables = availableTables;
        this.occupiedTables = occupiedTables;
        this.reservedTables = reservedTables;
        this.totalTables = totalTables;
        this.orderStatusCounts = orderStatusCounts;
    }

    public static DashboardStats empty() {
        return new DashboardStats(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0, 0, 0, 0, Map.of(
                "PENDING", 0,
                "PREPARING", 0,
                "READY", 0,
                "SERVED", 0,
                "CANCELLED", 0
        ));
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public int getUsersThisMonth() {
        return usersThisMonth;
    }

    public int getTotalMenuItems() {
        return totalMenuItems;
    }

    public int getMenuItemsThisMonth() {
        return menuItemsThisMonth;
    }

    public int getTotalInventoryItems() {
        return totalInventoryItems;
    }

    public int getInventoryItemsThisMonth() {
        return inventoryItemsThisMonth;
    }

    public int getLowStockCount() {
        return lowStockCount;
    }

    public int getLowStockThisWeek() {
        return lowStockThisWeek;
    }

    public int getTodayOrdersCount() {
        return todayOrdersCount;
    }

    public int getOrdersDiffVsYesterday() {
        return ordersDiffVsYesterday;
    }

    public double getTodayRevenue() {
        return todayRevenue;
    }

    public double getRevenuePercentVsYesterday() {
        return revenuePercentVsYesterday;
    }

    public int getAvailableTables() {
        return availableTables;
    }

    public int getOccupiedTables() {
        return occupiedTables;
    }

    public int getReservedTables() {
        return reservedTables;
    }

    public int getTotalTables() {
        return totalTables;
    }

    public Map<String, Integer> getOrderStatusCounts() {
        return orderStatusCounts;
    }

    public int getStatusCount(String status) {
        if (orderStatusCounts == null) return 0;
        return orderStatusCounts.getOrDefault(status.toUpperCase(), 0);
    }
}
