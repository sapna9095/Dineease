package com.dineease.ui.dashboard;

import com.dineease.model.DashboardStats;
import com.dineease.model.OrderRow;
import com.dineease.model.OrderStatusCount;
import com.dineease.model.SalesPoint;
import com.dineease.service.DashboardService;
import com.dineease.service.MockDashboardService;
import com.dineease.ui.IconFactory;
import com.dineease.util.FontUtil;
import com.dineease.util.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final DashboardService service = new MockDashboardService();
    private final JLabel clockLabel = new JLabel();

    public DashboardFrame() {
        super("DineEase AI - Admin Dashboard");
        Theme.apply();
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = Math.min(1536, Math.max(1200, bounds.width));
        int height = Math.min(1024, Math.max(900, bounds.height));
        setSize(width, height);
        setLocationRelativeTo(null);
        setLocation(Math.max(0, (int) bounds.getX() + (bounds.width - width) / 2), Math.max(0, (int) bounds.getY() + (bounds.height - height) / 2));

        add(createWindowShell(), BorderLayout.CENTER);
        startClock();
        setVisible(true);
    }

    private JPanel createWindowShell() {
        JPanel shell = new JPanel(new BorderLayout());
        shell.setBackground(new Color(236, 233, 228));
        shell.add(createTitleBar(), BorderLayout.NORTH);
        shell.add(createDashboardRoot(), BorderLayout.CENTER);
        return shell;
    }

    private JPanel createTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(238, 236, 232));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(214, 209, 201)));
        bar.setPreferredSize(new Dimension(0, 28));

        JLabel title = new JLabel(" DineEase AI - Admin Dashboard");
        title.setFont(FontUtil.getNunito(12f, Font.BOLD));
        title.setForeground(new Color(58, 58, 58));
        title.setIcon(IconFactory.chefHat(12, new Color(167, 123, 64)));
        title.setIconTextGap(8);
        title.setBorder(new EmptyBorder(0, 8, 0, 0));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 7));
        controls.setOpaque(false);
        JButton minimize = new JButton("—");
        minimize.setFocusable(false);
        minimize.setBorder(null);
        minimize.setContentAreaFilled(false);
        minimize.setForeground(new Color(90, 90, 90));
        minimize.setFont(new Font("SansSerif", Font.BOLD, 14));
        minimize.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton close = new JButton("×");
        close.setFocusable(false);
        close.setBorder(null);
        close.setContentAreaFilled(false);
        close.setForeground(new Color(90, 90, 90));
        close.setFont(new Font("SansSerif", Font.BOLD, 18));
        close.addActionListener(e -> dispose());

        controls.add(minimize);
        controls.add(close);

        bar.add(title, BorderLayout.WEST);
        bar.add(controls, BorderLayout.EAST);
        return bar;
    }

    private JPanel createDashboardRoot() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 241, 235));
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createContentArea(), BorderLayout.CENTER);
        return root;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(52, 35, 25));
        sidebar.setPreferredSize(new Dimension(288, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder());

        JPanel logoBlock = new JPanel();
        logoBlock.setOpaque(false);
        logoBlock.setLayout(new GridBagLayout());
        logoBlock.setBorder(new EmptyBorder(38, 0, 24, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 6, 0);

        JLabel dineEaseLabel = new JLabel("DineEase");
        dineEaseLabel.setForeground(new Color(214, 172, 95));
        dineEaseLabel.setFont(FontUtil.getPlayfairDisplay(36f, Font.BOLD));
        logoBlock.add(dineEaseLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 14, 0);
        JLabel aiLabel = new JLabel("AI");
        aiLabel.setForeground(Color.WHITE);
        aiLabel.setFont(FontUtil.getPlayfairDisplay(24f, Font.BOLD));
        logoBlock.add(aiLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel managementLabel = new JLabel("RESTAURANT MANAGEMENT");
        managementLabel.setForeground(new Color(184, 164, 144));
        managementLabel.setFont(FontUtil.getNunito(12f, Font.BOLD));
        managementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoBlock.add(managementLabel, gbc);

        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new GridBagLayout());
        navPanel.setBorder(new EmptyBorder(0, 12, 0, 12));

        GridBagConstraints navGbc = new GridBagConstraints();
        navGbc.gridx = 0;
        navGbc.gridy = 0;
        navGbc.fill = GridBagConstraints.HORIZONTAL;
        navGbc.weightx = 1.0;
        navGbc.insets = new Insets(0, 0, 8, 0);

        String[] items = {"Dashboard", "User Management", "Menu Management", "Inventory", "Order Management", "Table Management", "Billing", "Bill History", "Reports", "Settings"};
        Icon[] itemIcons = {
                IconFactory.home(24, new Color(227, 201, 143)),
                IconFactory.account(24, new Color(227, 201, 143)),
                IconFactory.silverware(24, new Color(227, 201, 143)),
                IconFactory.cube(24, new Color(227, 201, 143)),
                IconFactory.clipboard(24, new Color(227, 201, 143)),
                IconFactory.tableFurniture(24, new Color(227, 201, 143)),
                IconFactory.creditCard(24, new Color(227, 201, 143)),
                IconFactory.fileDocument(24, new Color(227, 201, 143)),
                IconFactory.barChart(24, new Color(227, 201, 143)),
                IconFactory.cog(24, new Color(227, 201, 143))
        };

        for (int i = 0; i < items.length; i++) {
            final boolean active = i == 0; final String itemTitle = items[i];
            JButton item = new JButton(items[i]);
            item.setIcon(itemIcons[i]);
            item.setOpaque(true);
            item.setFocusPainted(false);
            item.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(0, 0, 0, 0),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            item.setBackground(active ? new Color(144, 111, 71, 200) : new Color(0, 0, 0, 0));
            item.setForeground(active ? Color.WHITE : new Color(236, 228, 220));
            item.setFont(FontUtil.getNunito(17f, Font.BOLD));
            item.setHorizontalAlignment(SwingConstants.LEFT);
            item.setHorizontalTextPosition(SwingConstants.RIGHT);
            item.setIconTextGap(12);
            item.setContentAreaFilled(true);
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            item.setPreferredSize(new Dimension(0, 54));
            item.setMinimumSize(new Dimension(0, 54));
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
            item.addActionListener(e -> {
                item.setBackground(new Color(144, 111, 71, 200));
                item.setForeground(Color.WHITE);
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        itemTitle + " module is ready to open.",
                        "DineEase AI",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            navPanel.add(item, navGbc);
            navGbc.gridy++;
        }

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BorderLayout());
        footer.setBorder(new EmptyBorder(18, 18, 18, 12));

        JSeparator topLine = new JSeparator();
        topLine.setForeground(new Color(155, 124, 86));
        footer.add(topLine, BorderLayout.NORTH);

        JPanel meta = new JPanel();
        meta.setOpaque(false);
        meta.setLayout(new BoxLayout(meta, BoxLayout.Y_AXIS));
        meta.setBorder(new EmptyBorder(14, 0, 0, 0));

        JLabel admin = new JLabel("Administrator");
        admin.setForeground(Color.WHITE);
        admin.setFont(FontUtil.getNunito(15f, Font.BOLD));
        admin.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel version = new JLabel("DineEase AI • v1.0");
        version.setForeground(new Color(196, 176, 151));
        version.setFont(FontUtil.getNunito(12f, Font.PLAIN));
        version.setAlignmentX(Component.LEFT_ALIGNMENT);

        meta.add(admin);
        meta.add(Box.createVerticalStrut(4));
        meta.add(version);
        footer.add(meta, BorderLayout.SOUTH);

        sidebar.add(logoBlock, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);
        sidebar.add(footer, BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel createContentArea() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(244, 239, 233));
        content.setBorder(new EmptyBorder(22, 26, 18, 26));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(4, 0, 18, 0));

        JPanel leftHeader = new JPanel(new BorderLayout());
        leftHeader.setOpaque(false);
        JLabel title = new JLabel("Welcome to DineEase AI");
        title.setFont(FontUtil.getNunito(34f, Font.BOLD));
        title.setForeground(new Color(47, 34, 22));

        JLabel subtitle = new JLabel("Restaurant Management Dashboard");
        subtitle.setFont(FontUtil.getNunito(17f, Font.PLAIN));
        subtitle.setForeground(new Color(105, 86, 67));
        subtitle.setBorder(new EmptyBorder(8, 0, 0, 0));

        leftHeader.add(title, BorderLayout.NORTH);
        leftHeader.add(subtitle, BorderLayout.SOUTH);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        rightHeader.setOpaque(false);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(245, 208, 136));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusable(false);
        refreshButton.setBorder(new EmptyBorder(10, 16, 10, 16));
        refreshButton.setPreferredSize(new Dimension(138, 42));
        refreshButton.setFont(FontUtil.getNunito(15f, Font.BOLD));
        refreshButton.setIcon(IconFactory.refresh(16, Color.WHITE));
        refreshButton.setHorizontalAlignment(SwingConstants.CENTER);
        refreshButton.addActionListener(e -> {
            refreshButton.setEnabled(false);
            refreshButton.setText("Refreshing...");
            Timer refreshTimer = new Timer(500, event -> {
                refreshButton.setEnabled(true);
                refreshButton.setText("Refresh");
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        "Dashboard refreshed successfully.",
                        "DineEase AI",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            refreshTimer.setRepeats(false);
            refreshTimer.start();
        });

        JPanel datePanel = new JPanel(new BorderLayout(8, 0));
        datePanel.setOpaque(false);
        datePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel calendarIcon = new JLabel(IconFactory.calendar(18, new Color(172, 133, 72)));
        calendarIcon.setVerticalAlignment(SwingConstants.TOP);

        JPanel dateText = new JPanel();
        dateText.setOpaque(false);
        dateText.setLayout(new BoxLayout(dateText, BoxLayout.Y_AXIS));
        JLabel dateLine = new JLabel();
        dateLine.setFont(FontUtil.getNunito(13f, Font.BOLD));
        dateLine.setForeground(new Color(50, 36, 27));
        dateLine.setText("Monday, 8 Sep 2025");
        JLabel timeLine = new JLabel();
        timeLine.setFont(FontUtil.getNunito(12f, Font.BOLD));
        timeLine.setForeground(new Color(112, 89, 66));
        timeLine.setText("10:24 AM");
        dateText.add(dateLine);
        dateText.add(timeLine);

        datePanel.add(calendarIcon, BorderLayout.WEST);
        datePanel.add(dateText, BorderLayout.CENTER);

        clockLabel.setFont(FontUtil.getNunito(12f, Font.BOLD));
        clockLabel.setForeground(new Color(112, 89, 66));
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setText("Monday, 8 Sep 2025\n10:24 AM");
        clockLabel.setVerticalAlignment(SwingConstants.TOP);

        rightHeader.add(refreshButton);
        rightHeader.add(datePanel);

        header.add(leftHeader, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        JPanel cardGrid = new JPanel(new GridLayout(1, 6, 14, 0));
        cardGrid.setOpaque(false);
        cardGrid.setBorder(new EmptyBorder(0, 0, 18, 0));
        cardGrid.add(createStatCard("Total Users", "9", "+2 this month", new Color(80, 156, 102), new Color(212, 232, 217), IconFactory.account(24, new Color(87, 70, 54)), true));
        cardGrid.add(createStatCard("Menu Items", "5", "+1 this month", new Color(80, 156, 102), new Color(212, 232, 217), IconFactory.utensils(24, new Color(87, 70, 54)), true));
        cardGrid.add(createStatCard("Inventory Items", "8", "+0 this month", new Color(120, 120, 120), new Color(232, 232, 232), IconFactory.cube(24, new Color(87, 70, 54)), false));
        cardGrid.add(createStatCard("Low Stock", "2", "-1 this week", new Color(202, 120, 98), new Color(246, 215, 204), IconFactory.alert(24, new Color(176, 77, 56)), true));
        cardGrid.add(createStatCard("Today's Orders", "12", "+4 vs yesterday", new Color(80, 156, 102), new Color(212, 232, 217), IconFactory.clipboard(24, new Color(87, 70, 54)), true));
        cardGrid.add(createStatCard("Today's Revenue", "₹ 4,850", "+12% vs yesterday", new Color(80, 156, 102), new Color(236, 216, 153), IconFactory.creditCard(24, new Color(87, 70, 54)), true));

        JPanel middle = new JPanel(new GridLayout(1, 2, 18, 0));
        middle.setOpaque(false);
        middle.setBorder(new EmptyBorder(0, 0, 18, 0));
        middle.add(createSalesPanel());
        middle.add(createStatusPanel());

        JPanel lower = new JPanel(new GridLayout(1, 2, 18, 0));
        lower.setOpaque(false);
        lower.setBorder(new EmptyBorder(0, 0, 0, 0));
        lower.add(createTablePanel());
        lower.add(createRecentOrdersPanel());

        JPanel footerStrip = new JPanel(new BorderLayout());
        footerStrip.setOpaque(false);
        footerStrip.setBorder(new EmptyBorder(12, 0, 0, 0));
        footerStrip.setPreferredSize(new Dimension(0, 58));

        JPanel phraseBar = new JPanel(new BorderLayout());
        phraseBar.setBackground(new Color(246, 236, 217));
        phraseBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(234, 223, 203), 1),
                new EmptyBorder(0, 14, 0, 14)
        ));
        JLabel iconLabel = new JLabel(IconFactory.roomService(22, new Color(183, 121, 31)));
        JLabel slogan = new JLabel("Good Food  •  Good Mood  •  DineEase");
        slogan.setFont(FontUtil.getPlayfairDisplay(20f, Font.ITALIC));
        slogan.setForeground(new Color(172, 116, 59));
        slogan.setHorizontalAlignment(SwingConstants.CENTER);
        phraseBar.add(iconLabel, BorderLayout.WEST);
        phraseBar.add(slogan, BorderLayout.CENTER);
        footerStrip.add(phraseBar, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(header);
        body.add(cardGrid);
        body.add(middle);
        body.add(lower);
        body.add(footerStrip);

        content.add(body, BorderLayout.CENTER);
        return content;
    }

    private JPanel createStatCard(String labelText, String valueText, String deltaText, Color deltaColor, Color badgeBg, Icon icon, boolean positive) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(251, 248, 244));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel iconHolder = new JLabel(icon);
        iconHolder.setOpaque(true);
        iconHolder.setBackground(new Color(238, 227, 194));
        iconHolder.setHorizontalAlignment(SwingConstants.CENTER);
        iconHolder.setPreferredSize(new Dimension(42, 42));
        iconHolder.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topRow.add(iconHolder, BorderLayout.WEST);

        JLabel title = new JLabel(labelText);
        title.setFont(FontUtil.getNunito(14f, Font.PLAIN));
        title.setForeground(new Color(80, 63, 48));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setBorder(new EmptyBorder(20, 0, 0, 0));
        topRow.add(title, BorderLayout.CENTER);

        JLabel value = new JLabel(valueText);
        value.setFont(FontUtil.getNunito(30f, Font.BOLD));
        value.setForeground(new Color(44, 32, 22));
        value.setBorder(new EmptyBorder(8, 0, 6, 0));

        JLabel trend = new JLabel(deltaText, SwingConstants.LEFT);
        trend.setFont(FontUtil.getNunito(12f, Font.BOLD));
        trend.setForeground(deltaColor);
        trend.setText((positive ? "▲ " : "▼ ") + deltaText);
        trend.setIconTextGap(4);
        trend.setBackground(badgeBg);
        trend.setOpaque(false);

        JPanel stack = new JPanel();
        stack.setOpaque(false);
        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
        stack.add(topRow);
        stack.add(value);
        stack.add(trend);

        card.add(stack, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 248, 244));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Sales Overview");
        title.setFont(FontUtil.getNunito(17f, Font.BOLD));
        title.setForeground(new Color(38, 30, 24));

        JPanel period = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        period.setOpaque(false);
        JLabel periodLabel = new JLabel("Last 7 Days");
        periodLabel.setFont(FontUtil.getNunito(12f, Font.BOLD));
        periodLabel.setForeground(new Color(116, 98, 82));
        periodLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 219, 208), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        period.add(periodLabel);

        header.add(title, BorderLayout.WEST);
        header.add(period, BorderLayout.EAST);

        JPanel chartWrap = new JPanel(new BorderLayout());
        chartWrap.setOpaque(false);
        chartWrap.setBorder(new EmptyBorder(14, 0, 0, 0));
        chartWrap.add(new SalesChartPanel(), BorderLayout.CENTER);

        panel.add(header, BorderLayout.NORTH);
        panel.add(chartWrap, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 248, 244));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("Order Status");
        title.setFont(FontUtil.getNunito(17f, Font.BOLD));
        title.setForeground(new Color(38, 30, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBorder(new EmptyBorder(16, 0, 0, 0));

        String[] labels = {"Pending", "Preparing", "Ready", "Served", "Cancelled"};
        int[] counts = {2, 3, 1, 8, 0};
        Color[] colors = {new Color(234, 179, 74), new Color(75, 146, 235), new Color(76, 169, 110), new Color(155, 104, 200), new Color(220, 90, 82)};

        for (int i = 0; i < labels.length; i++) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setPreferredSize(new Dimension(0, 28));
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(231, 223, 214)),
                    new EmptyBorder(4, 0, 4, 0)
            ));

            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            left.setOpaque(false);
            JLabel dot = new JLabel();
            dot.setOpaque(true);
            dot.setBackground(colors[i]);
            dot.setPreferredSize(new Dimension(12, 12));
            dot.setBorder(BorderFactory.createEmptyBorder());
            JLabel label = new JLabel(labels[i]);
            label.setFont(FontUtil.getNunito(13f, Font.PLAIN));
            label.setForeground(new Color(58, 42, 31));
            left.add(dot);
            left.add(label);

            JLabel count = new JLabel(String.valueOf(counts[i]));
            count.setFont(FontUtil.getNunito(13f, Font.BOLD));
            count.setForeground(new Color(58, 42, 31));
            row.add(left, BorderLayout.WEST);
            row.add(count, BorderLayout.EAST);
            list.add(row);
        }

        DonutPanel donut = new DonutPanel(12, new int[]{2, 3, 1, 8, 0}, new Color[]{new Color(234, 179, 74), new Color(75, 146, 235), new Color(76, 169, 110), new Color(155, 104, 200), new Color(220, 90, 82)});

        content.add(list, BorderLayout.WEST);
        content.add(donut, BorderLayout.EAST);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 248, 244));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("Table Status");
        title.setFont(FontUtil.getNunito(17f, Font.BOLD));
        title.setForeground(new Color(38, 30, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(12, 0, 0, 0));

        JPanel boxes = new JPanel(new GridLayout(1, 3, 0, 0));
        boxes.setOpaque(false);
        boxes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 220, 208)));

        String[] labels = {"Available", "Occupied", "Reserved"};
        int[] counts = {5, 3, 2};
        Color[] colors = {new Color(76, 169, 110), new Color(214, 81, 72), new Color(225, 167, 56)};
        for (int i = 0; i < labels.length; i++) {
            JPanel col = new JPanel(new BorderLayout());
            col.setOpaque(false);
            col.setBorder(BorderFactory.createMatteBorder(0, 0, 0, i < 2 ? 1 : 0, new Color(229, 220, 208)));
            col.setPreferredSize(new Dimension(0, 120));

            JPanel center = new JPanel(new GridBagLayout());
            center.setOpaque(false);
            JLabel circle = new JLabel(IconFactory.utensils(24, Color.WHITE));
            circle.setOpaque(true);
            circle.setBackground(colors[i]);
            circle.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            circle.setHorizontalAlignment(SwingConstants.CENTER);
            circle.setVerticalAlignment(SwingConstants.CENTER);
            JLabel countLabel = new JLabel(String.valueOf(counts[i]));
            countLabel.setFont(FontUtil.getNunito(26f, Font.BOLD));
            countLabel.setForeground(new Color(39, 29, 23));
            countLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel nameLabel = new JLabel(labels[i]);
            nameLabel.setFont(FontUtil.getNunito(16f, Font.BOLD));
            nameLabel.setForeground(new Color(39, 29, 23));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel stack = new JPanel();
            stack.setOpaque(false);
            stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
            stack.setAlignmentX(Component.CENTER_ALIGNMENT);
            stack.add(circle);
            stack.add(Box.createVerticalStrut(8));
            stack.add(nameLabel);
            center.add(stack);
            col.add(center, BorderLayout.CENTER);
            col.add(countLabel, BorderLayout.SOUTH);
            boxes.add(col);
        }

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setBorder(new EmptyBorder(12, 0, 0, 0));
        JLabel totalIcon = new JLabel(IconFactory.utensils(18, new Color(71, 58, 48)));
        JLabel totalText = new JLabel("Total Tables");
        totalText.setFont(FontUtil.getNunito(15f, Font.BOLD));
        totalText.setForeground(new Color(39, 29, 23));
        JLabel totalValue = new JLabel("10");
        totalValue.setFont(FontUtil.getNunito(18f, Font.BOLD));
        totalValue.setForeground(new Color(39, 29, 23));
        totalRow.add(totalIcon, BorderLayout.WEST);
        totalRow.add(totalText, BorderLayout.CENTER);
        totalRow.add(totalValue, BorderLayout.EAST);

        body.add(boxes, BorderLayout.CENTER);
        body.add(totalRow, BorderLayout.SOUTH);
        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRecentOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 248, 244));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(14, 18, 14, 18)
        ));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Recent Orders");
        title.setFont(FontUtil.getNunito(17f, Font.BOLD));
        title.setForeground(new Color(38, 30, 24));
        JLabel viewAll = new JLabel("View All");
        viewAll.setFont(FontUtil.getNunito(12f, Font.BOLD));
        viewAll.setForeground(new Color(184, 129, 58));
        header.add(title, BorderLayout.WEST);
        header.add(viewAll, BorderLayout.EAST);

        JPanel table = new JPanel(new GridBagLayout());
        table.setOpaque(false);
        table.setBorder(new EmptyBorder(12, 0, 0, 0));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);

        String[] headers = {"Order ID", "Table No.", "Time", "Amount", "Status"};
        c.gridx = 0; table.add(headerCell(headers[0]), c);
        c.gridx = 1; table.add(headerCell(headers[1]), c);
        c.gridx = 2; table.add(headerCell(headers[2]), c);
        c.gridx = 3; table.add(headerCell(headers[3]), c);
        c.gridx = 4; table.add(headerCell(headers[4]), c);

        String[][] rows = {
                {"#1025", "Table 4", "10:12 AM", "₹ 850", "Served"},
                {"#1024", "Table 2", "09:58 AM", "₹ 420", "Ready"},
                {"#1023", "Table 7", "09:41 AM", "₹ 650", "Preparing"},
                {"#1022", "Table 1", "09:25 AM", "₹ 920", "Pending"},
                {"#1021", "Table 5", "08:50 AM", "₹ 780", "Served"}
        };

        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            c.gridy = rowIndex + 1;
            c.insets = new Insets(0, 0, 0, 0);
            for (int col = 0; col < rows[rowIndex].length; col++) {
                c.gridx = col;
                if (col == 4) {
                    table.add(createStatusPill(rows[rowIndex][col]), c);
                } else {
                    table.add(rowCell(rows[rowIndex][col]), c);
                }
            }
        }

        panel.add(header, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        return panel;
    }

    private JLabel headerCell(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(244, 234, 216));
        label.setForeground(new Color(55, 41, 31));
        label.setFont(FontUtil.getNunito(11f, Font.BOLD));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 218, 205)));
        label.setPreferredSize(new Dimension(0, 32));
        return label;
    }

    private JLabel rowCell(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(false);
        label.setForeground(new Color(39, 29, 21));
        label.setFont(FontUtil.getNunito(12f, Font.PLAIN));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 218, 205)));
        label.setPreferredSize(new Dimension(0, 30));
        return label;
    }

    private JPanel createStatusPill(String status) {
        JPanel pill = new JPanel(new GridBagLayout());
        pill.setOpaque(false);
        pill.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 218, 205)));
        JLabel label = new JLabel(status);
        label.setOpaque(true);
        label.setFont(FontUtil.getNunito(11.5f, Font.BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(4, 10, 4, 10));
        if ("Served".equals(status)) {
            label.setBackground(new Color(229, 220, 246));
            label.setForeground(new Color(105, 69, 170));
        } else if ("Ready".equals(status)) {
            label.setBackground(new Color(214, 237, 220));
            label.setForeground(new Color(64, 126, 79));
        } else if ("Preparing".equals(status)) {
            label.setBackground(new Color(218, 230, 249));
            label.setForeground(new Color(58, 111, 198));
        } else {
            label.setBackground(new Color(248, 234, 195));
            label.setForeground(new Color(170, 118, 35));
        }
        pill.add(label);
        return pill;
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            String value = now.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy\nHH:mm a"));
            clockLabel.setText(value);
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private static class SalesChartPanel extends JPanel {
        SalesChartPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(0, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int left = 34;
            int right = 18;
            int top = 14;
            int bottom = 28;
            int chartW = width - left - right;
            int chartH = height - top - bottom;
            int[] values = {10000, 18000, 11000, 20000, 25000, 19000, 35000};
            String[] labels = {"2 Sep", "3 Sep", "4 Sep", "5 Sep", "6 Sep", "7 Sep", "8 Sep"};
            int max = 40000;

            g2.setColor(new Color(236, 228, 220));
            for (int i = 0; i <= 4; i++) {
                int y = top + (chartH * i / 4);
                g2.drawLine(left, y, width - right, y);
                int value = (int) (max * (1 - i / 4.0));
                String text = value >= 10000 ? (value / 1000 + "K") : String.valueOf(value);
                g2.setColor(new Color(122, 103, 84));
                g2.setFont(FontUtil.getNunito(10f, Font.PLAIN));
                g2.drawString(text, 4, y + 4);
            }

            int[] xs = new int[values.length];
            int[] ys = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                xs[i] = left + (chartW * i / (values.length - 1));
                ys[i] = top + chartH - (int) ((values[i] * 1.0 / max) * chartH);
            }

            Path2D.Float path = new Path2D.Float();
            path.moveTo(xs[0], ys[0]);
            for (int i = 1; i < xs.length; i++) {
                path.lineTo(xs[i], ys[i]);
            }
            Path2D.Float area = new Path2D.Float(path);
            area.lineTo(xs[xs.length - 1], height - bottom);
            area.lineTo(xs[0], height - bottom);
            area.closePath();
            g2.setColor(new Color(214, 159, 79, 50));
            g2.fill(area);

            g2.setColor(new Color(210, 155, 58));
            g2.setStroke(new BasicStroke(2.5f));
            g2.draw(path);

            for (int i = 0; i < values.length; i++) {
                g2.setColor(new Color(210, 155, 58));
                g2.fillOval(xs[i] - 3, ys[i] - 3, 6, 6);
                g2.setColor(new Color(118, 98, 79));
                g2.setFont(FontUtil.getNunito(10f, Font.PLAIN));
                g2.drawString(labels[i], xs[i] - 14, height - 8);
            }

            g2.dispose();
        }
    }

    private static class DonutPanel extends JPanel {
        private final int total;
        private final int[] values;
        private final Color[] colors;

        DonutPanel(int total, int[] values, Color[] colors) {
            this.total = total;
            this.values = values;
            this.colors = colors;
            setOpaque(false);
            setPreferredSize(new Dimension(170, 170));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int radius = 54;
            int start = -90;
            int sum = 0;
            for (int value : values) sum += value;
            if (sum == 0) sum = 1;

            for (int i = 0; i < values.length; i++) {
                int sweep = (int) Math.round((values[i] * 360.0) / sum);
                g2.setColor(colors[i]);
                g2.fillArc(cx - radius, cy - radius, radius * 2, radius * 2, start, sweep);
                start += sweep;
            }

            g2.setColor(new Color(248, 244, 239));
            g2.fillOval(cx - 38, cy - 38, 76, 76);

            g2.setColor(new Color(64, 52, 42));
            g2.setFont(FontUtil.getNunito(11f, Font.BOLD));
            g2.drawString("Total Orders", cx - 35, cy - 5);
            g2.setFont(FontUtil.getNunito(28f, Font.BOLD));
            g2.drawString("12", cx - 16, cy + 28);
            g2.dispose();
        }
    }
}


