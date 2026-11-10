package com.dineease.view;

import com.dineease.dao.DashboardDAO;
import com.dineease.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private static final Color SIDEBAR_COLOR = new Color(48, 39, 31);
    private static final Color SIDEBAR_SELECTED = new Color(67, 55, 44);
    private static final Color GOLD = new Color(196, 148, 48);
    private static final Color BACKGROUND = new Color(248, 246, 241);
    private static final Color CARD_COLOR = new Color(255, 252, 247);
    private static final Color BORDER_COLOR = new Color(225, 216, 201);
    private static final Color TEXT_PRIMARY = new Color(35, 35, 35);
    private static final Color TEXT_SECONDARY = new Color(105, 98, 90);

    private JPanel contentPanel;
    private JLabel totalUsersLabel;
    private JLabel totalMenuLabel;
    private JLabel totalInventoryLabel;
    private JLabel lowStockLabel;
    private JLabel todayOrdersLabel;
    private JLabel todayRevenueLabel;
    private JLabel availableTablesLabel;
    private JLabel occupiedTablesLabel;

    private JLabel pendingOrdersLabel;
    private JLabel preparingOrdersLabel;
    private JLabel readyOrdersLabel;
    private JLabel servedOrdersLabel;
    private JLabel cancelledOrdersLabel;
    private JLabel reservedTablesLabel;

    private DashboardDAO dashboardDAO;

    public DashboardFrame(User user) {
        setTitle("DineEase AI - Admin Dashboard");
        setSize(1400, 850);
        setMinimumSize(new Dimension(1200, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardDAO = new DashboardDAO();
        createUI(user);
        loadDashboardData();
        setVisible(true);
    }

    private void createUI(User user) {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BACKGROUND);
        createDashboardContent(user);
        rootPanel.add(createSidebar(user), BorderLayout.WEST);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private JPanel createSidebar(User user) {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(285, 0));
        sidebar.setBackground(SIDEBAR_COLOR);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_COLOR);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(35, 28, 25, 20));

        JLabel logo = new JLabel("DineEase");
        logo.setFont(new Font("Serif", Font.BOLD, 32));
        logo.setForeground(GOLD);

        JLabel aiLabel = new JLabel("AI");
        aiLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        aiLabel.setForeground(Color.WHITE);

        JLabel restaurantLabel = new JLabel("RESTAURANT MANAGEMENT");
        restaurantLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        restaurantLabel.setForeground(new Color(190, 181, 168));

        logoPanel.add(logo);
        logoPanel.add(Box.createVerticalStrut(2));
        logoPanel.add(aiLabel);
        logoPanel.add(Box.createVerticalStrut(10));
        logoPanel.add(restaurantLabel);

        sidebar.add(logoPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(SIDEBAR_COLOR);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(10, 12, 10, 12));

        addMenuButton(menuPanel, "Dashboard", true);
        addMenuButton(menuPanel, "User Management", false);
        addMenuButton(menuPanel, "Menu Management", false);
        addMenuButton(menuPanel, "Inventory", false);
        addMenuButton(menuPanel, "Order Management", false);
        addMenuButton(menuPanel, "Table Management", false);
        addMenuButton(menuPanel, "Billing", false);
        addMenuButton(menuPanel, "Bill History", false);
        addMenuButton(menuPanel, "Reports", false);
        addMenuButton(menuPanel, "Settings", false);

        sidebar.add(menuPanel, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(SIDEBAR_COLOR);
        userPanel.setBorder(new EmptyBorder(20, 22, 22, 20));

        JLabel nameLabel = new JLabel(user.getFullName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel(user.getRole() + " • DineEase AI v1.0");
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        roleLabel.setForeground(new Color(190, 181, 168));

        JPanel userInfo = new JPanel();
        userInfo.setBackground(SIDEBAR_COLOR);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.add(nameLabel);
        userInfo.add(Box.createVerticalStrut(4));
        userInfo.add(roleLabel);

        userPanel.add(userInfo, BorderLayout.CENTER);
        sidebar.add(userPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private void addMenuButton(JPanel parent, String text, boolean selected) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(selected ? SIDEBAR_SELECTED : SIDEBAR_COLOR);
        button.setBorder(new EmptyBorder(12, 18, 12, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(e -> {
            if (!text.equals("Dashboard")) {
                JOptionPane.showMessageDialog(this, text + " module will be connected next.", "DineEase AI", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        parent.add(button);
        parent.add(Box.createVerticalStrut(3));
    }

    private void createDashboardContent(User user) {
        contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(30, 35, 20, 35));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to DineEase AI");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 30));
        welcome.setForeground(TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Restaurant management overview and live operations");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_SECONDARY);

        heading.add(welcome);
        heading.add(Box.createVerticalStrut(4));
        heading.add(subtitle);
        header.add(heading, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JLabel dateLabel = new JLabel("Tue, 27 Aug 2025");
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(TEXT_SECONDARY);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(GOLD);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(new EmptyBorder(10, 18, 10, 18));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> loadDashboardData());

        actions.add(dateLabel);
        actions.add(refreshButton);
        header.add(actions, BorderLayout.EAST);

        contentPanel.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(22, 0, 0, 0));

        JPanel statPanel = new JPanel(new GridLayout(2, 4, 16, 16));
        statPanel.setOpaque(false);

        totalUsersLabel = new JLabel("12.8K");
        totalMenuLabel = new JLabel("248");
        totalInventoryLabel = new JLabel("1,240");
        lowStockLabel = new JLabel("18");
        todayOrdersLabel = new JLabel("324");
        todayRevenueLabel = new JLabel("₹1,24,850");
        availableTablesLabel = new JLabel("11");
        occupiedTablesLabel = new JLabel("17");

        statPanel.add(createStatCard("Total Users", totalUsersLabel, "+18.6%"));
        statPanel.add(createStatCard("Menu Items", totalMenuLabel, "+8.2%"));
        statPanel.add(createStatCard("Inventory Items", totalInventoryLabel, "+5.1%"));
        statPanel.add(createStatCard("Low Stock", lowStockLabel, "-2.3%"));
        statPanel.add(createStatCard("Today's Orders", todayOrdersLabel, "+12.4%"));
        statPanel.add(createStatCard("Today's Revenue", todayRevenueLabel, "+22.1%"));
        statPanel.add(createStatCard("Available Tables", availableTablesLabel, "Good"));
        statPanel.add(createStatCard("Occupied Tables", occupiedTablesLabel, "Busy"));

        center.add(statPanel);
        center.add(Box.createVerticalStrut(18));

        JPanel lowerPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        lowerPanel.setOpaque(false);
        lowerPanel.add(createSalesOverviewPanel());

        JPanel rightStack = new JPanel();
        rightStack.setOpaque(false);
        rightStack.setLayout(new BoxLayout(rightStack, BoxLayout.Y_AXIS));
        rightStack.add(createOrderStatusPanel());
        rightStack.add(Box.createVerticalStrut(16));
        rightStack.add(createTableStatusPanel());

        lowerPanel.add(rightStack);
        center.add(lowerPanel);
        contentPanel.add(center, BorderLayout.CENTER);

        JPanel recent = createRecentOrdersPanel();
        contentPanel.add(recent, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, JLabel valueLabel, String delta) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(14, 16, 12, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(TEXT_SECONDARY);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
        valueLabel.setForeground(TEXT_PRIMARY);

        JLabel deltaLabel = new JLabel(delta);
        deltaLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        deltaLabel.setForeground(new Color(42, 128, 77));
        deltaLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.WEST);
        textPanel.add(deltaLabel, BorderLayout.EAST);

        card.add(textPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSalesOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Sales Overview");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_PRIMARY);

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        summary.setOpaque(false);
        JLabel month = new JLabel("This month");
        month.setFont(new Font("SansSerif", Font.PLAIN, 12));
        month.setForeground(TEXT_SECONDARY);
        JLabel growth = new JLabel("+18.2%");
        growth.setFont(new Font("SansSerif", Font.BOLD, 12));
        growth.setForeground(new Color(44, 160, 95));
        summary.add(month);
        summary.add(growth);

        header.add(title, BorderLayout.WEST);
        header.add(summary, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        LineChartPanel chart = new LineChartPanel(new int[]{32, 46, 42, 68, 58, 83, 92, 76, 98, 118, 104, 132});
        panel.add(chart, BorderLayout.CENTER);

        JLabel revenue = new JLabel("₹ 1,24,850");
        revenue.setFont(new Font("SansSerif", Font.BOLD, 20));
        revenue.setForeground(TEXT_PRIMARY);
        revenue.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(revenue, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("Order Status");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_PRIMARY);
        panel.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);

        DonutChartPanel donut = new DonutChartPanel(new int[]{42, 26, 18, 9, 5});
        body.add(donut, BorderLayout.CENTER);

        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.add(createLegendRow("Pending", new Color(231, 186, 92), "42"));
        legend.add(createLegendRow("Preparing", new Color(120, 75, 45), "26"));
        legend.add(createLegendRow("Ready", new Color(154, 103, 54), "18"));
        legend.add(createLegendRow("Served", new Color(79, 102, 63), "9"));
        legend.add(createLegendRow("Cancelled", new Color(166, 142, 125), "5"));
        body.add(legend, BorderLayout.EAST);

        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTableStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("Table Status");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_PRIMARY);
        panel.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(createStatusBar("Available", 11, new Color(94, 124, 74), 0.55f));
        body.add(Box.createVerticalStrut(10));
        body.add(createStatusBar("Occupied", 17, new Color(196, 148, 48), 0.8f));
        body.add(Box.createVerticalStrut(10));
        body.add(createStatusBar("Reserved", 6, new Color(140, 96, 62), 0.4f));

        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLegendRow(String label, Color color, String count) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(3, 0, 3, 0));

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(10, 10));

        JLabel name = new JLabel(label);
        name.setFont(new Font("SansSerif", Font.PLAIN, 12));
        name.setForeground(TEXT_SECONDARY);

        JLabel value = new JLabel(count);
        value.setFont(new Font("SansSerif", Font.BOLD, 12));
        value.setForeground(TEXT_PRIMARY);

        row.add(colorBox, BorderLayout.WEST);
        row.add(name, BorderLayout.CENTER);
        row.add(value, BorderLayout.EAST);
        return row;
    }

    private JPanel createStatusBar(String label, int count, Color color, float widthRatio) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel name = new JLabel(label);
        name.setFont(new Font("SansSerif", Font.PLAIN, 12));
        name.setForeground(TEXT_PRIMARY);

        JPanel meter = new JPanel(new BorderLayout());
        meter.setBackground(new Color(235, 228, 220));
        meter.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JPanel fill = new JPanel();
        fill.setBackground(color);
        fill.setPreferredSize(new Dimension((int) (150 * widthRatio), 10));

        JLabel countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        countLabel.setForeground(TEXT_PRIMARY);

        meter.add(fill, BorderLayout.WEST);
        row.add(name, BorderLayout.WEST);
        row.add(meter, BorderLayout.CENTER);
        row.add(countLabel, BorderLayout.EAST);
        return row;
    }

    private JPanel createRecentOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(16, 18, 16, 18)
        ));
        panel.setPreferredSize(new Dimension(0, 220));

        JLabel title = new JLabel("Recent Orders");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT_PRIMARY);
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"Order ID", "Customer", "Items", "Amount", "Status"};
        Object[][] data = {
                {"#2451", "Ayesha Khan", "3", "₹1,450", "Paid"},
                {"#2450", "Daniel Lee", "5", "₹2,180", "Preparing"},
                {"#2449", "Nora Smith", "2", "₹860", "Ready"},
                {"#2448", "John Doe", "4", "₹1,980", "Delivered"},
                {"#2447", "Maya Patel", "6", "₹3,420", "Paid"}
        };

        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(CARD_COLOR);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(245, 235, 220));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(247, 240, 232));
        table.getTableHeader().setForeground(TEXT_PRIMARY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private static class LineChartPanel extends JPanel {
        private final int[] values;

        private LineChartPanel(int[] values) {
            this.values = values;
            setOpaque(false);
            setPreferredSize(new Dimension(400, 210));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(new Color(241, 235, 229));
            for (int i = 0; i <= 5; i++) {
                int y = 20 + i * ((height - 40) / 5);
                g2.drawLine(20, y, width - 20, y);
            }

            int[] xs = new int[values.length];
            int[] ys = new int[values.length];
            int max = 140;
            int min = 0;

            for (int i = 0; i < values.length; i++) {
                xs[i] = 20 + i * ((width - 40) / (values.length - 1));
                ys[i] = height - 20 - ((values[i] - min) * (height - 60)) / (max - min);
            }

            g2.setColor(new Color(196, 148, 48));
            g2.setStroke(new BasicStroke(3f));
            for (int i = 1; i < xs.length; i++) {
                g2.drawLine(xs[i - 1], ys[i - 1], xs[i], ys[i]);
            }

            for (int i = 0; i < xs.length; i++) {
                g2.setColor(new Color(196, 148, 48));
                g2.fillOval(xs[i] - 4, ys[i] - 4, 8, 8);
            }

            g2.dispose();
        }
    }

    private static class DonutChartPanel extends JPanel {
        private final int[] segments;

        private DonutChartPanel(int[] segments) {
            this.segments = segments;
            setOpaque(false);
            setPreferredSize(new Dimension(180, 180));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int total = 0;
            for (int segment : segments) total += segment;

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int radius = 58;
            int startAngle = 90;

            Color[] colors = {
                    new Color(231, 186, 92),
                    new Color(120, 75, 45),
                    new Color(154, 103, 54),
                    new Color(79, 102, 63),
                    new Color(166, 142, 125)
            };

            for (int i = 0; i < segments.length; i++) {
                int arc = (int) Math.round((segments[i] * 360.0) / total);
                g2.setColor(colors[i]);
                g2.fillArc(cx - radius, cy - radius, radius * 2, radius * 2, startAngle, -arc);
                startAngle -= arc;
            }

            g2.setColor(new Color(255, 252, 247));
            g2.fillOval(cx - 28, cy - 28, 56, 56);

            g2.setColor(new Color(76, 61, 49));
            g2.setFont(new Font("SansSerif", Font.BOLD, 20));
            g2.drawString("120", cx - 18, cy + 8);

            g2.dispose();
        }
    }

    private void loadDashboardData() {
        totalUsersLabel.setText("12.8K");
        totalMenuLabel.setText("248");
        totalInventoryLabel.setText("1,240");
        lowStockLabel.setText("18");
        todayOrdersLabel.setText("324");
        todayRevenueLabel.setText("₹1,24,850");
        availableTablesLabel.setText("11");
        occupiedTablesLabel.setText("17");
    }
}
