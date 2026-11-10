package com.dineease.ui;

import com.dineease.service.UserSession;
import com.dineease.ui.IconFactory;
import com.dineease.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {
    private static final Color BG_DARK = new Color(59, 36, 20); // Dark brown #3B2414 approx
    private static final Color GOLD = new Color(200, 144, 46);
    private static final Color ACTIVE_BG = new Color(138, 96, 42); // Rounded pill active
    private static final Color TEXT_MUTED = new Color(185, 168, 150);
    private static final Color TEXT_WHITE = Color.WHITE;

    private final List<NavItem> navItems = new ArrayList<>();
    private String activeKey = "DASHBOARD";
    private Consumer<String> onNavigate;
    private final JLabel userNameLabel;
    private final JLabel userRoleLabel;

    public SidebarPanel() {
        setPreferredSize(new Dimension(288, 0));
        setBackground(BG_DARK);
        setLayout(new BorderLayout());

        // Top: Logo block
        JPanel topBlock = new JPanel();
        topBlock.setOpaque(false);
        topBlock.setLayout(new BoxLayout(topBlock, BoxLayout.Y_AXIS));
        topBlock.setBorder(BorderFactory.createEmptyBorder(28, 24, 20, 24));

        JLabel logoDineEase = new JLabel("DineEase");
        logoDineEase.setFont(FontUtil.getPlayfairDisplay(34f, Font.BOLD));
        logoDineEase.setForeground(GOLD);
        logoDineEase.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logoAI = new JLabel("AI");
        logoAI.setFont(FontUtil.getPlayfairDisplay(22f, Font.BOLD));
        logoAI.setForeground(TEXT_WHITE);
        logoAI.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtext = new JLabel("RESTAURANT MANAGEMENT");
        subtext.setFont(new Font("Segoe UI", Font.BOLD, 10));
        subtext.setForeground(TEXT_MUTED);
        subtext.setAlignmentX(Component.LEFT_ALIGNMENT);

        topBlock.add(logoDineEase);
        topBlock.add(Box.createVerticalStrut(2));
        topBlock.add(logoAI);
        topBlock.add(Box.createVerticalStrut(4));
        topBlock.add(subtext);

        add(topBlock, BorderLayout.NORTH);

        // Center: Navigation menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        String[][] menuDefs = {
                {"Dashboard", "DASHBOARD", "home"},
                {"Online Delivery", "DELIVERY", "truck"},
                {"User Management", "USERS", "user"},
                {"Menu Management", "MENU", "utensils"},
                {"Inventory", "INVENTORY", "box"},
                {"Order Management", "ORDERS", "clipboard"},
                {"Table Management", "TABLES", "table"},
                {"Billing", "BILLING", "credit-card"},
                {"Bill History", "BILL_HISTORY", "file"},
                {"Reports", "REPORTS", "bar-chart"},
                {"Settings", "SETTINGS", "gear"}
        };

        for (String[] def : menuDefs) {
            NavItem item = new NavItem(def[0], def[1], def[2]);
            navItems.add(item);
            menuPanel.add(item);
            menuPanel.add(Box.createVerticalStrut(4));
        }

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: Thin divider, logged-in user name & role, version
        JPanel bottomBlock = new JPanel();
        bottomBlock.setOpaque(false);
        bottomBlock.setLayout(new BoxLayout(bottomBlock, BoxLayout.Y_AXIS));
        bottomBlock.setBorder(BorderFactory.createEmptyBorder(12, 20, 18, 20));

        JSeparator divider = new JSeparator();
        divider.setForeground(new Color(90, 65, 45));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);

        userNameLabel = new JLabel("Administrator");
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameLabel.setForeground(TEXT_WHITE);
        userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        userRoleLabel = new JLabel("DineEase AI • v1.0");
        userRoleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userRoleLabel.setForeground(TEXT_MUTED);
        userRoleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        bottomBlock.add(divider);
        bottomBlock.add(Box.createVerticalStrut(12));
        bottomBlock.add(userNameLabel);
        bottomBlock.add(Box.createVerticalStrut(4));
        bottomBlock.add(userRoleLabel);

        add(bottomBlock, BorderLayout.SOUTH);
    }

    public void setOnNavigate(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
    }

    public void setActiveItem(String key) {
        this.activeKey = key;
        for (NavItem item : navItems) {
            item.setActive(item.key.equals(key));
        }
    }

    public void setSession(UserSession session) {
        if (session != null) {
            String roleName = session.getRole();
            if (roleName == null || roleName.isEmpty()) roleName = "Administrator";
            userNameLabel.setText(roleName);
            userRoleLabel.setText("DineEase AI • v1.0");
        }
    }

    private class NavItem extends JPanel {
        private final String label;
        private final String key;
        private final String iconType;
        private boolean isActive = false;
        private boolean isHovered = false;

        private final JLabel iconLbl;
        private final JLabel textLbl;

        public NavItem(String label, String key, String iconType) {
            this.label = label;
            this.key = key;
            this.iconType = iconType;

            setLayout(new BorderLayout(14, 0));
            setOpaque(false);
            setPreferredSize(new Dimension(260, 42));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            iconLbl = new JLabel(getIconForType(iconType, TEXT_MUTED));
            textLbl = new JLabel(label);
            textLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            textLbl.setForeground(TEXT_MUTED);

            add(iconLbl, BorderLayout.WEST);
            add(textLbl, BorderLayout.CENTER);

            if (key.equals(activeKey)) {
                setActive(true);
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    setActiveItem(key);
                    if (onNavigate != null) {
                        onNavigate.accept(key);
                    }
                }
            });
        }

        public void setActive(boolean active) {
            this.isActive = active;
            if (active) {
                textLbl.setForeground(TEXT_WHITE);
                iconLbl.setIcon(getIconForType(iconType, GOLD));
            } else {
                textLbl.setForeground(TEXT_MUTED);
                iconLbl.setIcon(getIconForType(iconType, TEXT_MUTED));
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isActive) {
                g2.setColor(ACTIVE_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            } else if (isHovered) {
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static Icon getIconForType(String type, Color c) {
        int s = 17;
        switch (type) {
            case "home": return IconFactory.home(s, c);
            case "truck": return IconFactory.roomService(s, c);
            case "user": return IconFactory.user(s, c);
            case "utensils": return IconFactory.utensils(s, c);
            case "box": return IconFactory.cube(s, c);
            case "clipboard": return IconFactory.clipboard(s, c);
            case "table": return IconFactory.tableFurniture(s, c);
            case "credit-card": return IconFactory.creditCard(s, c);
            case "file": return IconFactory.fileDocument(s, c);
            case "bar-chart": return IconFactory.barChart(s, c);
            case "gear": return IconFactory.cog(s, c);
            default: return IconFactory.home(s, c);
        }
    }
}

