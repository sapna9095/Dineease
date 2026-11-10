package com.dineease.view.admin.inventory;

import com.dineease.controller.InventoryController;
import com.dineease.model.InventoryItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteInventoryPanel extends JPanel {

    private JComboBox<InventoryItem> itemComboBox;

    private JLabel itemNameLabel;
    private JLabel categoryLabel;
    private JLabel quantityLabel;
    private JLabel unitLabel;
    private JLabel statusLabel;

    private JButton deleteButton;
    private JButton refreshButton;

    private InventoryController inventoryController;

    private List<InventoryItem> inventoryItems;


    // =========================
    // CONSTRUCTOR
    // =========================

    public DeleteInventoryPanel() {

        inventoryController =
                new InventoryController();

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
                        30,
                        20,
                        30
                )
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "Delete Inventory Item"
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


        add(
                title,
                BorderLayout.NORTH
        );


        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(
                        new GridBagLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =========================
        // SELECT ITEM
        // =========================

        JLabel selectLabel =
                new JLabel(
                        "Select Item:"
                );

        selectLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        selectLabel.setForeground(
                UITheme.TEXT
        );


        itemComboBox =
                new JComboBox<>();

        itemComboBox.setPreferredSize(
                new Dimension(
                        350,
                        38
                )
        );


        refreshButton =
                new JButton(
                        "↻ Refresh"
                );

        UITheme.stylePrimaryButton(
                refreshButton
        );


        JPanel selectionPanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        selectionPanel.setBackground(
                UITheme.BACKGROUND
        );


        selectionPanel.add(
                itemComboBox,
                BorderLayout.CENTER
        );


        selectionPanel.add(
                refreshButton,
                BorderLayout.EAST
        );


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;


        mainPanel.add(
                selectLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;


        mainPanel.add(
                selectionPanel,
                gbc
        );


        // =========================
        // ITEM INFORMATION
        // =========================

        JPanel detailsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        detailsPanel.setBackground(
                UITheme.CARD
        );

        detailsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );


        GridBagConstraints detailGbc =
                new GridBagConstraints();

        detailGbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );

        detailGbc.fill =
                GridBagConstraints.HORIZONTAL;


        // ITEM NAME

        itemNameLabel =
                createValueLabel();

        addDetail(
                detailsPanel,
                detailGbc,
                0,
                "Item Name:",
                itemNameLabel
        );


        // CATEGORY

        categoryLabel =
                createValueLabel();

        addDetail(
                detailsPanel,
                detailGbc,
                1,
                "Category:",
                categoryLabel
        );


        // QUANTITY

        quantityLabel =
                createValueLabel();

        addDetail(
                detailsPanel,
                detailGbc,
                2,
                "Quantity:",
                quantityLabel
        );


        // UNIT

        unitLabel =
                createValueLabel();

        addDetail(
                detailsPanel,
                detailGbc,
                3,
                "Unit:",
                unitLabel
        );


        // STATUS

        statusLabel =
                createValueLabel();

        addDetail(
                detailsPanel,
                detailGbc,
                4,
                "Status:",
                statusLabel
        );


        // =========================
        // DETAILS CONTAINER
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;

        mainPanel.add(
                detailsPanel,
                gbc
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =========================
        // DELETE BUTTON
        // =========================

        deleteButton =
                new JButton(
                        "Delete Item"
                );

        deleteButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        deleteButton.setForeground(
                Color.WHITE
        );


        deleteButton.setBackground(
                Color.RED
        );


        deleteButton.setFocusPainted(
                false
        );


        deleteButton.setPreferredSize(
                new Dimension(
                        150,
                        40
                )
        );


        deleteButton.addActionListener(
                e -> deleteInventoryItem()
        );


        refreshButton.addActionListener(
                e -> loadInventoryItems()
        );


        itemComboBox.addActionListener(
                e -> showSelectedItem()
        );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        buttonPanel.add(
                deleteButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // LOAD DATA
        // =========================

        loadInventoryItems();

    }


    // =========================
    // CREATE VALUE LABEL
    // =========================

    private JLabel createValueLabel() {

        JLabel label =
                new JLabel(
                        "-"
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        label.setForeground(
                UITheme.TEXT
        );


        return label;

    }


    // =========================
    // ADD DETAIL
    // =========================

    private void addDetail(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String title,
            JLabel value
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );


        panel.add(
                titleLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;


        panel.add(
                value,
                gbc
        );

    }


    // =========================
    // LOAD INVENTORY ITEMS
    // =========================

    private void loadInventoryItems() {

        inventoryItems =
                inventoryController
                        .getAllInventoryItems();


        itemComboBox.removeAllItems();


        if (
                inventoryItems == null
                        ||
                        inventoryItems.isEmpty()
        ) {

            clearDetails();

            deleteButton.setEnabled(
                    false
            );

            return;

        }


        for (
                InventoryItem item :
                inventoryItems
        ) {

            itemComboBox.addItem(
                    item
            );

        }


        deleteButton.setEnabled(
                true
        );


        showSelectedItem();

    }


    // =========================
    // SHOW SELECTED ITEM
    // =========================

    private void showSelectedItem() {

        InventoryItem selectedItem =
                (InventoryItem)
                        itemComboBox
                                .getSelectedItem();


        if (selectedItem == null) {

            clearDetails();

            return;

        }


        itemNameLabel.setText(
                selectedItem.getItemName()
        );


        categoryLabel.setText(
                selectedItem.getCategory()
        );


        quantityLabel.setText(
                String.valueOf(
                        selectedItem.getQuantity()
                )
        );


        unitLabel.setText(
                selectedItem.getUnit()
        );


        statusLabel.setText(
                selectedItem.getStatus()
        );


        // =========================
        // STATUS COLOR
        // =========================

        if (
                selectedItem.getStatus()
                        .equalsIgnoreCase(
                                "Out of Stock"
                        )
        ) {

            statusLabel.setForeground(
                    Color.RED
            );

        }

        else if (
                selectedItem.getStatus()
                        .equalsIgnoreCase(
                                "Low Stock"
                        )
        ) {

            statusLabel.setForeground(
                    Color.ORANGE
            );

        }

        else {

            statusLabel.setForeground(
                    UITheme.TEXT
            );

        }

    }


    // =========================
    // DELETE INVENTORY ITEM
    // =========================

    private void deleteInventoryItem() {

        InventoryItem selectedItem =
                (InventoryItem)
                        itemComboBox
                                .getSelectedItem();


        if (selectedItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an inventory item.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;

        }


        // =========================
        // CONFIRMATION
        // =========================

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete \""
                                + selectedItem.getItemName()
                                + "\"?\n\n"
                                + "This action cannot be undone.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirmation
                        !=
                        JOptionPane.YES_OPTION
        ) {

            return;

        }


        // =========================
        // DELETE
        // =========================

        boolean success =
                inventoryController
                        .deleteInventoryItem(
                                selectedItem
                                        .getInventoryId()
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Inventory item deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            loadInventoryItems();

        }

        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete inventory item.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    // =========================
    // CLEAR DETAILS
    // =========================

    private void clearDetails() {

        itemNameLabel.setText("-");

        categoryLabel.setText("-");

        quantityLabel.setText("-");

        unitLabel.setText("-");

        statusLabel.setText("-");

        statusLabel.setForeground(
                UITheme.TEXT
        );

    }


    // =========================
    // COMBO BOX DISPLAY
    // =========================

    @Override
    public String toString() {

        return "Inventory";

    }

}

