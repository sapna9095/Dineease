package com.dineease.view.admin.reports;

import com.dineease.db.DBConnection;
import com.dineease.ui.IconFactory;
import com.dineease.ui.components.RoundedPanel;
import com.dineease.util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportsPanel extends JPanel {

    private static final Color BG_CREAM = new Color(247, 241, 229);
    private static final Color GOLD = new Color(200, 144, 46);
    private static final Color TEXT_DARK = new Color(43, 27, 18);
    private static final Color TEXT_MUTED = new Color(110, 95, 80);

    private final JLabel totalRevenueLabel;
    private final JLabel totalOrdersLabel;
    private final JLabel totalUsersLabel;
    private final JLabel totalMenuItemsLabel;
    private final JLabel totalInventoryLabel;
    private final JLabel lowStockLabel;

    private final DefaultTableModel dailySalesModel;
    private final DefaultTableModel topItemsModel;
    private final JComboBox<String> filterCombo;

    public ReportsPanel() {
        setBackground(BG_CREAM);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(26, 32, 26, 32));

        // =========================
        // HEADER
        // =========================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Analytics & Reports");
        title.setFont(FontUtil.getPlayfairDisplay(28f, Font.BOLD));
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Comprehensive business metrics and sales breakdown from MySQL");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        controls.setOpaque(false);

        filterCombo = new JComboBox<>(new String[]{"All Time", "Last 30 Days", "This Month", "Last 7 Days"});
        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterCombo.setBackground(Color.WHITE);
        filterCombo.setFocusable(false);
        filterCombo.addActionListener(e -> loadReportData());

        JButton refreshBtn = new JButton("Refresh Reports");
        refreshBtn.setIcon(IconFactory.refresh(15, Color.WHITE));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(GOLD);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        refreshBtn.addActionListener(e -> loadReportData());

        controls.add(new JLabel("Range:"));
        controls.add(filterCombo);
        controls.add(refreshBtn);

        headerPanel.add(titleBlock, BorderLayout.WEST);
        headerPanel.add(controls, BorderLayout.EAST);
        root.add(headerPanel);
        root.add(Box.createVerticalStrut(20));

        // =========================
        // 6 REPORT STAT CARDS
        // =========================
        JPanel cardsGrid = new JPanel(new GridLayout(1, 6, 14, 0));
        cardsGrid.setOpaque(false);

        totalRevenueLabel = new JLabel("₹ 0");
        totalOrdersLabel = new JLabel("0");
        totalUsersLabel = new JLabel("0");
        totalMenuItemsLabel = new JLabel("0");
        totalInventoryLabel = new JLabel("0");
        lowStockLabel = new JLabel("0");

        cardsGrid.add(createMiniCard("Total Revenue", totalRevenueLabel, IconFactory.creditCard(18, new Color(80, 60, 45)), new Color(241, 224, 190)));
        cardsGrid.add(createMiniCard("Total Orders", totalOrdersLabel, IconFactory.clipboard(18, new Color(80, 60, 45)), new Color(241, 224, 190)));
        cardsGrid.add(createMiniCard("Total Users", totalUsersLabel, IconFactory.user(18, new Color(80, 60, 45)), new Color(241, 224, 190)));
        cardsGrid.add(createMiniCard("Menu Items", totalMenuItemsLabel, IconFactory.utensils(18, new Color(80, 60, 45)), new Color(241, 224, 190)));
        cardsGrid.add(createMiniCard("Inventory", totalInventoryLabel, IconFactory.cube(18, new Color(80, 60, 45)), new Color(241, 224, 190)));
        cardsGrid.add(createMiniCard("Low Stock", lowStockLabel, IconFactory.alert(18, new Color(190, 80, 50)), new Color(252, 228, 222)));

        root.add(cardsGrid);
        root.add(Box.createVerticalStrut(22));

        // =========================
        // 2 DATA TABLES: Daily Breakdown & Top Selling Items
        // =========================
        JPanel tablesRow = new JPanel(new GridLayout(1, 2, 18, 0));
        tablesRow.setOpaque(false);

        // 1. Daily Sales Breakdown
        dailySalesModel = new DefaultTableModel(new String[]{"Date", "Orders Count", "Total Revenue (₹)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable dailyTable = createStyledTable(dailySalesModel);
        RoundedPanel dailyPanel = new RoundedPanel(14, new Color(251, 246, 234));
        dailyPanel.setLayout(new BorderLayout());
        dailyPanel.setBorder(new EmptyBorder(18, 20, 16, 20));

        JPanel dHead = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        dHead.setOpaque(false);
        dHead.add(new JLabel(IconFactory.barChart(18, GOLD)));
        JLabel dTitle = new JLabel("Daily Sales Performance");
        dTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dTitle.setForeground(TEXT_DARK);
        dHead.add(dTitle);
        dailyPanel.add(dHead, BorderLayout.NORTH);

        JScrollPane dScroll = new JScrollPane(dailyTable);
        dScroll.setOpaque(false);
        dScroll.getViewport().setOpaque(false);
        dScroll.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        dailyPanel.add(dScroll, BorderLayout.CENTER);

        // 2. Top Menu Categories / Items Breakdown
        topItemsModel = new DefaultTableModel(new String[]{"Category", "Available Items", "Price Range"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable topTable = createStyledTable(topItemsModel);
        RoundedPanel topPanel = new RoundedPanel(14, new Color(251, 246, 234));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(18, 20, 16, 20));

        JPanel tHead = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tHead.setOpaque(false);
        tHead.add(new JLabel(IconFactory.utensils(18, GOLD)));
        JLabel tTitle = new JLabel("Menu Category Distribution");
        tTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tTitle.setForeground(TEXT_DARK);
        tHead.add(tTitle);
        topPanel.add(tHead, BorderLayout.NORTH);

        JScrollPane tScroll = new JScrollPane(topTable);
        tScroll.setOpaque(false);
        tScroll.getViewport().setOpaque(false);
        tScroll.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        topPanel.add(tScroll, BorderLayout.CENTER);

        tablesRow.add(dailyPanel);
        tablesRow.add(topPanel);
        root.add(tablesRow);

        JScrollPane mainScroll = new JScrollPane(root);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);

        // Load data on EDT via background worker
        loadReportData();
    }

    private JPanel createMiniCard(String title, JLabel valueLabel, Icon icon, Color iconBg) {
        RoundedPanel card = new RoundedPanel(14, new Color(251, 246, 234));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);

        JLabel iconLbl = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(iconBg);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconLbl.setPreferredSize(new Dimension(34, 34));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(iconLbl);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLbl.setForeground(TEXT_MUTED);
        top.add(titleLbl);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(TEXT_DARK);
        valueLabel.setBorder(new EmptyBorder(8, 4, 0, 0));

        card.add(top, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(236, 228, 218));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(238, 226, 206));
        table.setSelectionForeground(TEXT_DARK);

        table.getTableHeader().setPreferredSize(new Dimension(0, 32));
        table.getTableHeader().setBackground(new Color(245, 238, 224));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(new Color(90, 75, 60));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, center);

        return table;
    }

    public void loadReportData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            int users = 0;
            int menu = 0;
            int inventory = 0;
            int lowStock = 0;
            int orders = 0;
            double revenue = 0.0;

            final java.util.List<Object[]> dailyRows = new java.util.ArrayList<>();
            final java.util.List<Object[]> categoryRows = new java.util.ArrayList<>();

            @Override
            protected Void doInBackground() {
                try (Connection conn = DBConnection.getConnection()) {
                    // Counts
                    users = getSingleInt(conn, "SELECT COUNT(*) FROM users");
                    menu = getSingleInt(conn, "SELECT COUNT(*) FROM menu_items");
                    inventory = getSingleInt(conn, "SELECT COUNT(*) FROM inventory_items");
                    lowStock = getSingleInt(conn, "SELECT COUNT(*) FROM inventory_items WHERE quantity <= reorder_level");
                    orders = getSingleInt(conn, "SELECT COUNT(*) FROM orders");
                    revenue = getSingleDouble(conn, "SELECT COALESCE(SUM(total_amount), 0) FROM bills WHERE payment_status = 'PAID' OR payment_status = 'Paid'");

                    // Daily breakdown
                    String dailySql = "SELECT DATE(paid_at) as dt, COUNT(*) as cnt, COALESCE(SUM(total_amount), 0) as sm " +
                            "FROM bills WHERE payment_status = 'PAID' OR payment_status = 'Paid' " +
                            "GROUP BY DATE(paid_at) ORDER BY dt DESC LIMIT 10";
                    try (PreparedStatement ps = conn.prepareStatement(dailySql);
                         ResultSet rs = ps.executeQuery()) {
                        DecimalFormat df = new DecimalFormat("#,##,##0.00");
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
                        while (rs.next()) {
                            java.sql.Date d = rs.getDate("dt");
                            String dateStr = d != null ? d.toLocalDate().format(fmt) : "-";
                            int cnt = rs.getInt("cnt");
                            double sm = rs.getDouble("sm");
                            dailyRows.add(new Object[]{dateStr, cnt, "₹ " + df.format(sm)});
                        }
                    }

                    // Category distribution
                    String catSql = "SELECT category, COUNT(*) as cnt, MIN(price) as min_p, MAX(price) as max_p " +
                            "FROM menu_items GROUP BY category ORDER BY cnt DESC";
                    try (PreparedStatement ps = conn.prepareStatement(catSql);
                         ResultSet rs = ps.executeQuery()) {
                        DecimalFormat df = new DecimalFormat("#,##0");
                        while (rs.next()) {
                            String cat = rs.getString("category");
                            int cnt = rs.getInt("cnt");
                            double minP = rs.getDouble("min_p");
                            double maxP = rs.getDouble("max_p");
                            categoryRows.add(new Object[]{cat, cnt, "₹ " + df.format(minP) + " - ₹ " + df.format(maxP)});
                        }
                    }

                } catch (Exception e) {
                    System.err.println("Reports load error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                DecimalFormat df = new DecimalFormat("#,##,##0");
                totalUsersLabel.setText(String.valueOf(users));
                totalMenuItemsLabel.setText(String.valueOf(menu));
                totalInventoryLabel.setText(String.valueOf(inventory));
                lowStockLabel.setText(String.valueOf(lowStock));
                totalOrdersLabel.setText(String.valueOf(orders));
                totalRevenueLabel.setText("₹ " + df.format(revenue));

                dailySalesModel.setRowCount(0);
                for (Object[] r : dailyRows) {
                    dailySalesModel.addRow(r);
                }

                topItemsModel.setRowCount(0);
                for (Object[] r : categoryRows) {
                    topItemsModel.addRow(r);
                }
            }
        };
        worker.execute();
    }

    private int getSingleInt(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception ignored) {}
        return 0;
    }

    private double getSingleDouble(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception ignored) {}
        return 0.0;
    }
}
