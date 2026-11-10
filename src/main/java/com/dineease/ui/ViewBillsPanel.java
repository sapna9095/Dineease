package com.dineease.ui;

import com.dineease.controller.BillController;
import com.dineease.model.Bill;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewBillsPanel extends JPanel {

    private JTable billTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JButton searchButton;
    private JButton refreshButton;
    private JButton viewButton;
    private JButton deleteButton;

    private BillController billController;

    private List<Bill> bills;


    public ViewBillsPanel() {

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

        createTable();

        createBottomPanel();

        loadBills();
    }


    // =========================
    // TOP
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


        panel.add(
                title,
                BorderLayout.WEST
        );


        JPanel search =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        search.setBackground(
                UITheme.BACKGROUND
        );


        search.add(
                new JLabel(
                        "Search Bill:"
                )
        );


        searchField =
                new JTextField(
                        10
                );


        search.add(
                searchField
        );


        searchButton =
                new JButton(
                        "Search"
                );


        UITheme.stylePrimaryButton(
                searchButton
        );


        search.add(
                searchButton
        );


        panel.add(
                search,
                BorderLayout.EAST
        );


        add(
                panel,
                BorderLayout.NORTH
        );


        searchButton.addActionListener(
                e -> searchBills()
        );


        searchField.addActionListener(
                e -> searchBills()
        );
    }


    // =========================
    // TABLE
    // =========================

    private void createTable() {

        String[] columns = {

                "Bill ID",
                "Table",
                "Date",
                "Subtotal",
                "Discount",
                "Tax",
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


        billTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        billTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
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
    // BOTTOM
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
                        "View Bill"
                );

        deleteButton =
                new JButton(
                        "Delete Bill"
                );


        UITheme.styleSecondaryButton(
                refreshButton
        );

        UITheme.stylePrimaryButton(
                viewButton
        );

        UITheme.styleDangerButton(
                deleteButton
        );


        panel.add(
                refreshButton
        );

        panel.add(
                viewButton
        );

        panel.add(
                deleteButton
        );


        add(
                panel,
                BorderLayout.SOUTH
        );


        refreshButton.addActionListener(
                e -> loadBills()
        );

        viewButton.addActionListener(
                e -> viewBill()
        );

        deleteButton.addActionListener(
                e -> deleteBill()
        );
    }


    // =========================
    // LOAD BILLS
    // =========================

    private void loadBills() {

        bills =
                billController.getAllBills();


        if (
                bills == null
        ) {

            bills =
                    new java.util.ArrayList<>();
        }


        displayBills(
                bills
        );
    }


    // =========================
    // DISPLAY
    // =========================

    private void displayBills(
            List<Bill> list
    ) {

        tableModel.setRowCount(0);


        for (
                Bill bill :
                list
        ) {

            tableModel.addRow(
                    new Object[]{

                            bill.getBillId(),

                            bill.getTableNumber(),

                            bill.getBillDate(),

                            String.format(
                                    "₹%.2f",
                                    bill.getSubtotal()
                            ),

                            String.format(
                                    "₹%.2f",
                                    bill.getDiscount()
                            ),

                            String.format(
                                    "₹%.2f",
                                    bill.getTax()
                            ),

                            String.format(
                                    "₹%.2f",
                                    bill.getTotalAmount()
                            ),

                            bill.getPaymentMethod(),

                            bill.getStatus()
                    }
            );
        }
    }


    // =========================
    // SEARCH
    // =========================

    private void searchBills() {

        if (
                bills == null
        ) {

            return;
        }


        String text =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        if (text.isEmpty()) {

            displayBills(
                    bills
            );

            return;
        }


        List<Bill> filtered =
                bills.stream()
                        .filter(
                                bill ->
                                        String.valueOf(
                                                        bill.getBillId()
                                                )
                                                .contains(text)
                                                ||
                                                String.valueOf(
                                                                bill.getTableNumber()
                                                        )
                                                        .contains(text)
                        )
                        .toList();


        displayBills(
                filtered
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


        for (
                Bill bill :
                bills
        ) {

            if (
                    bill.getBillId()
                            == billId
            ) {

                return bill;
            }
        }


        return null;
    }


    // =========================
    // VIEW BILL
    // =========================

    private void viewBill() {

        Bill bill =
                getSelectedBill();


        if (
                bill == null
        ) {

            return;
        }


        String message =
                "BILL DETAILS\n"
                        + "============================\n\n"
                        + "Bill ID: "
                        + bill.getBillId()
                        + "\n"
                        + "Table: "
                        + bill.getTableNumber()
                        + "\n"
                        + "Date: "
                        + bill.getBillDate()
                        + "\n\n"
                        + "Subtotal: ₹"
                        + String.format(
                        "%.2f",
                        bill.getSubtotal()
                )
                        + "\n"
                        + "Discount: ₹"
                        + String.format(
                        "%.2f",
                        bill.getDiscount()
                )
                        + "\n"
                        + "Tax: ₹"
                        + String.format(
                        "%.2f",
                        bill.getTax()
                )
                        + "\n"
                        + "Total: ₹"
                        + String.format(
                        "%.2f",
                        bill.getTotalAmount()
                )
                        + "\n\n"
                        + "Payment: "
                        + bill.getPaymentMethod()
                        + "\n"
                        + "Status: "
                        + bill.getStatus();


        JTextArea area =
                new JTextArea(
                        message
                );

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        area
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        450,
                        400
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Bill Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================
    // DELETE BILL
    // =========================

    private void deleteBill() {

        Bill bill =
                getSelectedBill();


        if (
                bill == null
        ) {

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete Bill #"
                                + bill.getBillId()
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                billController.deleteBill(
                        bill.getBillId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bill deleted successfully."
            );

            loadBills();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete bill.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}