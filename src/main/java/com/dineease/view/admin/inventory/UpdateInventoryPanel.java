package com.dineease.view.admin.inventory;

import com.dineease.controller.InventoryController;
import com.dineease.model.InventoryItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateInventoryPanel extends JPanel {

    private JComboBox<InventoryItem> itemComboBox;

    private JTextField itemNameField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField reorderLevelField;
    private JTextField minimumStockField;
    private JTextField supplierNameField;
    private JTextField costPerUnitField;
    private JComboBox<String> statusComboBox;

    private JButton updateButton;
    private JButton clearButton;
    private JButton refreshButton;

    private InventoryController inventoryController;

    private List<InventoryItem> inventoryItems;


    // =========================
    // CONSTRUCTOR
    // =========================

    public UpdateInventoryPanel() {

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
                        "Update Inventory Item"
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
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setBackground(
                UITheme.BACKGROUND
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =========================
        // SELECT ITEM
        // =========================

        itemComboBox =
                new JComboBox<>();

        itemComboBox.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        refreshButton =
                new JButton(
                        "↻"
                );

        refreshButton.setPreferredSize(
                new Dimension(
                        45,
                        35
                )
        );

        UITheme.stylePrimaryButton(
                refreshButton
        );


        JPanel selectionPanel =
                new JPanel(
                        new BorderLayout(
                                5,
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


        addFormField(
                formPanel,
                gbc,
                0,
                "Select Item *",
                selectionPanel
        );


        // =========================
        // ITEM NAME
        // =========================

        itemNameField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                1,
                "Item Name *",
                itemNameField
        );


        // =========================
        // CATEGORY
        // =========================

        categoryField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                2,
                "Category *",
                categoryField
        );


        // =========================
        // QUANTITY
        // =========================

        quantityField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                3,
                "Quantity *",
                quantityField
        );


        // =========================
        // UNIT
        // =========================

        unitComboBox =
                new JComboBox<>(
                        new String[]{
                                "Kg",
                                "Gram",
                                "Liter",
                                "Milliliter",
                                "Piece",
                                "Packet",
                                "Box",
                                "Bottle",
                                "Dozen"
                        }
                );

        addFormField(
                formPanel,
                gbc,
                4,
                "Unit *",
                unitComboBox
        );


        // =========================
        // REORDER LEVEL
        // =========================

        reorderLevelField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                5,
                "Reorder Level *",
                reorderLevelField
        );


        // =========================
        // MINIMUM STOCK
        // =========================

        minimumStockField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                6,
                "Minimum Stock *",
                minimumStockField
        );


        // =========================
        // SUPPLIER
        // =========================

        supplierNameField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                7,
                "Supplier Name",
                supplierNameField
        );


        // =========================
        // COST PER UNIT
        // =========================

        costPerUnitField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                8,
                "Cost Per Unit *",
                costPerUnitField
        );


        // =========================
        // STATUS
        // =========================

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "In Stock",
                                "Low Stock",
                                "Out of Stock"
                        }
                );

        addFormField(
                formPanel,
                gbc,
                9,
                "Status",
                statusComboBox
        );


        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        centerPanel.setBackground(
                UITheme.BACKGROUND
        );

        centerPanel.add(
                formPanel
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTONS
        // =========================

        clearButton =
                new JButton(
                        "Clear"
                );

        updateButton =
                new JButton(
                        "Update Item"
                );


        UITheme.styleSecondaryButton(
                clearButton
        );

        UITheme.stylePrimaryButton(
                updateButton
        );


        clearButton.addActionListener(
                e -> clearForm()
        );


        updateButton.addActionListener(
                e -> updateInventoryItem()
        );


        refreshButton.addActionListener(
                e -> loadInventoryItems()
        );


        itemComboBox.addActionListener(
                e -> loadSelectedItem()
        );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                updateButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // INITIAL LOAD
        // =========================

        loadInventoryItems();

    }


    // =========================
    // ADD FORM FIELD
    // =========================

    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        JLabel label =
                new JLabel(
                        labelText
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(
                UITheme.TEXT
        );

        panel.add(
                label,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        component.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );

        panel.add(
                component,
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

            clearForm();

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


        loadSelectedItem();

    }


    // =========================
    // LOAD SELECTED ITEM
    // =========================

    private void loadSelectedItem() {

        InventoryItem selectedItem =
                (InventoryItem)
                        itemComboBox
                                .getSelectedItem();


        if (selectedItem == null) {

            return;

        }


        itemNameField.setText(
                selectedItem.getItemName()
        );

        categoryField.setText(
                selectedItem.getCategory()
        );

        quantityField.setText(
                String.valueOf(
                        selectedItem.getQuantity()
                )
        );

        unitComboBox.setSelectedItem(
                selectedItem.getUnit()
        );

        reorderLevelField.setText(
                String.valueOf(
                        selectedItem.getReorderLevel()
                )
        );

        minimumStockField.setText(
                String.valueOf(
                        selectedItem.getMinimumStock()
                )
        );

        supplierNameField.setText(
                selectedItem.getSupplierName()
        );

        costPerUnitField.setText(
                String.valueOf(
                        selectedItem.getCostPerUnit()
                )
        );

        statusComboBox.setSelectedItem(
                selectedItem.getStatus()
        );

    }


    // =========================
    // UPDATE INVENTORY
    // =========================

    private void updateInventoryItem() {

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


        String itemName =
                itemNameField
                        .getText()
                        .trim();

        String category =
                categoryField
                        .getText()
                        .trim();

        String quantityText =
                quantityField
                        .getText()
                        .trim();

        String reorderLevelText =
                reorderLevelField
                        .getText()
                        .trim();

        String minimumStockText =
                minimumStockField
                        .getText()
                        .trim();

        String supplierName =
                supplierNameField
                        .getText()
                        .trim();

        String costPerUnitText =
                costPerUnitField
                        .getText()
                        .trim();


        String unit =
                (String)
                        unitComboBox
                                .getSelectedItem();


        String status =
                (String)
                        statusComboBox
                                .getSelectedItem();


        // =========================
        // REQUIRED VALIDATION
        // =========================

        if (
                itemName.isEmpty()
                        ||
                        category.isEmpty()
                        ||
                        quantityText.isEmpty()
                        ||
                        reorderLevelText.isEmpty()
                        ||
                        minimumStockText.isEmpty()
                        ||
                        costPerUnitText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;

        }


        // =========================
        // NUMBER PARSING
        // =========================

        double quantity;
        double reorderLevel;
        double minimumStock;
        double costPerUnit;


        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

            reorderLevel =
                    Double.parseDouble(
                            reorderLevelText
                    );

            minimumStock =
                    Double.parseDouble(
                            minimumStockText
                    );

            costPerUnit =
                    Double.parseDouble(
                            costPerUnitText
                    );


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity, reorder level, minimum stock and cost per unit must be valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return;

        }


        // =========================
        // NEGATIVE VALUE CHECK
        // =========================

        if (
                quantity < 0
                        ||
                        reorderLevel < 0
                        ||
                        minimumStock < 0
                        ||
                        costPerUnit < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Numeric values cannot be negative.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            return;

        }


        // =========================
        // UPDATE OBJECT
        // =========================

        selectedItem.setItemName(
                itemName
        );

        selectedItem.setCategory(
                category
        );

        selectedItem.setQuantity(
                quantity
        );

        selectedItem.setUnit(
                unit
        );

        selectedItem.setReorderLevel(
                reorderLevel
        );

        selectedItem.setMinimumStock(
                minimumStock
        );

        selectedItem.setSupplierName(
                supplierName
        );

        selectedItem.setCostPerUnit(
                costPerUnit
        );

        selectedItem.setStatus(
                status
        );


        // =========================
        // DATABASE UPDATE
        // =========================

        boolean success =
                inventoryController
                        .updateInventoryItem(
                                selectedItem
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Inventory item updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            loadInventoryItems();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update inventory item.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    // =========================
    // CLEAR FORM
    // =========================

    private void clearForm() {

        itemNameField.setText("");

        categoryField.setText("");

        quantityField.setText("");

        reorderLevelField.setText("");

        minimumStockField.setText("");

        supplierNameField.setText("");

        costPerUnitField.setText("");


        if (unitComboBox.getItemCount() > 0) {

            unitComboBox.setSelectedIndex(
                    0
            );

        }


        if (statusComboBox.getItemCount() > 0) {

            statusComboBox.setSelectedIndex(
                    0
            );

        }

    }


    // =========================
    // COMBO BOX DISPLAY
    // =========================

    @Override
    public String toString() {

        return "Inventory";

    }

}

