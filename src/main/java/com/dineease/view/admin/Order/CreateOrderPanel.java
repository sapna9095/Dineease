package com.dineease.view.admin.Order;

import com.dineease.controller.OrderController;
import com.dineease.model.Order;
import com.dineease.model.OrderItem;
import com.dineease.util.DBConnection;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderPanel extends JPanel {

    private OrderController orderController;

    // =========================
    // UI COMPONENTS
    // =========================

    private JComboBox<TableOption> tableComboBox;
    private JComboBox<MenuItemOption> menuItemComboBox;

    private JSpinner quantitySpinner;

    private JLabel priceLabel;
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;

    private JTable orderItemTable;
    private DefaultTableModel tableModel;

    private JButton addItemButton;
    private JButton removeItemButton;
    private JButton clearButton;
    private JButton saveOrderButton;


    // =========================
    // CART
    // =========================

    private List<OrderItem> cartItems =
            new ArrayList<>();


    // =========================
    // TAX
    // =========================

    private static final double TAX_RATE = 0.05;


    // =========================
    // CONSTRUCTOR
    // =========================

    public CreateOrderPanel() {

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

        createOrderEntryPanel();

        createOrderItemTable();

        createBottomPanel();

        loadTables();

        loadMenuItems();

        updateTotals();
    }


    // =========================
    // TOP PANEL
    // =========================

    private void createTopPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                UITheme.BACKGROUND
        );

        JLabel title =
                new JLabel(
                        "Create New Order"
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
                        "Create a new restaurant order"
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

        titlePanel.add(title);

        titlePanel.add(subtitle);

        panel.add(
                titlePanel,
                BorderLayout.WEST
        );

        add(
                panel,
                BorderLayout.NORTH
        );
    }


    // =========================
    // ORDER ENTRY PANEL
    // =========================

    private void createOrderEntryPanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                UITheme.CARD
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        5,
                        8,
                        5,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;


        // =========================
        // TABLE
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(
                new JLabel("Select Table:"),
                gbc
        );


        tableComboBox =
                new JComboBox<>();

        tableComboBox.setPreferredSize(
                new Dimension(
                        220,
                        32
                )
        );

        gbc.gridx = 1;

        panel.add(
                tableComboBox,
                gbc
        );


        // =========================
        // MENU ITEM
        // =========================

        gbc.gridx = 2;

        panel.add(
                new JLabel("Menu Item:"),
                gbc
        );


        menuItemComboBox =
                new JComboBox<>();

        menuItemComboBox.setPreferredSize(
                new Dimension(
                        220,
                        32
                )
        );

        gbc.gridx = 3;

        panel.add(
                menuItemComboBox,
                gbc
        );


        // =========================
        // QUANTITY
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(
                new JLabel("Quantity:"),
                gbc
        );


        quantitySpinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                1,
                                1,
                                100,
                                1
                        )
                );

        quantitySpinner.setPreferredSize(
                new Dimension(
                        100,
                        32
                )
        );

        gbc.gridx = 1;

        panel.add(
                quantitySpinner,
                gbc
        );


        // =========================
        // PRICE
        // =========================

        gbc.gridx = 2;

        panel.add(
                new JLabel("Price:"),
                gbc
        );


        priceLabel =
                new JLabel(
                        "₹0.00"
                );

        priceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 3;

        panel.add(
                priceLabel,
                gbc
        );


        // =========================
        // ADD BUTTON
        // =========================

        addItemButton =
                new JButton(
                        "Add Item"
                );

        UITheme.stylePrimaryButton(
                addItemButton
        );

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 2;

        panel.add(
                addItemButton,
                gbc
        );


        add(
                panel,
                BorderLayout.BEFORE_FIRST_LINE
        );


        // =========================
        // MENU ITEM CHANGE
        // =========================

        menuItemComboBox.addActionListener(
                e -> updateSelectedPrice()
        );


        // =========================
        // QUANTITY CHANGE
        // =========================

        quantitySpinner.addChangeListener(
                e -> updateSelectedPrice()
        );


        // =========================
        // ADD ITEM
        // =========================

        addItemButton.addActionListener(
                e -> addItemToCart()
        );
    }


    // =========================
    // ORDER ITEM TABLE
    // =========================

    private void createOrderItemTable() {

        String[] columns = {

                "Item ID",
                "Item Name",
                "Quantity",
                "Price",
                "Total"
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


        orderItemTable =
                new JTable(
                        tableModel
                );

        orderItemTable.setRowHeight(
                30
        );

        orderItemTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        orderItemTable
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
    // BOTTOM PANEL
    // =========================

    private void createBottomPanel() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );


        // =========================
        // LEFT BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                5
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        removeItemButton =
                new JButton(
                        "Remove Item"
                );

        clearButton =
                new JButton(
                        "Clear Order"
                );

        saveOrderButton =
                new JButton(
                        "Save Order"
                );


        UITheme.styleSecondaryButton(
                removeItemButton
        );

        UITheme.styleSecondaryButton(
                clearButton
        );

        UITheme.stylePrimaryButton(
                saveOrderButton
        );


        buttonPanel.add(
                removeItemButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                saveOrderButton
        );


        mainPanel.add(
                buttonPanel,
                BorderLayout.WEST
        );


        // =========================
        // TOTALS
        // =========================

        JPanel totalsPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                3
                        )
                );

        totalsPanel.setBackground(
                UITheme.BACKGROUND
        );


        totalsPanel.add(
                new JLabel("Subtotal:")
        );

        subtotalLabel =
                new JLabel(
                        "₹0.00"
                );

        totalsPanel.add(
                subtotalLabel
        );


        totalsPanel.add(
                new JLabel("Tax (5%):")
        );

        taxLabel =
                new JLabel(
                        "₹0.00"
                );

        totalsPanel.add(
                taxLabel
        );


        totalsPanel.add(
                new JLabel("Total:")
        );

        totalLabel =
                new JLabel(
                        "₹0.00"
                );

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        totalsPanel.add(
                totalLabel
        );


        mainPanel.add(
                totalsPanel,
                BorderLayout.EAST
        );


        add(
                mainPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // ACTIONS
        // =========================

        removeItemButton.addActionListener(
                e -> removeSelectedItem()
        );

        clearButton.addActionListener(
                e -> clearOrder()
        );

        saveOrderButton.addActionListener(
                e -> saveOrder()
        );
    }


    // =========================
    // LOAD TABLES
    // =========================

    private void loadTables() {

        tableComboBox.removeAllItems();

        String sql =
                "SELECT table_id, table_number, capacity " +
                        "FROM restaurant_tables " +
                        "WHERE status='Available' " +
                        "ORDER BY table_number";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                tableComboBox.addItem(
                        new TableOption(
                                rs.getInt("table_id"),
                                rs.getInt("table_number"),
                                rs.getInt("capacity")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load restaurant tables.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // LOAD MENU ITEMS
    // =========================

    private void loadMenuItems() {

        menuItemComboBox.removeAllItems();

        String sql =
                "SELECT item_id, item_name, price " +
                        "FROM menu_items " +
                        "WHERE status='Available' " +
                        "ORDER BY item_name";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                menuItemComboBox.addItem(
                        new MenuItemOption(
                                rs.getInt("item_id"),
                                rs.getString("item_name"),
                                rs.getDouble("price")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load menu items.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // UPDATE PRICE
    // =========================

    private void updateSelectedPrice() {

        MenuItemOption item =
                (MenuItemOption)
                        menuItemComboBox.getSelectedItem();

        if (item == null) {

            priceLabel.setText(
                    "₹0.00"
            );

            return;
        }

        priceLabel.setText(
                String.format(
                        "₹%.2f",
                        item.price
                )
        );
    }


    // =========================
    // ADD ITEM TO CART
    // =========================

    private void addItemToCart() {

        MenuItemOption menuItem =
                (MenuItemOption)
                        menuItemComboBox.getSelectedItem();

        if (menuItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a menu item."
            );

            return;
        }


        int quantity =
                (Integer)
                        quantitySpinner.getValue();


        int totalQuantity =
                quantity;


        OrderItem existingItem = null;

        for (
                OrderItem item :
                cartItems
        ) {

            if (
                    item.getItemId()
                            == menuItem.itemId
            ) {

                existingItem = item;

                totalQuantity =
                        item.getQuantity()
                                + quantity;

                break;
            }
        }


        double totalPrice =
                menuItem.price
                        * totalQuantity;


        if (existingItem != null) {

            existingItem.setQuantity(
                    totalQuantity
            );

            existingItem.setTotalPrice(
                    totalPrice
            );

        } else {

            OrderItem item =
                    new OrderItem();

            item.setItemId(
                    menuItem.itemId
            );

            item.setItemName(
                    menuItem.itemName
            );

            item.setQuantity(
                    quantity
            );

            item.setPrice(
                    menuItem.price
            );

            item.setTotalPrice(
                    menuItem.price
                            * quantity
            );

            cartItems.add(
                    item
            );
        }


        refreshCartTable();

        updateTotals();
    }


    // =========================
    // REFRESH CART TABLE
    // =========================

    private void refreshCartTable() {

        tableModel.setRowCount(
                0
        );


        for (
                OrderItem item :
                cartItems
        ) {

            tableModel.addRow(
                    new Object[]{

                            item.getItemId(),

                            item.getItemName(),

                            item.getQuantity(),

                            String.format(
                                    "₹%.2f",
                                    item.getPrice()
                            ),

                            String.format(
                                    "₹%.2f",
                                    item.getTotalPrice()
                            )
                    }
            );
        }
    }


    // =========================
    // UPDATE TOTALS
    // =========================

    private void updateTotals() {

        double subtotal = 0;

        for (
                OrderItem item :
                cartItems
        ) {

            subtotal +=
                    item.getTotalPrice();
        }


        double tax =
                subtotal
                        * TAX_RATE;


        double total =
                subtotal
                        + tax;


        subtotalLabel.setText(
                String.format(
                        "₹%.2f",
                        subtotal
                )
        );

        taxLabel.setText(
                String.format(
                        "₹%.2f",
                        tax
                )
        );

        totalLabel.setText(
                String.format(
                        "₹%.2f",
                        total
                )
        );
    }


    // =========================
    // REMOVE SELECTED ITEM
    // =========================

    private void removeSelectedItem() {

        int row =
                orderItemTable.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item first."
            );

            return;
        }


        cartItems.remove(
                row
        );

        refreshCartTable();

        updateTotals();
    }


    // =========================
    // CLEAR ORDER
    // =========================

    private void clearOrder() {

        cartItems.clear();

        refreshCartTable();

        updateTotals();

        quantitySpinner.setValue(
                1
        );
    }


    // =========================
    // SAVE ORDER
    // =========================

    private void saveOrder() {

        // =========================
        // VALIDATE TABLE
        // =========================

        TableOption selectedTable =
                (TableOption) tableComboBox.getSelectedItem();

        if (selectedTable == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a table.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // VALIDATE ITEMS
        // =========================

        if (cartItems.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please add at least one item.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // CALCULATE TOTALS
        // =========================

        double subtotal = 0;

        for (OrderItem item : cartItems) {

            subtotal += item.getTotalPrice();
        }


        double tax =
                subtotal * TAX_RATE;


        double total =
                subtotal + tax;


        // =========================
        // CREATE ORDER
        // =========================

        Order order =
                new Order();

        order.setTableId(
                selectedTable.tableId
        );

        order.setOrderStatus(
                "Pending"
        );

        order.setSubtotal(
                subtotal
        );

        order.setTax(
                tax
        );

        order.setTotalAmount(
                total
        );


        // =========================
        // SAVE ORDER
        // =========================

        int orderId =
                orderController.addOrder(
                        order
                );


        if (orderId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to create order.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =========================
        // SAVE ORDER ITEMS
        // =========================

        boolean itemsSaved = true;


        for (OrderItem item : cartItems) {

            item.setOrderId(
                    orderId
            );


            boolean saved =
                    orderController.addOrderItem(
                            item
                    );


            if (!saved) {

                itemsSaved = false;

                break;
            }
        }


        // =========================
        // RESULT
        // =========================

        if (itemsSaved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order created successfully!\n\n"
                            + "Order ID: "
                            + orderId
                            + "\n"
                            + "Table: "
                            + selectedTable.tableNumber
                            + "\n"
                            + "Subtotal: ₹"
                            + String.format(
                            "%.2f",
                            subtotal
                    )
                            + "\n"
                            + "Tax: ₹"
                            + String.format(
                            "%.2f",
                            tax
                    )
                            + "\n"
                            + "Total: ₹"
                            + String.format(
                            "%.2f",
                            total
                    ),
                    "Order Created",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Clear the current order
            clearOrder();


            // Reload available tables
            loadTables();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Order was created, but some order items could not be saved.\n"
                            + "Order ID: "
                            + orderId,
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    // =========================
    // TABLE OPTION
    // =========================

    private static class TableOption {

        int tableId;
        int tableNumber;
        int capacity;


        TableOption(
                int tableId,
                int tableNumber,
                int capacity
        ) {

            this.tableId =
                    tableId;

            this.tableNumber =
                    tableNumber;

            this.capacity =
                    capacity;
        }


        @Override
        public String toString() {

            return "Table "
                    + tableNumber
                    + " (Capacity: "
                    + capacity
                    + ")";
        }
    }


    // =========================
    // MENU ITEM OPTION
    // =========================

    private static class MenuItemOption {

        int itemId;
        String itemName;
        double price;


        MenuItemOption(
                int itemId,
                String itemName,
                double price
        ) {

            this.itemId =
                    itemId;

            this.itemName =
                    itemName;

            this.price =
                    price;
        }


        @Override
        public String toString() {

            return itemName
                    + " - ₹"
                    + String.format(
                    "%.2f",
                    price
            );
        }
    }
}


