package com.dineease.ui;

import com.dineease.service.UserSession;
import com.dineease.util.Theme;
import com.dineease.view.admin.Order.OrderManagementPanel;
import com.dineease.view.admin.billing.BillHistoryPanel;
import com.dineease.view.admin.billing.BillingPanel;
import com.dineease.view.admin.inventory.InventoryManagementPanel;
import com.dineease.view.admin.menu.MenuManagementPanel;
import com.dineease.view.admin.reports.ReportsPanel;
import com.dineease.view.admin.settings.SettingsPanel;
import com.dineease.view.admin.table.TableManagementPanel;
import com.dineease.view.admin.user.UserManagementPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final SidebarPanel sidebarPanel;
    private final DashboardPanel dashboardPanel;
    private final UserSession session;

    public MainFrame() {
        this(new UserSession("Administrator", "admin@dineease.com", "ADMIN"));
    }

    public MainFrame(UserSession session) {
        super("DineEase AI - Admin Dashboard");
        this.session = session;
        Theme.apply();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = Math.min(1536, Math.max(1280, bounds.width));
        int height = Math.min(1024, Math.max(850, bounds.height));
        setSize(width, height);
        setMinimumSize(new Dimension(1200, 750));
        setLocationRelativeTo(null);

        // Sidebar
        sidebarPanel = new SidebarPanel();
        sidebarPanel.setSession(session);
        add(sidebarPanel, BorderLayout.WEST);

        // Content Area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(247, 241, 229));

        // 1. Dashboard panel
        dashboardPanel = new DashboardPanel();
        contentPanel.add(dashboardPanel, "DASHBOARD");

        // Online Delivery (Jaipur)
        try {
            contentPanel.add(new com.dineease.ui.delivery.DeliveryManagementPanel(), "DELIVERY");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Online Delivery"), "DELIVERY");
        }

        // 2. Working CRUD module panels
        try {
            contentPanel.add(new UserManagementPanel(), "USERS");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("User Management"), "USERS");
        }

        try {
            contentPanel.add(new MenuManagementPanel(), "MENU");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Menu Management"), "MENU");
        }

        try {
            contentPanel.add(new InventoryManagementPanel(), "INVENTORY");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Inventory Management"), "INVENTORY");
        }

        try {
            contentPanel.add(new OrderManagementPanel(), "ORDERS");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Order Management"), "ORDERS");
        }

        try {
            contentPanel.add(new TableManagementPanel(), "TABLES");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Table Management"), "TABLES");
        }

        try {
            contentPanel.add(new BillingPanel(), "BILLING");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Billing"), "BILLING");
        }

        try {
            contentPanel.add(new BillHistoryPanel(), "BILL_HISTORY");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Bill History"), "BILL_HISTORY");
        }

        try {
            contentPanel.add(new ReportsPanel(), "REPORTS");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Reports"), "REPORTS");
        }

        try {
            contentPanel.add(new SettingsPanel(), "SETTINGS");
        } catch (Exception e) {
            contentPanel.add(createPlaceholder("Settings"), "SETTINGS");
        }

        add(contentPanel, BorderLayout.CENTER);

        // Navigation bindings
        sidebarPanel.setOnNavigate(this::navigateTo);
        dashboardPanel.setOnNavigate(this::navigateTo);

        cardLayout.show(contentPanel, "DASHBOARD");
        setVisible(true);
    }

    public void navigateTo(String key) {
        sidebarPanel.setActiveItem(key);
        cardLayout.show(contentPanel, key);

        // When switching back to DASHBOARD, reload data immediately to catch updates from other modules
        if ("DASHBOARD".equals(key)) {
            dashboardPanel.reloadAllData(false);
        }
    }

    private JPanel createPlaceholder(String title) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(247, 241, 229));
        JLabel lbl = new JLabel(title + " Module");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(new Color(90, 75, 60));
        p.add(lbl);
        return p;
    }
}

