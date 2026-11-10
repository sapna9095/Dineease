 package com.dineease.view.admin.inventory;

import com.dineease.controller.InventoryController;
import com.dineease.model.InventoryItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class AddInventoryPanel extends JPanel {

    private JTextField itemNameField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField reorderLevelField;
    private JTextField minimumStockField;
    private JTextField supplierNameField;
    private JTextField costPerUnitField;
    private JComboBox<String> statusComboBox;

    private InventoryController inventoryController;


    // =========================
    // CONSTRUCTOR
    // =========================

    public AddInventoryPanel() {

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
                        "Add Inventory Item"
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
        // ITEM NAME
        // =========================

        itemNameField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                0,
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
                1,
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
                2,
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
                3,
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
                4,
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
                5,
                "Minimum Stock *",
                minimumStockField
        );


        // =========================
        // SUPPLIER NAME
        // =========================

        supplierNameField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                6,
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
                7,
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
                8,
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

        JButton clearButton =
                new JButton(
                        "Clear"
                );

        JButton addButton =
                new JButton(
                        "Add Item"
                );


        UITheme.styleSecondaryButton(
                clearButton
        );

        UITheme.stylePrimaryButton(
                addButton
        );


        clearButton.addActionListener(
                e -> clearForm()
        );


        addButton.addActionListener(
                e -> addInventoryItem()
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
                addButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

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
                        250,
                        35
                )
        );

        panel.add(
                component,
                gbc
        );

    }


    // =========================
    // ADD INVENTORY ITEM
    // =========================

    private void addInventoryItem() {

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
        // REQUIRED FIELD VALIDATION
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
        // NUMBER VALIDATION
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
        // NEGATIVE VALUE VALIDATION
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
        // CREATE INVENTORY OBJECT
        // =========================

        InventoryItem item =
                new InventoryItem(
                        itemName,
                        category,
                        quantity,
                        unit,
                        reorderLevel,
                        status,
                        minimumStock,
                        supplierName,
                        costPerUnit
                );


        // =========================
        // SAVE TO DATABASE
        // =========================

        boolean success =
                inventoryController
                        .addInventoryItem(
                                item
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Inventory item added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add inventory item.",
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


        unitComboBox.setSelectedIndex(
                0
        );

        statusComboBox.setSelectedIndex(
                0
        );


        itemNameField.requestFocus();

    }

}
