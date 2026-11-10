package com.dineease.view.admin.billing;

import com.dineease.controller.BillController;
import com.dineease.model.Bill;
import com.dineease.model.BillItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

public class BillHistoryPanel extends JPanel {

    private BillController billController;

    private JTable billTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> statusComboBox;

    private JButton viewPdfButton;
    private JButton printButton;
    private JButton refreshButton;
    private JButton searchButton;
    private JButton updateStatusButton;

    private List<Bill> allBills;


    // =========================
    // CONSTRUCTOR
    // =========================

    public BillHistoryPanel() {

        billController =
                new BillController();

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

        loadBills();
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


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "Bill History"
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
                        "View and manage previously generated bills"
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

        titlePanel.add(
                title
        );

        titlePanel.add(
                subtitle
        );


        panel.add(
                titlePanel,
                BorderLayout.WEST
        );


        // =========================
        // SEARCH PANEL
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
                new JLabel(
                        "Search:"
                )
        );


        searchField =
                new JTextField(
                        12
                );

        searchField.setToolTipText(
                "Search by Bill ID or Table Number"
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
                new JLabel(
                        "Status:"
                )
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Paid",
                                "Pending",
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
        // SEARCH ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> filterBills()
        );


        searchField.addActionListener(
                e -> filterBills()
        );


        statusComboBox.addActionListener(
                e -> filterBills()
        );
    }


    // =========================
    // BILL TABLE
    // =========================

    private void createBillTable() {

        String[] columns = {

                "Bill ID",
                "Table",
                "Date",
                "Subtotal",
                "GST",
                "Discount",
                "Total",
                "Payment",
                "Status"
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
                        13
                )
        );


        billTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        billTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
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


        viewPdfButton =
                new JButton(
                        "View PDF"
                );

        UITheme.stylePrimaryButton(
                viewPdfButton
        );


        printButton =
                new JButton(
                        "Print"
                );

        UITheme.styleSecondaryButton(
                printButton
        );


        refreshButton =
                new JButton(
                        "Refresh"
                );

        updateStatusButton =
                new JButton(
                        "Update Status"
                );

        UITheme.styleSecondaryButton(
                updateStatusButton
        );

        UITheme.styleSecondaryButton(
                refreshButton
        );


        panel.add(
                refreshButton
        );

        panel.add(
                updateStatusButton
        );

        panel.add(
                printButton
        );

        panel.add(
                viewPdfButton
        );


        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    statusComboBox.setSelectedItem(
                            "All"
                    );

                    loadBills();
                }
        );




        viewPdfButton.addActionListener(
                e -> viewSelectedBillPDF()
        );


        printButton.addActionListener(
                e -> printSelectedBill()
        );

        updateStatusButton.addActionListener(
                e -> updateSelectedBillStatus()
        );


        add(
                panel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD BILLS
    // =========================

    private void loadBills() {

        tableModel.setRowCount(
                0
        );


        allBills =
                billController.getAllBills();


        if (
                allBills == null
                        ||
                        allBills.isEmpty()
        ) {

            return;
        }


        displayBills(
                allBills
        );
    }


    // =========================
    // DISPLAY BILLS
    // =========================

    private void displayBills(
            List<Bill> bills
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                Bill bill :
                bills
        ) {

            Object[] row = {

                    bill.getBillId(),

                    bill.getTableNumber(),

                    bill.getBillDate(),

                    String.format(
                            "₹%.2f",
                            bill.getSubtotal()
                    ),

                    String.format(
                            "₹%.2f",
                            bill.getTax()
                    ),

                    String.format(
                            "₹%.2f",
                            bill.getDiscount()
                    ),

                    String.format(
                            "₹%.2f",
                            bill.getTotalAmount()
                    ),

                    bill.getPaymentMethod(),

                    bill.getStatus()
            };


            tableModel.addRow(
                    row
            );
        }
    }


    // =========================
    // FILTER BILLS
    // =========================

    private void filterBills() {

        if (
                allBills == null
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


        List<Bill> filteredBills =
                allBills
                        .stream()
                        .filter(
                                bill -> {

                                    boolean searchMatch =
                                            searchText.isEmpty()
                                                    ||
                                                    String.valueOf(
                                                                    bill.getBillId()
                                                            )
                                                            .contains(
                                                                    searchText
                                                            )
                                                    ||
                                                    String.valueOf(
                                                                    bill.getTableNumber()
                                                            )
                                                            .contains(
                                                                    searchText
                                                            );


                                    boolean statusMatch =
                                            selectedStatus.equals(
                                                    "All"
                                            )
                                                    ||
                                                    bill.getStatus()
                                                            .equalsIgnoreCase(
                                                                    selectedStatus
                                                            );


                                    return searchMatch
                                            &&
                                            statusMatch;
                                }
                        )
                        .toList();


        displayBills(
                filteredBills
        );
    }


    // =========================
    // SELECTED BILL
    // =========================

    private Bill getSelectedBill() {

        int row =
                billTable.getSelectedRow();


        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a bill first."
            );

            return null;
        }


        int billId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );


        if (allBills != null) {

            for (
                    Bill bill :
                    allBills
            ) {

                if (
                        bill.getBillId()
                                == billId
                ) {

                    return bill;
                }
            }
        }


        return null;
    }


    // =========================
    // VIEW PDF
    // =========================

    private void viewSelectedBillPDF() {

        Bill bill =
                getSelectedBill();


        if (bill == null) {

            return;
        }


        File pdfFile =
                generateBillPDF(
                        bill
                );


        if (
                pdfFile != null
                        &&
                        pdfFile.exists()
        ) {

            try {

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
                        "Unable to open PDF.\n"
                                + e.getMessage(),
                        "PDF Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // =========================
    // GENERATE PDF
    // =========================

    private File generateBillPDF(
            Bill bill
    ) {

        String folderPath =
                "bills";

        File folder =
                new File(
                        folderPath
                );


        if (
                !folder.exists()
        ) {

            folder.mkdirs();
        }


        String filePath =
                folderPath
                        + File.separator
                        + "Bill_"
                        + bill.getBillId()
                        + ".pdf";


        try {

            List<BillItem> items =
                    billController.getBillItems(
                            bill.getBillId()
                    );


            PdfWriter writer =
                    new PdfWriter(
                            filePath
                    );


            PdfDocument pdf =
                    new PdfDocument(
                            writer
                    );


            Document document =
                    new Document(
                            pdf
                    );


            document.add(
                    new Paragraph(
                            "DINEEASE AI"
                    )
                            .setBold()
                            .setFontSize(22)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "RESTAURANT & CAFE"
                    )
                            .setBold()
                            .setFontSize(12)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "Jaipur, Rajasthan"
                    )
                            .setFontSize(10)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "Phone: +91-1234587692"
                    )
                            .setFontSize(10)
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            document.add(
                    new Paragraph(
                            "----------------------------------------"
                    )
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );


            // =========================
            // BILL INFORMATION
            // =========================

            Table infoTable =
                    new Table(
                            UnitValue.createPercentArray(
                                    new float[]{
                                            1,
                                            1
                                    }
                            )
                    );


            infoTable.setWidth(
                    UnitValue.createPercentValue(
                            100
                    )
            );


            infoTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Bill ID: "
                                            + bill.getBillId()
                            )
                    )
            );


            infoTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Date: "
                                            + bill.getBillDate()
                            )
                    )
            );


            infoTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Table No: "
                                            + bill.getTableNumber()
                            )
                    )
            );


            infoTable.addCell(
                    new Cell().add(
                            new Paragraph(
                                    "Payment: "
                                            + bill.getPaymentMethod()
                            )
                    )
            );


            document.add(
                    infoTable
            );


            document.add(
                    new Paragraph(" ")
            );


            // =========================
            // ITEMS TABLE
            // =========================

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
                    UnitValue.createPercentValue(
                            100
                    )
            );


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


            if (
                    items != null
            ) {

                int serialNumber = 1;


                for (
                        BillItem item :
                        items
                ) {

                    itemTable.addCell(
                            new Cell().add(
                                    new Paragraph(
                                            String.valueOf(
                                                    serialNumber++
                                            )
                                    )
                            )
                    );


                    itemTable.addCell(
                            new Cell().add(
                                    new Paragraph(
                                            item.getItemName()
                                    )
                            )
                    );


                    itemTable.addCell(
                            new Cell().add(
                                    new Paragraph(
                                            String.valueOf(
                                                    item.getQuantity()
                                            )
                                    )
                            )
                    );


                    itemTable.addCell(
                            new Cell().add(
                                    new Paragraph(
                                            String.format(
                                                    "₹%.2f",
                                                    item.getPrice()
                                            )
                                    )
                            )
                    );


                    itemTable.addCell(
                            new Cell().add(
                                    new Paragraph(
                                            String.format(
                                                    "₹%.2f",
                                                    item.getSubtotal()
                                            )
                                    )
                            )
                    );
                }
            }


            document.add(
                    itemTable
            );


            document.add(
                    new Paragraph(" ")
            );


            // =========================
            // SUMMARY
            // =========================

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
                    UnitValue.createPercentValue(
                            100
                    )
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
                                            bill.getSubtotal()
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
                                    "- ₹"
                                            + String.format(
                                            "%.2f",
                                            bill.getDiscount()
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
                                            bill.getTax()
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
                                            bill.getTotalAmount()
                                    )
                            )
                                    .setBold()
                                    .setFontSize(13)
                    )
            );


            document.add(
                    summaryTable
            );


            // =========================
            // FOOTER
            // =========================

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


            return new File(
                    filePath
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "PDF could not be created.\n"
                            + e.getMessage(),
                    "PDF Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return null;
        }
    }
