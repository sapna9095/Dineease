package com.dineease.ui;

import com.dineease.controller.BillController;
import com.dineease.controller.MenuController;
import com.dineease.model.Bill;
import com.dineease.model.BillItem;
import com.dineease.model.MenuItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CreateBillPanel extends JPanel {

    private JComboBox<String> tableComboBox;
    private JComboBox<String> menuItemComboBox;

    private JTextField quantityField;
    private JTextField discountField;

    private JTable billTable;
    private DefaultTableModel tableModel;

    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;

    private JButton addItemButton;
    private JButton removeItemButton;
    private JButton calculateButton;
    private JButton generateBillButton;
    private JButton clearButton;

    private BillController billController;
    private MenuController menuController;

    private List<MenuItem> menuItems;


    public CreateBillPanel() {

        billController =
                new BillController();

        menuController =
                new MenuController();


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

        createTable();

        createBottomPanel();

        loadMenuItems();
    }


    // =========================
    // TOP
    // =========================

    private void createTopPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                UITheme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Create New Bill"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        title.setForeground(
                UITheme.TEXT
        );


        panel.add(
                title,
                BorderLayout.NORTH
        );


        JPanel selection =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                10,
                                10
                        )
                );

        selection.setBackground(
                UITheme.CARD
        );

        selection.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );


        selection.add(
                new JLabel("Table:")
        );


        tableComboBox =
                new JComboBox<>(
                        new String[]{
                                "Table 1",
                                "Table 2",
                                "Table 3",
                                "Table 4",
                                "Table 7"
                        }
                );


        selection.add(
                tableComboBox
        );


        selection.add(
                new JLabel("Menu Item:")
        );


        menuItemComboBox =
                new JComboBox<>();


        selection.add(
                menuItemComboBox
        );


        selection.add(
                new JLabel("Quantity:")
        );


        quantityField =
                new JTextField("1");


        selection.add(
                quantityField
        );


        addItemButton =
                new JButton(
                        "Add Item"
                );

        UITheme.stylePrimaryButton(
                addItemButton
        );


        selection.add(
                addItemButton
        );


        panel.add(
                selection,
                BorderLayout.CENTER
        );


        add(
                panel,
                BorderLayout.NORTH
        );


        addItemButton.addActionListener(
                e -> addItem()
        );
    }


    // =========================
    // TABLE
    // =========================

    private void createTable() {

        String[] columns = {
                "Item",
                "Quantity",
                "Price",
                "Subtotal"
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


        billTable =
                new JTable(
                        tableModel
                );


        billTable.setRowHeight(
                30
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        billTable
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
    // BOTTOM
    // =========================

    private void createBottomPanel() {

        JPanel bottom =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        bottom.setBackground(
                UITheme.BACKGROUND
        );


        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        left.setBackground(
                UITheme.BACKGROUND
        );


        removeItemButton =
                new JButton(
                        "Remove Item"
                );

        clearButton =
                new JButton(
                        "Clear Bill"
                );


        UITheme.styleDangerButton(
                removeItemButton
        );

        UITheme.styleSecondaryButton(
                clearButton
        );


        left.add(
                removeItemButton
        );

        left.add(
                clearButton
        );


        JPanel right =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        right.setBackground(
                UITheme.BACKGROUND
        );


        JPanel discount =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        discount.setBackground(
                UITheme.BACKGROUND
        );


        discount.add(
                new JLabel("Discount:")
        );


        discountField =
                new JTextField(
                        "0",
                        6
                );


        discount.add(
                discountField
        );


        calculateButton =
                new JButton(
                        "Calculate"
                );

        UITheme.stylePrimaryButton(
                calculateButton
        );


        discount.add(
                calculateButton
        );


        right.add(
                discount,
                BorderLayout.NORTH
        );


        JPanel totals =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                5
                        )
                );

        totals.setBackground(
                UITheme.CARD
        );


        totals.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );


        totals.add(
                new JLabel("Subtotal:")
        );


        subtotalLabel =
                new JLabel("₹0.00");


        totals.add(
                subtotalLabel
        );


        totals.add(
                new JLabel("Tax (5%):")
        );


        taxLabel =
                new JLabel("₹0.00");


        totals.add(
                taxLabel
        );


        totals.add(
                new JLabel("Total:")
        );


        totalLabel =
                new JLabel("₹0.00");


        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );


        totals.add(
                totalLabel
        );


        right.add(
                totals,
                BorderLayout.CENTER
        );


        generateBillButton =
                new JButton(
                        "Generate Bill"
                );


        UITheme.stylePrimaryButton(
                generateBillButton
        );


        JPanel generate =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        generate.setBackground(
                UITheme.BACKGROUND
        );


        generate.add(
                generateBillButton
        );


        right.add(
                generate,
                BorderLayout.SOUTH
        );


        bottom.add(
                left,
                BorderLayout.WEST
        );

        bottom.add(
                right,
                BorderLayout.EAST
        );


        add(
                bottom,
                BorderLayout.SOUTH
        );


        removeItemButton.addActionListener(
                e -> removeItem()
        );

        clearButton.addActionListener(
                e -> clearBill()
        );

        calculateButton.addActionListener(
                e -> calculateTotal()
        );

        generateBillButton.addActionListener(
                e -> generateBill()
        );
    }


    // =========================
    // LOAD MENU
    // =========================

    private void loadMenuItems() {

        menuItems =
                menuController.getAllMenuItems();


        menuItemComboBox.removeAllItems();


        if (
                menuItems == null
        ) {

            return;
        }


        for (
                MenuItem item :
                menuItems
        ) {

            if (
                    "Available".equalsIgnoreCase(
                            item.getStatus()
                    )
            ) {

                menuItemComboBox.addItem(
                        item.getItemId()
                                + " - "
                                + item.getItemName()
                );
            }
        }
    }


    // =========================
    // ADD ITEM
    // =========================

    private void addItem() {

        int index =
                menuItemComboBox
                        .getSelectedIndex();


        if (
                menuItems == null
                        ||
                        index < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a menu item."
            );

            return;
        }


        int quantity;


        try {

            quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid quantity."
            );

            return;
        }


        if (quantity <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than zero."
            );

            return;
        }


        MenuItem item =
                menuItems.get(index);


        double price =
                item.getPrice();


        double subtotal =
                price * quantity;


        tableModel.addRow(
                new Object[]{
                        item.getItemName(),
                        quantity,
                        price,
                        subtotal
                }
        );


        calculateTotal();
    }


    // =========================
    // REMOVE
    // =========================

    private void removeItem() {

        int row =
                billTable.getSelectedRow();


        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item."
            );

            return;
        }


        tableModel.removeRow(row);

        calculateTotal();
    }


    // =========================
    // CALCULATE
    // =========================

    private void calculateTotal() {

        double subtotal = 0;


        for (
                int i = 0;
                i < tableModel.getRowCount();
                i++
        ) {

            subtotal +=
                    Double.parseDouble(
                            tableModel
                                    .getValueAt(
                                            i,
                                            3
                                    )
                                    .toString()
                    );
        }


        double discount = 0;


        try {

            discount =
                    Double.parseDouble(
                            discountField
                                    .getText()
                                    .trim()
                    );

        }
        catch (Exception ignored) {
        }


        if (discount < 0) {
            discount = 0;
        }

        if (discount > subtotal) {
            discount = subtotal;
        }


        double taxable =
                subtotal - discount;


        double tax =
                taxable * 0.05;


        double total =
                taxable + tax;


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
    // GENERATE BILL
    // =========================

    private void generateBill() {

        if (
                tableModel.getRowCount() == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please add at least one item."
            );

            return;
        }


        calculateTotal();


        int tableNumber =
                getTableNumber();


        double subtotal =
                getAmount(
                        subtotalLabel
                );


        double tax =
                getAmount(
                        taxLabel
                );


        double total =
                getAmount(
                        totalLabel
                );


        double discount = 0;


        try {

            discount =
                    Double.parseDouble(
                            discountField
                                    .getText()
                                    .trim()
                    );

        }
        catch (Exception ignored) {
        }


        Bill bill =
                new Bill();


        bill.setTableNumber(
                tableNumber
        );

        bill.setSubtotal(
                subtotal
        );

        bill.setDiscount(
                discount
        );

        bill.setTax(
                tax
        );

        bill.setTotalAmount(
                total
        );

        bill.setPaymentMethod(
                "Cash"
        );

        bill.setStatus(
                "Paid"
        );


        int billId =
                billController.addBill(
                        bill
                );


        if (billId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to create bill.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        boolean success = true;


        for (
                int row = 0;
                row < tableModel.getRowCount();
                row++
        ) {

            String itemName =
                    tableModel
                            .getValueAt(
                                    row,
                                    0
                            )
                            .toString();


            int quantity =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            row,
                                            1
                                    )
                                    .toString()
                    );


            double price =
                    Double.parseDouble(
                            tableModel
                                    .getValueAt(
                                            row,
                                            2
                                    )
                                    .toString()
                    );


            double itemSubtotal =
                    Double.parseDouble(
                            tableModel
                                    .getValueAt(
                                            row,
                                            3
                                    )
                                    .toString()
                    );


            int menuItemId =
                    findMenuItemId(
                            itemName
                    );


            if (menuItemId <= 0) {

                success = false;

                break;
            }


            BillItem item =
                    new BillItem();


            item.setBillId(
                    billId
            );

            item.setMenuItemId(
                    menuItemId
            );

            item.setItemName(
                    itemName
            );

            item.setQuantity(
                    quantity
            );

            item.setPrice(
                    price
            );

            item.setSubtotal(
                    itemSubtotal
            );


            if (
                    !billController
                            .addBillItem(item)
            ) {

                success = false;

                break;
            }
        }


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill generated successfully!\n\n"
                            + "Bill ID: "
                            + billId
            );

            clearBill();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill was created, but some items could not be saved.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    // =========================
    // TABLE NUMBER
    // =========================

    private int getTableNumber() {

        String selected =
                tableComboBox
                        .getSelectedItem()
                        .toString();


        try {

            return Integer.parseInt(
                    selected
                            .replace(
                                    "Table ",
                                    ""
                            )
                            .trim()
            );

        }
        catch (Exception e) {

            return 1;
        }
    }


    // =========================
    // FIND MENU ID
    // =========================

    private int findMenuItemId(
            String name
    ) {

        if (
                menuItems == null
        ) {

            return -1;
        }


        for (
                MenuItem item :
                menuItems
        ) {

            if (
                    item.getItemName()
                            .equals(name)
            ) {

                return item.getItemId();
            }
        }


        return -1;
    }


    // =========================
    // GET AMOUNT
    // =========================

    private double getAmount(
            JLabel label
    ) {

        try {

            return Double.parseDouble(
                    label.getText()
                            .replace(
                                    "₹",
                                    ""
                            )
                            .trim()
            );

        }
        catch (Exception e) {

            return 0;
        }
    }


    // =========================
    // CLEAR
    // =========================

    private void clearBill() {

        tableModel.setRowCount(0);

        quantityField.setText("1");

        discountField.setText("0");

        calculateTotal();
    }
}
