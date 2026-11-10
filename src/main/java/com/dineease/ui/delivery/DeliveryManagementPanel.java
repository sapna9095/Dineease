package com.dineease.ui.delivery;

import com.dineease.dao.DeliveryDAO;
import com.dineease.model.DeliveryOrder;
import com.dineease.ui.IconFactory;
import com.dineease.ui.components.RoundedPanel;
import com.dineease.ui.components.StatusBadge;
import com.dineease.util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class DeliveryManagementPanel extends JPanel {

    private static final Color BG_CREAM = new Color(247, 241, 229);
    private static final Color GOLD = new Color(200, 144, 46);
    private static final Color TEXT_DARK = new Color(43, 27, 18);
    private static final Color TEXT_MUTED = new Color(110, 95, 80);

    private final DeliveryDAO deliveryDAO = new DeliveryDAO();
    private final DefaultTableModel ordersTableModel;
    private final JTable ordersTable;
    private final JaipurDeliveryMapPanel mapPanel;

    private final JLabel customerNameLabel = new JLabel("-");
    private final JLabel customerPhoneLabel = new JLabel("-");
    private final JLabel deliveryAreaLabel = new JLabel("-");
    private final JLabel fullAddressLabel = new JLabel("-");
    private final JLabel itemsSummaryLabel = new JLabel("-");
    private final JLabel totalAmountLabel = new JLabel("₹ 0");
    private final JComboBox<String> statusDropdown;

    private List<DeliveryOrder> cachedOrders;
    private DeliveryOrder selectedOrder;
    private Timer riderAnimationTimer;

    public DeliveryManagementPanel() {
        setBackground(BG_CREAM);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(24, 30, 24, 30));

        // ==========================================
        // HEADER
        // ==========================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Jaipur Online Delivery & Live Tracking");
        title.setFont(FontUtil.getPlayfairDisplay(28f, Font.BOLD));
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Express delivery coverage for Sitapura, Mansarovar, Malviya Nagar, Vaishali Nagar & Raja Park");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerButtons.setOpaque(false);

        JButton newOrderBtn = new JButton("+ New Delivery Order");
        newOrderBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        newOrderBtn.setBackground(GOLD);
        newOrderBtn.setForeground(Color.WHITE);
        newOrderBtn.setFocusPainted(false);
        newOrderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newOrderBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        newOrderBtn.addActionListener(e -> showNewOrderDialog());

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setIcon(IconFactory.refresh(15, Color.WHITE));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(new Color(110, 85, 60));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        refreshBtn.addActionListener(e -> reloadDeliveryData());

        headerButtons.add(newOrderBtn);
        headerButtons.add(refreshBtn);

        headerPanel.add(titleBlock, BorderLayout.WEST);
        headerPanel.add(headerButtons, BorderLayout.EAST);
        root.add(headerPanel);
        root.add(Box.createVerticalStrut(20));

        // ==========================================
        // MAIN BODY: Two Columns (Left Table + Right Map & Details)
        // ==========================================
        JPanel mainRow = new JPanel(new GridLayout(1, 2, 20, 0));
        mainRow.setOpaque(false);

        // 1. LEFT PANEL: Active Jaipur Delivery Orders List
        RoundedPanel listCard = new RoundedPanel(14, new Color(251, 246, 234));
        listCard.setLayout(new BorderLayout());
        listCard.setBorder(new EmptyBorder(18, 20, 16, 20));

        JPanel listHead = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        listHead.setOpaque(false);
        listHead.add(new JLabel(IconFactory.clipboard(18, GOLD)));
        JLabel listTitle = new JLabel("Jaipur Delivery Orders");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        listTitle.setForeground(TEXT_DARK);
        listHead.add(listTitle);
        listCard.add(listHead, BorderLayout.NORTH);

        ordersTableModel = new DefaultTableModel(new String[]{"ID", "Customer", "Area", "Amount", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        ordersTable = new JTable(ordersTableModel);
        ordersTable.setRowHeight(36);
        ordersTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ordersTable.setGridColor(new Color(236, 228, 218));
        ordersTable.setShowVerticalLines(false);
        ordersTable.setSelectionBackground(new Color(238, 226, 206));
        ordersTable.setSelectionForeground(TEXT_DARK);

        ordersTable.getTableHeader().setPreferredSize(new Dimension(0, 32));
        ordersTable.getTableHeader().setBackground(new Color(245, 238, 224));
        ordersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        ordersTable.getTableHeader().setForeground(new Color(90, 75, 60));

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        centerRender.setHorizontalAlignment(SwingConstants.CENTER);
        ordersTable.setDefaultRenderer(Object.class, centerRender);

        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = ordersTable.getSelectedRow();
                if (row >= 0 && cachedOrders != null && row < cachedOrders.size()) {
                    selectOrder(cachedOrders.get(row));
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(ordersTable);
        tableScroll.setOpaque(false);
        tableScroll.getViewport().setOpaque(false);
        tableScroll.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        listCard.add(tableScroll, BorderLayout.CENTER);

        // 2. RIGHT PANEL: Live Jaipur GPS Map + Order Details Card
        JPanel rightCol = new JPanel();
        rightCol.setOpaque(false);
        rightCol.setLayout(new BoxLayout(rightCol, BoxLayout.Y_AXIS));

        mapPanel = new JaipurDeliveryMapPanel();

        RoundedPanel detailsCard = new RoundedPanel(14, new Color(251, 246, 234));
        detailsCard.setLayout(new BorderLayout());
        detailsCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JPanel dHead = new JPanel(new BorderLayout());
        dHead.setOpaque(false);
        JLabel dTitle = new JLabel("Live Order Details & Dispatch");
        dTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        dTitle.setForeground(TEXT_DARK);

        statusDropdown = new JComboBox<>(new String[]{"Order Placed", "Preparing", "Out for Delivery", "Delivered", "Cancelled"});
        statusDropdown.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusDropdown.setBackground(Color.WHITE);
        statusDropdown.setFocusable(false);

        JButton updateStatusBtn = new JButton("Update Status");
        updateStatusBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        updateStatusBtn.setBackground(GOLD);
        updateStatusBtn.setForeground(Color.WHITE);
        updateStatusBtn.setFocusPainted(false);
        updateStatusBtn.setBorder(new EmptyBorder(6, 12, 6, 12));
        updateStatusBtn.addActionListener(e -> updateSelectedOrderStatus());

        JPanel statusControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        statusControls.setOpaque(false);
        statusControls.add(statusDropdown);
        statusControls.add(updateStatusBtn);

        dHead.add(dTitle, BorderLayout.WEST);
        dHead.add(statusControls, BorderLayout.EAST);
        detailsCard.add(dHead, BorderLayout.NORTH);

        JPanel detailsGrid = new JPanel(new GridLayout(3, 2, 12, 8));
        detailsGrid.setOpaque(false);
        detailsGrid.setBorder(new EmptyBorder(12, 4, 8, 4));

        customerNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        customerPhoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        deliveryAreaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deliveryAreaLabel.setForeground(new Color(197, 139, 43));
        fullAddressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemsSummaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalAmountLabel.setForeground(new Color(46, 158, 91));

        detailsGrid.add(makeLabeled("Customer:", customerNameLabel));
        detailsGrid.add(makeLabeled("Phone:", customerPhoneLabel));
        detailsGrid.add(makeLabeled("Jaipur Area:", deliveryAreaLabel));
        detailsGrid.add(makeLabeled("Total Amount:", totalAmountLabel));
        detailsGrid.add(makeLabeled("Address:", fullAddressLabel));
        detailsGrid.add(makeLabeled("Items Ordered:", itemsSummaryLabel));

        detailsCard.add(detailsGrid, BorderLayout.CENTER);

        rightCol.add(mapPanel);
        rightCol.add(Box.createVerticalStrut(14));
        rightCol.add(detailsCard);

        mainRow.add(listCard);
        mainRow.add(rightCol);
        root.add(mainRow);

        JScrollPane scrollPane = new JScrollPane(root);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Start live GPS rider simulation timer
        riderAnimationTimer = new Timer(1500, e -> mapPanel.advanceRider());
        riderAnimationTimer.start();

        reloadDeliveryData();
    }

    private JPanel makeLabeled(String caption, JLabel value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        p.setOpaque(false);
        JLabel cap = new JLabel(caption);
        cap.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cap.setForeground(TEXT_MUTED);
        p.add(cap);
        p.add(value);
        return p;
    }

    private void selectOrder(DeliveryOrder order) {
        this.selectedOrder = order;
        if (order == null) return;
        customerNameLabel.setText(order.getCustomerName());
        customerPhoneLabel.setText(order.getCustomerPhone());
        deliveryAreaLabel.setText(order.getArea() + " (Jaipur)");
        fullAddressLabel.setText(order.getFullAddress());
        itemsSummaryLabel.setText(order.getItemsSummary());
        DecimalFormat df = new DecimalFormat("#,##,##0.00");
        totalAmountLabel.setText("₹ " + df.format(order.getTotalAmount()));
        statusDropdown.setSelectedItem(order.getStatus());
        mapPanel.setSelectedOrder(order);
    }

    public void reloadDeliveryData() {
        SwingWorker<List<DeliveryOrder>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<DeliveryOrder> doInBackground() {
                return deliveryDAO.getAllDeliveryOrders();
            }

            @Override
            protected void done() {
                try {
                    cachedOrders = get();
                    ordersTableModel.setRowCount(0);
                    DecimalFormat df = new DecimalFormat("#,##0");
                    for (DeliveryOrder o : cachedOrders) {
                        ordersTableModel.addRow(new Object[]{
                                "#D" + o.getId(),
                                o.getCustomerName(),
                                o.getArea(),
                                "₹ " + df.format(o.getTotalAmount()),
                                o.getStatus()
                        });
                    }
                    if (!cachedOrders.isEmpty() && (selectedOrder == null || ordersTable.getSelectedRow() < 0)) {
                        ordersTable.setRowSelectionInterval(0, 0);
                        selectOrder(cachedOrders.get(0));
                    }
                } catch (Exception ex) {
                    System.err.println("Failed to reload delivery orders: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void updateSelectedOrderStatus() {
        if (selectedOrder == null) return;
        String newStatus = (String) statusDropdown.getSelectedItem();
        if (newStatus == null) return;

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return deliveryDAO.updateStatus(selectedOrder.getId(), newStatus);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        selectedOrder.setStatus(newStatus);
                        reloadDeliveryData();
                    }
                } catch (Exception ignored) {}
            }
        };
        worker.execute();
    }

    private void showNewOrderDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create Jaipur Delivery Order", true);
        dialog.setSize(480, 520);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(251, 246, 234));
        form.setBorder(new EmptyBorder(20, 24, 20, 24));

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JComboBox<String> areaCombo = new JComboBox<>(new String[]{"Sitapura", "Mansarovar", "Malviya Nagar", "Vaishali Nagar", "Raja Park"});
        JTextField addressField = new JTextField();
        JTextField itemsField = new JTextField("1x Dal Makhani, 2x Butter Naan");
        JTextField amountField = new JTextField("350.00");

        addDialogRow(form, 0, "Customer Name:", nameField);
        addDialogRow(form, 1, "Phone Number:", phoneField);
        addDialogRow(form, 2, "Jaipur Delivery Area:", areaCombo);
        addDialogRow(form, 3, "Full Address:", addressField);
        addDialogRow(form, 4, "Order Items:", itemsField);
        addDialogRow(form, 5, "Total Amount (₹):", amountField);

        JButton createBtn = new JButton("Confirm Order");
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        createBtn.setBackground(GOLD);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String area = (String) areaCombo.getSelectedItem();
            String address = addressField.getText().trim();
            String items = itemsField.getText().trim();
            double amt;
            try {
                amt = Double.parseDouble(amountField.getText().trim());
            } catch (Exception ex) {
                amt = 250.00;
            }

            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter customer name and delivery address.");
                return;
            }

            DeliveryOrder order = new DeliveryOrder(
                    0, name, phone, area, address, items, amt, "Out for Delivery",
                    26.7818, 75.8234, null
            );

            deliveryDAO.createDeliveryOrder(order);
            dialog.dispose();
            reloadDeliveryData();
        });

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBar.setBackground(new Color(251, 246, 234));
        btnBar.add(createBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnBar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void addDialogRow(JPanel panel, int row, String label, JComponent comp) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 4, 6, 12);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        panel.add(l, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 4);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comp.setPreferredSize(new Dimension(0, 32));
        panel.add(comp, gbc);
    }
}