// =========================
// PRINT
// =========================

    private void printSelectedBill() {

        int selectedRow =
                billTable.getSelectedRow();

        if (selectedRow < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a bill first."
            );

            return;
        }

        try {

            boolean complete =
                    billTable.print();

            if (complete) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill sent to printer."
                );
            }

        }
        catch (PrinterException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to print bill.\n"
                            + e.getMessage(),
                    "Print Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


// =========================
// UPDATE BILL STATUS
// =========================

    private void updateSelectedBillStatus() {

        Bill bill =
                getSelectedBill();

        if (bill == null) {

            return;
        }


        String[] statuses = {

                "Paid",
                "Pending",
                "Cancelled"
        };


        String selectedStatus =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Select new bill status:",
                        "Update Bill Status",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        statuses,
                        bill.getStatus()
                );


        if (selectedStatus == null) {

            return;
        }


        if (
                selectedStatus.equalsIgnoreCase(
                        bill.getStatus()
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill is already "
                            + selectedStatus
                            + "."
            );

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Change Bill ID "
                                + bill.getBillId()
                                + " status from "
                                + bill.getStatus()
                                + " to "
                                + selectedStatus
                                + "?",
                        "Confirm Status Change",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirm != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                billController.updateBillStatus(
                        bill.getBillId(),
                        selectedStatus
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill status updated successfully."
            );

            // Reload database data
            loadBills();

            // Re-apply current search/status filter
            filterBills();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update bill status.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}

