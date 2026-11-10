package com.dineease.view.admin.billing;

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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.awt.Desktop;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillingPanel extends JPanel {

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
    private JButton generateBillButton;
    private JButton clearButton;

    private BillController billController;
    private MenuController menuController;

    private List<MenuItem> menuItems;


    // =========================
    // CONSTRUCTOR
    // =========================

    public BillingPanel() {

        billController = new BillController();
        menuController = new MenuController();

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

        createBillTable();

        createBottomPanel();

        loadMenuItems();
    }


    // =========================
    // TOP PANEL
    // =========================

    private void createTopPanel() {

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        topPanel.setBackground(
                UITheme.BACKGROUND
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "Billing & Checkout"
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

        topPanel.add(
                title,
                BorderLayout.NORTH
        );


        // =========================
        // SELECTION PANEL
        // =========================

        JPanel selectionPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                10,
                                10
                        )
                );

        selectionPanel.setBackground(
                UITheme.CARD
        );

        selectionPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );


        // =========================
        // TABLE
        // =========================

        selectionPanel.add(
                new JLabel(
                        "Table:"
                )
        );

        tableComboBox =
                new JComboBox<>(
                        new String[]{
                                "Table 1",
                                "Table 2",
                                "Table 3",
                                "Table 4",
                                "Table 5",
                                "Table 6",
                                "Table 7",
                                "Table 8",
                                "Table 9",
                                "Table 10"
                        }
                );

        selectionPanel.add(
                tableComboBox
        );


        // =========================
        // MENU ITEM
        // =========================

        selectionPanel.add(
                new JLabel(
                        "Menu Item:"
                )
        );

        menuItemComboBox =
                new JComboBox<>();

        selectionPanel.add(
                menuItemComboBox
        );


        // =========================
        // QUANTITY
        // =========================

        selectionPanel.add(
                new JLabel(
                        "Quantity:"
                )
        );

        quantityField =
                new JTextField(
                        "1"
                );

        selectionPanel.add(
                quantityField
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

        selectionPanel.add(
                addItemButton
        );


        topPanel.add(
                selectionPanel,
                BorderLayout.CENTER
        );


        add(
                topPanel,
                BorderLayout.NORTH
        );


        addItemButton.addActionListener(
                e -> addItemToBill()
        );
    }


    // =========================
    // BILL TABLE
    // =========================

    private void createBillTable() {

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
                32
        );

        billTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        billTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
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
    // BOTTOM PANEL
    // =========================

    private void createBottomPanel() {

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        bottomPanel.setBackground(
                UITheme.BACKGROUND
        );


        // =========================
        // LEFT BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        removeItemButton =
                new JButton(
                        "Remove Item"
                );

        UITheme.styleDangerButton(
                removeItemButton
        );


        clearButton =
                new JButton(
                        "Clear Bill"
                );

        UITheme.styleSecondaryButton(
                clearButton
        );


        buttonPanel.add(
                removeItemButton
        );

        buttonPanel.add(
                clearButton
        );


        // =========================
        // TOTAL PANEL
        // =========================

        JPanel totalPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                5
                        )
                );

        totalPanel.setBackground(
                UITheme.CARD
        );

        totalPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );


        totalPanel.add(
                new JLabel(
                        "Subtotal:"
                )
        );

        subtotalLabel =
                new JLabel(
                        "₹0.00"
                );

        totalPanel.add(
                subtotalLabel
        );


        totalPanel.add(
                new JLabel(
                        "Tax (5%):"
                )
        );

        taxLabel =
                new JLabel(
                        "₹0.00"
                );

        totalPanel.add(
                taxLabel
        );


        totalPanel.add(
                new JLabel(
                        "Total:"
                )
        );

        totalLabel =
                new JLabel(
                        "₹0.00"
                );

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        totalPanel.add(
                totalLabel
        );


        // =========================
        // DISCOUNT PANEL
        // =========================

        JPanel discountPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        discountPanel.setBackground(
                UITheme.BACKGROUND
        );


        discountPanel.add(
                new JLabel(
                        "Discount:"
                )
        );


        discountField =
                new JTextField(
                        "0",
                        6
                );

        discountPanel.add(
                discountField
        );


        JButton calculateButton =
                new JButton(
                        "Calculate"
                );

        UITheme.stylePrimaryButton(
                calculateButton
        );

        discountPanel.add(
                calculateButton
        );


        calculateButton.addActionListener(
                e -> calculateTotal()
        );


        // =========================
        // RIGHT PANEL
        // =========================

        JPanel rightPanel =
                new JPanel(
                        new BorderLayout()
                );

        rightPanel.setBackground(
                UITheme.BACKGROUND
        );


        rightPanel.add(
                discountPanel,
                BorderLayout.NORTH
        );


        rightPanel.add(
                totalPanel,
                BorderLayout.CENTER
        );


        // =========================
        // GENERATE BILL
        // =========================

        generateBillButton =
                new JButton(
                        "Generate Bill"
                );

        UITheme.stylePrimaryButton(
                generateBillButton
        );


        JPanel generatePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        generatePanel.setBackground(
                UITheme.BACKGROUND
        );


        generatePanel.add(
                generateBillButton
        );


        rightPanel.add(
                generatePanel,
                BorderLayout.SOUTH
        );


        bottomPanel.add(
                buttonPanel,
                BorderLayout.WEST
        );


        bottomPanel.add(
                rightPanel,
                BorderLayout.EAST
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        removeItemButton.addActionListener(
                e -> removeSelectedItem()
        );


        clearButton.addActionListener(
                e -> clearBill()
        );


        generateBillButton.addActionListener(
                e -> generateBill()
        );
    }

    private void generateBillPDF(
            int billId,
            int tableNumber,
            double subtotal,
            double discount,
            double tax,
            double total
    ) {

        String folderPath = "bills";

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath =
                folderPath
                        + File.separator
                        + "Bill_"
                        + billId
                        + ".pdf";

        try {

            PdfWriter writer =
                    new PdfWriter(filePath);

            PdfDocument pdf =
                    new PdfDocument(writer);

            Document document =
                    new Document(pdf);


            // ==========================================
            // RESTAURANT HEADER
            // ==========================================

            Paragraph restaurantName =
                    new Paragraph("DINEEASE AI")
                            .setBold()
                            .setFontSize(22)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            );

            document.add(
                    restaurantName
            );


            Paragraph restaurantType =
                    new Paragraph("RESTAURANT & CAFE")
                            .setBold()
                            .setFontSize(12)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            );

            document.add(
                    restaurantType
            );


            Paragraph address =
                    new Paragraph(
                            "Jaipur, Rajasthan"
                    )
                            .setFontSize(10)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            );

            document.add(
                    address
            );


            Paragraph phone =
                    new Paragraph(
                            "Phone: +91-XXXXXXXXXX"
                    )
                            .setFontSize(10)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            );

            document.add(
                    phone
            );


            document.add(
                    new Paragraph(
                            "----------------------------------------"
                    )
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            // ==========================================
            // BILL INFORMATION
            // ==========================================

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm"
                    );

            String dateTime =
                    LocalDateTime.now()
                            .format(formatter);


            Table billInfo =
                    new Table(
                            UnitValue.createPercentArray(
                                    new float[]{
                                            1,
                                            1
                                    }
                            )
                    );

            billInfo.setWidth(
                    UnitValue.createPercentValue(100)
            );


            billInfo.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Bill ID: "
                                            + billId
                            )
                    )
            );


            billInfo.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Date: "
                                            + dateTime
                            )
                    )
            );


            billInfo.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Table No: "
                                            + tableNumber
                            )
                    )
            );


            billInfo.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Payment: Cash"
                            )
                    )
            );


            document.add(
                    billInfo
            );


            document.add(
                    new Paragraph(" ")
            );


            // ==========================================
            // ITEMS TABLE
            // ==========================================

            Table itemTable =
                    new Table(
                            UnitValue.createPercentArray(
                                    new float[]{
                                            0.7f,
                                            3f,
                                            1f,
                                            1.2f,
                                            1.4f
                                    }
                            )
                    );


            itemTable.setWidth(
                    UnitValue.createPercentValue(100)
            );


            // Header

            itemTable.addHeaderCell(
                    new Cell().add(
                            new Paragraph("S.No.")
                                    .setBold()
                    )
            );


            itemTable.addHeaderCell(
                    new Cell().add(
                            new Paragraph("Items Taken")
                                    .setBold()
                    )
            );


            itemTable.addHeaderCell(
                    new Cell().add(
                            new Paragraph("Qty")
                                    .setBold()
                    )
            );


            itemTable.addHeaderCell(
                    new Cell().add(
                            new Paragraph("Price")
                                    .setBold()
                    )
            );


            itemTable.addHeaderCell(
                    new Cell().add(
                            new Paragraph("Amount")
                                    .setBold()
                    )
            );


            // ==========================================
            // BILL ITEMS
            // ==========================================

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


                itemTable.addCell(
                        new Cell().add(
                                new Paragraph(
                                        String.valueOf(
                                                row + 1
                                        )
                                )
                        )
                );


                itemTable.addCell(
                        new Cell().add(
                                new Paragraph(
                                        itemName
                                )
                        )
                );


                itemTable.addCell(
                        new Cell().add(
                                new Paragraph(
                                        String.valueOf(
                                                quantity
                                        )
                                )
                        )
                );


                itemTable.addCell(
                        new Cell().add(
                                new Paragraph(
                                        String.format(
                                                "₹%.2f",
                                                price
                                        )
                                )
                        )
                );


                itemTable.addCell(
                        new Cell().add(
                                new Paragraph(
                                        String.format(
                                                "₹%.2f",
                                                itemSubtotal
                                        )
                                )
                        )
                );
            }


            document.add(
                    itemTable
            );


            document.add(
                    new Paragraph(" ")
            );


            // ==========================================
            // BILL SUMMARY
            // ==========================================

            Table summaryTable =
                    new Table(
                            UnitValue.createPercentArray(
                                    new float[]{
                                            3,
                                            1.5f
                                    }
                            )
                    );


            summaryTable.setWidth(
                    UnitValue.createPercentValue(100)
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Subtotal"
                            ).setBold()
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.format(
                                            "₹%.2f",
                                            subtotal
                                    )
                            )
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Discount"
                            ).setBold()
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.format(
                                            "- ₹%.2f",
                                            discount
                                    )
                            )
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "GST"
                            ).setBold()
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.format(
                                            "₹%.2f",
                                            tax
                                    )
                            )
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "TOTAL"
                            )
                                    .setBold()
                                    .setFontSize(13)
                    )
            );


            summaryTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.format(
                                            "₹%.2f",
                                            total
                                    )
                            )
                                    .setBold()
                                    .setFontSize(13)
                    )
            );


            document.add(
                    summaryTable
            );


            // ==========================================
            // FOOTER
            // ==========================================

            document.add(
                    new Paragraph(" ")
            );


            document.add(
                    new Paragraph(
                            "----------------------------------------"
                    )
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "Thank You for Visiting!"
                    )
                            .setBold()
                            .setFontSize(13)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "Welcome Again!"
                    )
                            .setBold()
                            .setFontSize(12)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "We hope to serve you again soon."
                    )
                            .setFontSize(10)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "Thank you for choosing DineEase AI."
                    )
                            .setFontSize(9)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.close();


            // ==========================================
            // OPEN PDF
            // ==========================================

            File pdfFile =
                    new File(filePath);

            if (
                    Desktop.isDesktopSupported()
            ) {

                Desktop.getDesktop()
                        .open(pdfFile);
            }

        }
        catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Bill generated, but PDF could not be created.\n"
                            + e.getMessage(),
                    "PDF Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // LOAD MENU ITEMS
    // =========================

    private void loadMenuItems() {

        menuItems =
                menuController
                        .getAllMenuItems();


        menuItemComboBox.removeAllItems();


        if (menuItems == null) {

            return;
        }


        for (
                MenuItem item :
                menuItems
        ) {

            menuItemComboBox.addItem(
                    item.getItemId()
                            + " - "
                            + item.getItemName()
            );
        }
    }


    // =========================
    // ADD ITEM
    // =========================

    private void addItemToBill() {

        if (
                menuItems == null
                        ||
                        menuItems.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No menu items available."
            );

            return;
        }


        int index =
                menuItemComboBox
                        .getSelectedIndex();


        if (
                index < 0
                        ||
                        index >= menuItems.size()
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
                    "Quantity must be a valid number."
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


        Object[] row = {

                item.getItemName(),

                quantity,

                price,

                subtotal
        };


        tableModel.addRow(
                row
        );


        calculateTotal();
    }


    // =========================
    // REMOVE ITEM
    // =========================

    private void removeSelectedItem() {

        int selectedRow =
                billTable.getSelectedRow();


        if (selectedRow < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove."
            );

            return;
        }


        tableModel.removeRow(
                selectedRow
        );


        calculateTotal();
    }


    // =========================
    // CALCULATE TOTAL
    // =========================

    private void calculateTotal() {

        double subtotal = 0;


        for (
                int row = 0;
                row < tableModel.getRowCount();
                row++
        ) {

            Object value =
                    tableModel.getValueAt(
                            row,
                            3
                    );


            subtotal +=
                    Double.parseDouble(
                            value.toString()
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
        catch (NumberFormatException e) {

            discount = 0;
        }


        if (discount < 0) {

            discount = 0;
        }


        if (discount > subtotal) {

            discount = subtotal;
        }


        double taxableAmount =
                subtotal - discount;


        double tax =
                taxableAmount * 0.05;


        double total =
                taxableAmount + tax;


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
    // CLEAR BILL
    // =========================

    private void clearBill() {

        tableModel.setRowCount(
                0
        );


        quantityField.setText(
                "1"
        );


        discountField.setText(
                "0"
        );


        calculateTotal();
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
                tableComboBox
                        .getSelectedIndex()
                        + 1;


        double subtotal =
                getAmount(
                        subtotalLabel
                );


        double discount;


        try {

            discount =
                    Double.parseDouble(
                            discountField
                                    .getText()
                                    .trim()
                    );

        }
        catch (NumberFormatException e) {

            discount = 0;
        }


        double tax =
                getAmount(
                        taxLabel
                );


        double total =
                getAmount(
                        totalLabel
                );


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
                    "Failed to generate bill."
            );

            return;
        }


        boolean allItemsAdded = true;


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


            BillItem billItem =
                    new BillItem();


            billItem.setBillId(
                    billId
            );


            billItem.setMenuItemId(
                    menuItemId
            );


            billItem.setItemName(
                    itemName
            );


            billItem.setQuantity(
                    quantity
            );


            billItem.setPrice(
                    price
            );


            billItem.setSubtotal(
                    itemSubtotal
            );


            if (
                    !billController.addBillItem(
                            billItem
                    )
            ) {

                allItemsAdded = false;

                break;
            }
        }


        if (allItemsAdded) {

            generateBillPDF(
                    billId,
                    tableNumber,
                    subtotal,
                    discount,
                    tax,
                    total
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Bill generated successfully!\nBill ID: "
                            + billId
            );

            clearBill();

        }

        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill created, but some items could not be saved."
            );
        }
    }


    // =========================
    // FIND MENU ITEM ID
    // =========================

    private int findMenuItemId(
            String itemName
    ) {

        if (menuItems == null) {

            return -1;
        }


        for (
                MenuItem item :
                menuItems
        ) {

            if (
                    item.getItemName()
                            .equals(itemName)
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

        String text =
                label.getText()
                        .replace(
                                "₹",
                                ""
                        )
                        .trim();


        try {

            return Double.parseDouble(
                    text
            );

        }
        catch (NumberFormatException e) {

            return 0;
        }
    }
}

