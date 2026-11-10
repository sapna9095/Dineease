package com.dineease.view.admin;


import javax.swing.*;
import java.awt.*;

import com.dineease.view.admin.home.DashboardHomePanel;
import com.dineease.view.admin.user.UserManagementPanel;
import com.dineease.view.admin.menu.MenuManagementPanel;
import com.dineease.view.admin.inventory.InventoryManagementPanel;
import com.dineease.view.admin.reports.ReportsPanel;
import com.dineease.view.admin.settings.SettingsPanel;
import com.dineease.view.admin.billing.BillingPanel;
import com.dineease.view.admin.billing.BillHistoryPanel;
import com.dineease.util.UITheme;
import com.dineease.view.admin.table.TableManagementPanel;
import com.dineease.view.admin.Order.OrderManagementPanel;


public class AdminDashboard extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    // =========================
    // COLORS
    // =========================

    private static final Color SIDEBAR_COLOR =
            new Color(48, 40, 32);

    private static final Color SIDEBAR_TEXT =
            new Color(245, 239, 228);

    private static final Color SIDEBAR_MUTED =
            new Color(190, 178, 158);

    private static final Color GOLD =
            UITheme.PRIMARY;


    // =========================
    // CONSTRUCTOR
    // =========================

    public AdminDashboard() {

        setTitle(
                "DineEase AI - Admin Dashboard"
        );

        setSize(
                1280,
                750
        );

        setMinimumSize(
                new Dimension(
                        1100,
                        650
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );


        // =========================
        // SIDEBAR
        // =========================

        JPanel sidebar =
                createSidebar();

        add(
                sidebar,
                BorderLayout.WEST
        );


        // =========================
        // CONTENT AREA
        // =========================

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(
                        cardLayout
                );

        contentPanel.setBackground(
                UITheme.BACKGROUND
        );


        // =========================
        // HOME
        // =========================

        contentPanel.add(
                new DashboardHomePanel(),
                "HOME"
        );


        // =========================
        // USERS
        // =========================

        contentPanel.add(
                new UserManagementPanel(),
                "USERS"
        );


        // =========================
        // MENU
        // =========================

        contentPanel.add(
                new MenuManagementPanel(),
                "MENU"
        );


        // =========================
        // INVENTORY
        // =========================

        contentPanel.add(
                new InventoryManagementPanel(),
                "INVENTORY"
        );

        // =========================
// ORDERS
// =========================

        contentPanel.add(
                new OrderManagementPanel(),
                "ORDERS"
        );

        // =========================
// TABLE MANAGEMENT
// =========================

        contentPanel.add(
                new TableManagementPanel(),
                "TABLES"
        );


        // =========================
        // BILLING
        // =========================

        contentPanel.add(
                new BillingPanel(),
                "BILLING"
        );

        contentPanel.add(
                new BillHistoryPanel(),
                "BILL_HISTORY"
        );


        // =========================
        // REPORTS
        // =========================

        contentPanel.add(
                new ReportsPanel(),
                "REPORTS"
        );


        // =========================
        // SETTINGS
        // =========================

        contentPanel.add(
                new SettingsPanel(),
                "SETTINGS"
        );


        add(
                contentPanel,
                BorderLayout.CENTER
        );


        // =========================
        // SHOW HOME
        // =========================

        cardLayout.show(
                contentPanel,
                "HOME"
        );


        setVisible(true);

    }


    // =========================
    // CREATE SIDEBAR
    // =========================

    private JPanel createSidebar() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setPreferredSize(
                new Dimension(
                        255,
                        750
                )
        );

        panel.setBackground(
                SIDEBAR_COLOR
        );


        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                SIDEBAR_COLOR
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        25,
                        25,
                        25
                )
        );


        JLabel logo =
                new JLabel(
                        "DineEase"
                );

        logo.setForeground(
                GOLD
        );

        logo.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        30
                )
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel aiLabel =
                new JLabel(
                        "AI"
                );

        aiLabel.setForeground(
                Color.WHITE
        );

        aiLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        18
                )
        );

        aiLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel subtitle =
                new JLabel(
                        "RESTAURANT MANAGEMENT"
                );

        subtitle.setForeground(
                SIDEBAR_MUTED
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        header.add(
                logo
        );

        header.add(
                Box.createVerticalStrut(2)
        );

        header.add(
                aiLabel
        );

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(
                subtitle
        );


        panel.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // NAVIGATION
        // =========================

        JPanel navigation =
                new JPanel();

        navigation.setLayout(
                new GridLayout(
                        10,
                        1,
                        0,
                        8
                )
        );

        navigation.setBackground(
                SIDEBAR_COLOR
        );

        navigation.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );


        // =========================
        // BUTTONS
        // =========================

        JButton home =
                createSidebarButton(
                        "⌂   Dashboard"
                );

        JButton users =
                createSidebarButton(
                        "♙   User Management"
                );

        JButton menu =
                createSidebarButton(
                        "◆   Menu Management"
                );

        JButton inventory =
                createSidebarButton(
                        "▣   Inventory"
                );

        JButton orders =
                createSidebarButton(
                        "▤   Order Management"
                );

        JButton tables =
                createSidebarButton(
                        "▦   Table Management"
                );

        JButton billing =
                createSidebarButton(
                        "💳  Billing"
                );

        JButton billHistory =
                createSidebarButton(
                        "▤   Bill History"
                );

        JButton reports =
                createSidebarButton(
                        "▤   Reports"
                );

        JButton settings =
                createSidebarButton(
                        "⚙   Settings"
                );


        // =========================
        // ADD BUTTONS
        // =========================

        navigation.add(home);
        navigation.add(users);
        navigation.add(menu);
        navigation.add(inventory);
        navigation.add(orders);
        navigation.add(tables);
        navigation.add(billing);
        navigation.add(billHistory);
        navigation.add(reports);
        navigation.add(settings);


        panel.add(
                navigation,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        home.addActionListener(
                e -> showPage("HOME")
        );

        users.addActionListener(
                e -> showPage("USERS")
        );

        menu.addActionListener(
                e -> showPage("MENU")
        );

        inventory.addActionListener(
                e -> showPage("INVENTORY")
        );

        orders.addActionListener(
                e -> showPage("ORDERS")
        );

        tables.addActionListener(
                e -> showPage("TABLES")
        );

        billing.addActionListener(
                e -> showPage("BILLING")
        );

        billHistory.addActionListener(
                e -> showPage("BILL_HISTORY")
        );

        reports.addActionListener(
                e -> showPage("REPORTS")
        );

        settings.addActionListener(
                e -> showPage("SETTINGS")
        );


        // =========================
        // FOOTER
        // =========================

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                SIDEBAR_COLOR
        );

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        20,
                        20
                )
        );


        JLabel admin =
                new JLabel(
                        "Administrator"
                );

        admin.setForeground(
                SIDEBAR_TEXT
        );

        admin.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );


        JLabel version =
                new JLabel(
                        "DineEase AI • v1.0"
                );

        version.setForeground(
                SIDEBAR_MUTED
        );

        version.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );


        footer.add(
                admin,
                BorderLayout.NORTH
        );

        footer.add(
                version,
                BorderLayout.SOUTH
        );


        panel.add(
                footer,
                BorderLayout.SOUTH
        );


        return panel;

    }


    // =========================
    // SHOW PAGE
    // =========================

    private void showPage(
            String page
    ) {

        cardLayout.show(
                contentPanel,
                page
        );

    }


    // =========================
    // SIDEBAR BUTTON
    // =========================

    private JButton createSidebarButton(
            String text
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setForeground(
                SIDEBAR_TEXT
        );

        button.setBackground(
                SIDEBAR_COLOR
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        13,
                        15,
                        13,
                        15
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setOpaque(
                true
        );

        return button;

    }

}

