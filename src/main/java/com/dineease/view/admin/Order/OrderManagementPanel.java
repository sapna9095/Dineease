package com.dineease.view.admin.Order;

import com.dineease.controller.OrderController;
import com.dineease.model.Order;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderManagementPanel extends JPanel {

    private OrderController orderController;

    private JTable orderTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> statusComboBox;

    private JButton searchButton;
    private JButton refreshButton;
    private JButton viewButton;
    private JButton updateStatusButton;
    private JButton deleteButton;

    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel completedLabel;

    private List<Order> allOrders;


    // =========================
    // CONSTRUCTOR
    // =========================

    public OrderManagementPanel() {

        orderController =
                new OrderController();

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        setBackground(
                UITheme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        createTopPanel();

        createSummaryPanel();

        createOrderTable();

        createBottomPanel();

        loadOrders();
    }


    // =========================
    // TOP PANEL
    // =========================

    private void createTopPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        panel.setBackground(
                UITheme.BACKGROUND
        );


        JPanel titlePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        titlePanel.setBackground(
                UITheme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Order Management"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                UITheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Manage restaurant orders and order status"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                Color.GRAY
        );


        titlePanel.add(title);
        titlePanel.add(subtitle);


        panel.add(
                titlePanel,
                BorderLayout.WEST
        );


        // =========================
        // SEARCH
        // =========================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                5
                        )
                );

        searchPanel.setBackground(
                UITheme.BACKGROUND
        );


        searchPanel.add(
                new JLabel("Search Order:")
        );


        searchField =
                new JTextField(
                        10
                );

        searchField.setToolTipText(
                "Search by Order ID or Table ID"
        );


        searchPanel.add(
                searchField
        );


        searchButton =
                new JButton(
                        "Search"
                );

        UITheme.stylePrimaryButton(
                searchButton
        );


        searchPanel.add(
                searchButton
        );


        searchPanel.add(
                new JLabel("Status:")
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Pending",
                                "Preparing",
                                "Ready",
                                "Served",
                                "Cancelled"
                        }
                );


        searchPanel.add(
                statusComboBox
        );


        panel.add(
                searchPanel,
                BorderLayout.EAST
        );


        add(
                panel,
                BorderLayout.NORTH
        );


        // =========================
        // ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> filterOrders()
        );


        searchField.addActionListener(
                e -> filterOrders()
        );


        statusComboBox.addActionListener(
                e -> filterOrders()
        );
    }


    // =========================
    // SUMMARY PANEL
    // =========================

    private void createSummaryPanel() {

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        summaryPanel.setBackground(
                UITheme.BACKGROUND
        );


        totalLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        pendingLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        completedLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        summaryPanel.add(
                createSummaryCard(
                        "Total Orders",
                        totalLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Pending Orders",
                        pendingLabel
                )
        );

        summaryPanel.add(
                createSummaryCard(
                        "Served Orders",
                        completedLabel
                )
        );





        add(
                summaryPanel,
                BorderLayout.BEFORE_FIRST_LINE
        );
    }


    // =========================
    // SUMMARY CARD
    // =========================

    private JPanel createSummaryCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        card.setBackground(
                UITheme.CARD
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                15,
                                12,
                                15
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        titleLabel.setForeground(
                Color.GRAY
        );


        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        valueLabel.setForeground(
                UITheme.TEXT
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================
    // ORDER TABLE
    // =========================

    private void createOrderTable() {

        String[] columns = {

                "Order ID",
                "Table ID",
                "Order Date",
                "Status",
                "Subtotal",
                "Tax",
                "Total Amount"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        orderTable =
                new JTable(
                        tableModel
                );


        orderTable.setRowHeight(
                32
        );


        orderTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        orderTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        orderTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        // =========================
        // STATUS COLUMN
        // =========================

        orderTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new StatusCellRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        orderTable
                );


        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );


        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }


    // =========================
    // STATUS RENDERER
    // =========================

    private static class StatusCellRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (!isSelected && value != null) {

                String status =
                        value.toString();


                if (
                        status.equalsIgnoreCase(
                                "Served"
                        )
                ){

                    setForeground(
                            new Color(
                                    30,
                                    130,
                                    70
                            )
                    );

                }
                else if (
                        status.equalsIgnoreCase(
                                "Pending"
                        )
                ) {

                    setForeground(
                            new Color(
                                    200,
                                    140,
                                    20
                            )
                    );

                }
                else if (
                        status.equalsIgnoreCase(
                                "Cancelled"
                        )
                ) {

                    setForeground(
                            new Color(
                                    200,
                                    50,
                                    50
                            )
                    );

                }
                else {

                    setForeground(
                            UITheme.TEXT
                    );
                }
            }


            return component;
        }
    }


    // =========================
    // BOTTOM PANEL
    // =========================

    private void createBottomPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        panel.setBackground(
                UITheme.BACKGROUND
        );


        refreshButton =
                new JButton(
                        "Refresh"
                );


        viewButton =
                new JButton(
                        "View Order"
                );


        updateStatusButton =
                new JButton(
                        "Update Status"
                );


        deleteButton =
                new JButton(
                        "Delete Order"
                );


        UITheme.styleSecondaryButton(
                refreshButton
        );


        UITheme.stylePrimaryButton(
                viewButton
        );


        UITheme.styleSecondaryButton(
                updateStatusButton
        );


        UITheme.styleSecondaryButton(
                deleteButton
        );


        panel.add(
                refreshButton
        );

        panel.add(
                viewButton
        );

        panel.add(
                updateStatusButton
        );

        panel.add(
                deleteButton
        );


        // =========================
        // ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    statusComboBox.setSelectedItem(
                            "All"
                    );

                    loadOrders();
                }
        );


        viewButton.addActionListener(
                e -> viewSelectedOrder()
        );


        updateStatusButton.addActionListener(
                e -> updateSelectedOrderStatus()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedOrder()
        );


        add(
                panel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD ORDERS
    // =========================

    private void loadOrders() {

        allOrders =
                orderController.getAllOrders();


        if (
                allOrders == null
        ) {

            allOrders =
                    new java.util.ArrayList<>();
        }


        displayOrders(
                allOrders
        );


        updateSummary(
                allOrders
        );
    }


    // =========================
    // DISPLAY ORDERS
    // =========================

    private void displayOrders(
            List<Order> orders
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                Order order :
                orders
        ) {

            Object[] row = {

                    order.getOrderId(),

                    order.getTableId(),

                    order.getOrderDate(),

                    order.getOrderStatus(),

                    String.format(
                            "₹%.2f",
                            order.getSubtotal()
                    ),

                    String.format(
                            "₹%.2f",
                            order.getTax()
                    ),

                    String.format(
                            "₹%.2f",
                            order.getTotalAmount()
                    )
            };


            tableModel.addRow(
                    row
            );
        }
    }


    // =========================
    // UPDATE SUMMARY
    // =========================

    private void updateSummary(
            List<Order> orders
    ) {

        int total =
                orders.size();

        int pending = 0;

        int completed = 0;


        for (
                Order order :
                orders
        ) {

            if (
                    "Pending".equalsIgnoreCase(
                            order.getOrderStatus()
                    )
            ) {

                pending++;
            }


            if (
                    "Served".equalsIgnoreCase(
                            order.getOrderStatus()
                    )
            ) {

                completed++;
            }
        }


        totalLabel.setText(
                String.valueOf(total)
        );


        pendingLabel.setText(
                String.valueOf(pending)
        );


        completedLabel.setText(
                String.valueOf(completed)
        );
    }


    // =========================
    // FILTER ORDERS
    // =========================

    private void filterOrders() {

        if (
                allOrders == null
        ) {

            return;
        }


        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String selectedStatus =
                statusComboBox
                        .getSelectedItem()
                        .toString();


        List<Order> filteredOrders =
                allOrders
                        .stream()
                        .filter(
                                order -> {

                                    boolean searchMatch =
                                            searchText.isEmpty()
                                                    ||
                                                    String.valueOf(
                                                                    order.getOrderId()
                                                            )
                                                            .contains(
                                                                    searchText
                                                            )
                                                    ||
                                                    String.valueOf(
                                                                    order.getTableId()
                                                            )
                                                            .contains(
                                                                    searchText
                                                            );


                                    boolean statusMatch =
                                            selectedStatus.equals(
                                                    "All"
                                            )
                                                    ||
                                                    order.getOrderStatus()
                                                            .equalsIgnoreCase(
                                                                    selectedStatus
                                                            );


                                    return searchMatch
                                            &&
                                            statusMatch;
                                }
                        )
                        .toList();


        displayOrders(
                filteredOrders
        );
    }


    // =========================
    // GET SELECTED ORDER
    // =========================

    private Order getSelectedOrder() {

        int row =
                orderTable.getSelectedRow();


        if (
                row < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an order first."
            );

            return null;
        }


        int orderId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );


        for (
                Order order :
                allOrders
        ) {

            if (
                    order.getOrderId()
                            == orderId
            ) {

                return order;
            }
        }


        return null;
    }


    // =========================
    // VIEW ORDER
    // =========================


    private void viewSelectedOrder() {

        Order selected = getSelectedOrder();

        if (selected == null) {
            return;
        }

        List<com.dineease.model.OrderItem> items =
                orderController.getOrderItems(
                        selected.getOrderId()
                );

        StringBuilder message =
                new StringBuilder();

        message.append("ORDER DETAILS\n");
        message.append("==============================\n\n");

        message.append("Order ID: ")
                .append(selected.getOrderId())
                .append("\n");

        message.append("Table ID: ")
                .append(selected.getTableId())
                .append("\n");

        message.append("Order Date: ")
                .append(selected.getOrderDate())
                .append("\n");

        message.append("Status: ")
                .append(selected.getOrderStatus())
                .append("\n\n");


        // =========================
        // ORDER ITEMS
        // =========================

        message.append("ORDER ITEMS\n");
        message.append("------------------------------\n");


        if (items == null || items.isEmpty()) {

            message.append("No items found.\n");

        } else {

            for (com.dineease.model.OrderItem item : items) {

                message.append(
                                "Item: "
                        )
                        .append(
                                item.getItemName()
                        )
                        .append("\n");

                message.append(
                                "Quantity: "
                        )
                        .append(
                                item.getQuantity()
                        )
                        .append("\n");

                message.append(
                                "Price: ₹"
                        )
                        .append(
                                String.format(
                                        "%.2f",
                                        item.getPrice()
                                )
                        )
                        .append("\n");

                message.append(
                                "Total: ₹"
                        )
                        .append(
                                String.format(
                                        "%.2f",
                                        item.getTotalPrice()
                                )
                        )
                        .append("\n");

                message.append(
                        "------------------------------\n"
                );
            }
        }


        // =========================
        // BILL SUMMARY
        // =========================

        message.append("\nBILL SUMMARY\n");
        message.append("------------------------------\n");

        message.append("Subtotal: ₹")
                .append(
                        String.format(
                                "%.2f",
                                selected.getSubtotal()
                        )
                )
                .append("\n");

        message.append("Tax: ₹")
                .append(
                        String.format(
                                "%.2f",
                                selected.getTax()
                        )
                )
                .append("\n");

        message.append("TOTAL: ₹")
                .append(
                        String.format(
                                "%.2f",
                                selected.getTotalAmount()
                        )
                );


        // =========================
        // TEXT AREA
        // =========================

        JTextArea textArea =
                new JTextArea(
                        message.toString()
                );

        textArea.setEditable(false);

        textArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        textArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        // =========================
        // SCROLL PANE
        // =========================

        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        450,
                        500
                )
        );


        // =========================
        // SHOW DIALOG
        // =========================

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Order Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }



    // =========================
    // UPDATE ORDER STATUS
    // =========================

    private void updateSelectedOrderStatus() {

        Order selected =
                getSelectedOrder();


        if (
                selected == null
        ) {

            return;
        }


        String[] statuses = {

                "Pending",
                "Preparing",
                "Ready",
                "Served",
                "Cancelled"
        };


        JComboBox<String> statusCombo =
                new JComboBox<>(
                        statuses
                );


        statusCombo.setSelectedItem(
                selected.getOrderStatus()
        );


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        statusCombo,
                        "Update Order Status",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result != JOptionPane.OK_OPTION
        ) {

            return;
        }


        String newStatus =
                statusCombo
                        .getSelectedItem()
                        .toString();


        boolean success =
                orderController.updateOrderStatus(
                        selected.getOrderId(),
                        newStatus
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order status updated successfully."
            );

            loadOrders();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update order status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // DELETE ORDER
    // =========================

    private void deleteSelectedOrder() {

        Order selected =
                getSelectedOrder();


        if (
                selected == null
        ) {

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete Order #"
                                + selected.getOrderId()
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                orderController.deleteOrder(
                        selected.getOrderId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order deleted successfully."
            );

            loadOrders();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete order.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}