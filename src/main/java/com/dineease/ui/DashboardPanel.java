package com.dineease.ui;

import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesData;
import com.dineease.service.DashboardService;
import com.dineease.service.DashboardServiceImpl;
import com.dineease.ui.components.DonutChartPanel;
import com.dineease.ui.components.RecentOrdersTable;
import com.dineease.ui.components.SalesChartPanel;
import com.dineease.ui.components.StatCard;
import com.dineease.ui.components.TableStatusPanel;
import com.dineease.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class DashboardPanel extends JPanel {
    private static final Color MAIN_BG = new Color(247, 241, 229); // Cream background
    private static final Color GOLD = new Color(200, 144, 46);

    private final DashboardService dashboardService = new DashboardServiceImpl();

    // Header components
    private final JButton refreshBtn;
    private final JLabel clockDateLabel;
    private final JLabel clockTimeLabel;
    private final JLabel errorBanner;

    // Stat cards
    private final StatCard totalUsersCard;
    private final StatCard menuItemsCard;
    private final StatCard inventoryItemsCard;
    private final StatCard lowStockCard;
    private final StatCard todaysOrdersCard;
    private final StatCard todaysRevenueCard;

    // Chart & Table components
    private final SalesChartPanel salesChartPanel;
    private final DonutChartPanel donutChartPanel;
    private final TableStatusPanel tableStatusPanel;
    private final RecentOrdersTable recentOrdersTable;

    private String currentSalesPeriod = "Last 7 Days";
    private Timer autoRefreshTimer;
    private Timer liveClockTimer;
    private Consumer<String> onNavigate;

    public DashboardPanel() {
        setBackground(MAIN_BG);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(22, 28, 20, 28));

        // 1. Error Banner (Hidden when OK)
        errorBanner = new JLabel(" Database connection error. Displaying last loaded data.");
        errorBanner.setOpaque(true);
        errorBanner.setBackground(new Color(250, 222, 221));
        errorBanner.setForeground(new Color(180, 45, 40));
        errorBanner.setFont(new Font("Segoe UI", Font.BOLD, 12));
        errorBanner.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        errorBanner.setVisible(false);

        // 2. Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Left header: "Welcome to DineEase AI" & "Restaurant Management Dashboard"
        JPanel leftHeader = new JPanel();
        leftHeader.setOpaque(false);
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to DineEase AI");
        title.setFont(FontUtil.getPlayfairDisplay(30f, Font.BOLD));
        title.setForeground(new Color(43, 27, 18));

        JLabel subtitle = new JLabel("Restaurant Management Dashboard");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(115, 98, 82));

        leftHeader.add(title);
        leftHeader.add(Box.createVerticalStrut(4));
        leftHeader.add(subtitle);

        // Right header: Gold Refresh button & Live calendar/clock
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        rightHeader.setOpaque(false);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setIcon(IconFactory.refresh(15, Color.WHITE));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(GOLD);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        refreshBtn.addActionListener(e -> reloadAllData(true));

        JPanel clockBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        clockBox.setOpaque(false);
        JLabel calIcon = new JLabel(IconFactory.calendar(18, new Color(197, 139, 43)));

        JPanel timeBox = new JPanel();
        timeBox.setOpaque(false);
        timeBox.setLayout(new BoxLayout(timeBox, BoxLayout.Y_AXIS));

        clockDateLabel = new JLabel("Monday, 8 Sep 2025");
        clockDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        clockDateLabel.setForeground(new Color(50, 36, 27));

        clockTimeLabel = new JLabel("10:24 AM");
        clockTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clockTimeLabel.setForeground(new Color(112, 89, 66));

        timeBox.add(clockDateLabel);
        timeBox.add(clockTimeLabel);

        clockBox.add(calIcon);
        clockBox.add(timeBox);

        rightHeader.add(refreshBtn);
        rightHeader.add(clockBox);

        headerPanel.add(leftHeader, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        // 3. 6 Stat Cards Grid
        JPanel statCardsGrid = new JPanel(new GridLayout(1, 6, 14, 0));
        statCardsGrid.setOpaque(false);
        statCardsGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 18, 0));

        totalUsersCard = new StatCard("Total Users", "0", "+0 this month",
                IconFactory.user(18, new Color(80, 60, 45)), new Color(241, 224, 190));
        menuItemsCard = new StatCard("Menu Items", "0", "+0 this month",
                IconFactory.utensils(18, new Color(80, 60, 45)), new Color(241, 224, 190));
        inventoryItemsCard = new StatCard("Inventory Items", "0", "+0 this month",
                IconFactory.cube(18, new Color(80, 60, 45)), new Color(241, 224, 190));
        lowStockCard = new StatCard("Low Stock", "0", "-0 this week",
                IconFactory.alert(18, new Color(190, 80, 50)), new Color(252, 228, 222));
        todaysOrdersCard = new StatCard("Today's Orders", "0", "+0 vs yesterday",
                IconFactory.clipboard(18, new Color(80, 60, 45)), new Color(241, 224, 190));
        todaysRevenueCard = new StatCard("Today's Revenue", "₹ 0", "+0% vs yesterday",
                IconFactory.creditCard(18, new Color(80, 60, 45)), new Color(241, 224, 190));

        statCardsGrid.add(totalUsersCard);
        statCardsGrid.add(menuItemsCard);
        statCardsGrid.add(inventoryItemsCard);
        statCardsGrid.add(lowStockCard);
        statCardsGrid.add(todaysOrdersCard);
        statCardsGrid.add(todaysRevenueCard);

        // 4. Middle Row: Sales Overview (Left) + Order Status (Right)
        JPanel middleRow = new JPanel(new GridLayout(1, 2, 18, 0));
        middleRow.setOpaque(false);
        middleRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        salesChartPanel = new SalesChartPanel();
        salesChartPanel.setOnRangeChanged(period -> {
            this.currentSalesPeriod = period;
            reloadSalesData();
        });

        donutChartPanel = new DonutChartPanel();

        middleRow.add(salesChartPanel);
        middleRow.add(donutChartPanel);

        // 5. Lower Row: Table Status (Left) + Recent Orders (Right)
        JPanel lowerRow = new JPanel(new GridLayout(1, 2, 18, 0));
        lowerRow.setOpaque(false);
        lowerRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        tableStatusPanel = new TableStatusPanel();
        recentOrdersTable = new RecentOrdersTable();
        recentOrdersTable.setOnViewAllAction(() -> {
            if (onNavigate != null) {
                onNavigate.accept("ORDERS");
            }
        });

        lowerRow.add(tableStatusPanel);
        lowerRow.add(recentOrdersTable);

        // 6. Footer Strip
        JPanel footerStrip = new JPanel(new BorderLayout());
        footerStrip.setOpaque(false);
        footerStrip.setPreferredSize(new Dimension(0, 52));

        JPanel phraseBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(251, 246, 234));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(230, 222, 210));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        phraseBar.setOpaque(false);
        phraseBar.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 22));

        JLabel clocheIcon = new JLabel(IconFactory.roomService(22, GOLD));

        JLabel slogan = new JLabel("Good Food  •  Good Mood  •  DineEase");
        slogan.setFont(FontUtil.getPlayfairDisplay(18f, Font.ITALIC));
        slogan.setForeground(new Color(190, 138, 48));
        slogan.setHorizontalAlignment(SwingConstants.CENTER);

        // Leaf decoration at right corner
        JLabel leafIcon = new JLabel("🌿");
        leafIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        leafIcon.setForeground(new Color(190, 160, 110));

        phraseBar.add(clocheIcon, BorderLayout.WEST);
        phraseBar.add(slogan, BorderLayout.CENTER);
        phraseBar.add(leafIcon, BorderLayout.EAST);

        footerStrip.add(phraseBar, BorderLayout.CENTER);

        // Assemble body
        root.add(errorBanner);
        root.add(headerPanel);
        root.add(statCardsGrid);
        root.add(middleRow);
        root.add(lowerRow);
        root.add(footerStrip);

        JScrollPane scrollPane = new JScrollPane(root);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Start live clock
        startLiveClock();

        // Start auto-refresh timer (every 5 seconds)
        startAutoRefresh();

        // Initial async load
        reloadAllData(false);
    }

    public void setOnNavigate(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }

    private void startLiveClock() {
        liveClockTimer = new Timer(1000, e -> updateClockDisplay());
        liveClockTimer.setInitialDelay(0);
        liveClockTimer.start();
    }

    private void updateClockDisplay() {
        LocalDateTime now = LocalDateTime.now();
        clockDateLabel.setText(now.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy")));
        clockTimeLabel.setText(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
    }

    private void startAutoRefresh() {
        autoRefreshTimer = new Timer(5000, e -> reloadAllData(false));
        autoRefreshTimer.start();
    }

    public void reloadAllData(boolean manual) {
        if (manual) {
            refreshBtn.setEnabled(false);
            refreshBtn.setText("Reloading...");
        }

        SwingWorker<DashboardDataBundle, Void> worker = new SwingWorker<>() {
            @Override
            protected DashboardDataBundle doInBackground() {
                DashboardStats stats = dashboardService.getDashboardStats();
                SalesData salesData = dashboardService.getSalesData(currentSalesPeriod);
                List<OrderStatusCount> statuses = dashboardService.getOrderStatuses();
                List<OrderRow> recentOrders = dashboardService.getRecentOrders();
                return new DashboardDataBundle(stats, salesData, statuses, recentOrders);
            }

            @Override
            protected void done() {
                try {
                    DashboardDataBundle bundle = get();
                    errorBanner.setVisible(false);
                    applyDashboardData(bundle);
                } catch (Exception ex) {
                    errorBanner.setVisible(true);
                } finally {
                    if (manual) {
                        refreshBtn.setEnabled(true);
                        refreshBtn.setText("Refresh");
                    }
                }
            }
        };
        worker.execute();
    }

    private void reloadSalesData() {
        SwingWorker<SalesData, Void> worker = new SwingWorker<>() {
            @Override
            protected SalesData doInBackground() {
                return dashboardService.getSalesData(currentSalesPeriod);
            }

            @Override
            protected void done() {
                try {
                    SalesData data = get();
                    salesChartPanel.updateData(data);
                } catch (Exception ignored) {
                }
            }
        };
        worker.execute();
    }

    private void applyDashboardData(DashboardDataBundle bundle) {
        if (bundle == null) return;
        DashboardStats stats = bundle.stats;

        // Card 1: Users
        totalUsersCard.updateData(
                String.valueOf(stats.getTotalUsers()),
                stats.getUsersThisMonth() + " this month",
                stats.getUsersThisMonth() > 0,
                stats.getUsersThisMonth() == 0
        );

        // Card 2: Menu Items
        menuItemsCard.updateData(
                String.valueOf(stats.getTotalMenuItems()),
                stats.getMenuItemsThisMonth() + " this month",
                stats.getMenuItemsThisMonth() > 0,
                stats.getMenuItemsThisMonth() == 0
        );

        // Card 3: Inventory Items
        inventoryItemsCard.updateData(
                String.valueOf(stats.getTotalInventoryItems()),
                stats.getInventoryItemsThisMonth() + " this month",
                stats.getInventoryItemsThisMonth() > 0,
                stats.getInventoryItemsThisMonth() == 0
        );

        // Card 4: Low Stock
        lowStockCard.updateData(
                String.valueOf(stats.getLowStockCount()),
                stats.getLowStockThisWeek() + " this week",
                stats.getLowStockThisWeek() <= 0,
                false
        );

        // Card 5: Today's Orders
        int orderDiff = stats.getOrdersDiffVsYesterday();
        todaysOrdersCard.updateData(
                String.valueOf(stats.getTodayOrdersCount()),
                (orderDiff >= 0 ? "+" : "") + orderDiff + " vs yesterday",
                orderDiff >= 0,
                false
        );

        // Card 6: Today's Revenue
        DecimalFormat df = new DecimalFormat("#,##,##0");
        double revPct = stats.getRevenuePercentVsYesterday();
        todaysRevenueCard.updateData(
                "₹ " + df.format(stats.getTodayRevenue()),
                (revPct >= 0 ? "+" : "") + String.format("%.0f%%", revPct) + " vs yesterday",
                revPct >= 0,
                false
        );

        // Sales Chart
        salesChartPanel.updateData(bundle.salesData);

        // Donut Chart
        donutChartPanel.updateData(bundle.statuses);

        // Tables
        tableStatusPanel.updateData(
                stats.getAvailableTables(),
                stats.getOccupiedTables(),
                stats.getReservedTables(),
                stats.getTotalTables()
        );

        // Recent Orders
        recentOrdersTable.updateData(bundle.recentOrders);
    }

    private static class DashboardDataBundle {
        final DashboardStats stats;
        final SalesData salesData;
        final List<OrderStatusCount> statuses;
        final List<OrderRow> recentOrders;

        DashboardDataBundle(DashboardStats stats, SalesData salesData, List<OrderStatusCount> statuses, List<OrderRow> recentOrders) {
            this.stats = stats;
            this.salesData = salesData;
            this.statuses = statuses;
            this.recentOrders = recentOrders;
        }
    }
}

