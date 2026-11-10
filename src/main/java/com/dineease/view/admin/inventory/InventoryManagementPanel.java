package com.dineease.view.admin.inventory;

import com.dineease.controller.InventoryController;
import com.dineease.model.InventoryItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryManagementPanel extends JPanel {

    private InventoryController inventoryController;

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> statusComboBox;

    private JButton searchButton;
    private JButton refreshButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JLabel totalLabel;
    private JLabel lowStockLabel;
    private JLabel outOfStockLabel;

    private List<InventoryItem> allInventoryItems;


    // =========================
    // CONSTRUCTOR
    // =========================

    public InventoryManagementPanel() {

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
                        25,
                        20,
                        25
                )
        );

        createTopPanel();

        createSummaryPanel();

        createInventoryTable();

        createBottomPanel();

        loadInventory();
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
                        "Inventory Management"
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
                        "Manage restaurant stock and supplies"
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
        // SEARCH AREA
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
                new JLabel("Search:")
        );


        searchField =
                new JTextField(
                        12
                );

        searchField.setToolTipText(
                "Search by item or supplier"
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
                new JLabel("Category:")
        );


        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Vegetables",
                                "Dairy",
                                "Meat",
                                "Beverages",
                                "Grains",
                                "Spices",
                                "Other"
                        }
                );


        searchPanel.add(
                categoryComboBox
        );


        searchPanel.add(
                new JLabel("Status:")
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "In Stock",
                                "Low Stock",
                                "Out of Stock"
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
                e -> filterInventory()
        );


        searchField.addActionListener(
                e -> filterInventory()
        );


        categoryComboBox.addActionListener(
                e -> filterInventory()
        );


        statusComboBox.addActionListener(
                e -> filterInventory()
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


        lowStockLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        outOfStockLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        summaryPanel.add(
                createSummaryCard(
                        "Total Items",
                        totalLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Low Stock",
                        lowStockLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Out of Stock",
                        outOfStockLabel
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
    // INVENTORY TABLE
    // =========================

    private void createInventoryTable() {

        String[] columns = {

                "ID",
                "Item Name",
                "Category",
                "Quantity",
                "Unit",
                "Reorder Level",
                "Minimum Stock",
                "Supplier",
                "Cost / Unit",
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


        inventoryTable =
                new JTable(
                        tableModel
                );


        inventoryTable.setRowHeight(
                32
        );


        inventoryTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        inventoryTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        inventoryTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        // =========================
        // STATUS RENDERER
        // =========================

        inventoryTable
                .getColumnModel()
                .getColumn(9)
                .setCellRenderer(
                        new StatusCellRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        inventoryTable
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
    // STATUS CELL RENDERER
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
                                "In Stock"
                        )
                ) {

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
                                "Low Stock"
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
                                "Out of Stock"
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


        addButton =
                new JButton(
                        "Add Item"
                );


        updateButton =
                new JButton(
                        "Update Item"
                );


        deleteButton =
                new JButton(
                        "Delete Item"
                );


        UITheme.styleSecondaryButton(
                refreshButton
        );


        UITheme.stylePrimaryButton(
                addButton
        );


        UITheme.styleSecondaryButton(
                updateButton
        );


        UITheme.styleSecondaryButton(
                deleteButton
        );


        panel.add(
                refreshButton
        );


        panel.add(
                addButton
        );


        panel.add(
                updateButton
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

                    categoryComboBox.setSelectedItem(
                            "All"
                    );

                    statusComboBox.setSelectedItem(
                            "All"
                    );

                    loadInventory();
                }
        );


        addButton.addActionListener(
                e -> showAddInventoryDialog()
        );


        updateButton.addActionListener(
                e -> showUpdateInventoryDialog()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedInventory()
        );


        add(
                panel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD INVENTORY
    // =========================

    private void loadInventory() {

        allInventoryItems =
                inventoryController
                        .getAllInventoryItems();


        if (
                allInventoryItems == null
        ) {

            allInventoryItems =
                    new java.util.ArrayList<>();
        }


        displayInventory(
                allInventoryItems
        );


        updateSummary(
                allInventoryItems
        );
    }


    // =========================
    // DISPLAY INVENTORY
    // =========================

    private void displayInventory(
            List<InventoryItem> items
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                InventoryItem item :
                items
        ) {

            Object[] row = {

                    item.getInventoryId(),

                    item.getItemName(),

                    item.getCategory(),

                    String.format(
                            "%.2f",
                            item.getQuantity()
                    ),

                    item.getUnit(),

                    String.format(
                            "%.2f",
                            item.getReorderLevel()
                    ),

                    String.format(
                            "%.2f",
                            item.getMinimumStock()
                    ),

                    item.getSupplierName(),

                    String.format(
                            "₹%.2f",
                            item.getCostPerUnit()
                    ),

                    item.getStatus()
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
            List<InventoryItem> items
    ) {

        int total = items.size();

        int lowStock = 0;

        int outOfStock = 0;


        for (
                InventoryItem item :
                items
        ) {

            if (
                    "Low Stock".equalsIgnoreCase(
                            item.getStatus()
                    )
            ) {

                lowStock++;
            }


            if (
                    "Out of Stock".equalsIgnoreCase(
                            item.getStatus()
                    )
            ) {

                outOfStock++;
            }
        }


        totalLabel.setText(
                String.valueOf(total)
        );


        lowStockLabel.setText(
                String.valueOf(lowStock)
        );


        outOfStockLabel.setText(
                String.valueOf(outOfStock)
        );
    }


    // =========================
    // FILTER INVENTORY
    // =========================

    private void filterInventory() {

        if (
                allInventoryItems == null
        ) {

            return;
        }


        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String selectedCategory =
                categoryComboBox
                        .getSelectedItem()
                        .toString();


        String selectedStatus =
                statusComboBox
                        .getSelectedItem()
                        .toString();


        List<InventoryItem> filteredItems =
                allInventoryItems
                        .stream()
                        .filter(
                                item -> {

                                    boolean searchMatch =
                                            searchText.isEmpty()
                                                    ||
                                                    item.getItemName()
                                                            .toLowerCase()
                                                            .contains(
                                                                    searchText
                                                            )
                                                    ||
                                                    (
                                                            item.getSupplierName()
                                                                    != null
                                                                    &&
                                                                    item.getSupplierName()
                                                                            .toLowerCase()
                                                                            .contains(
                                                                                    searchText
                                                                            )
                                                    );


                                    boolean categoryMatch =
                                            selectedCategory.equals(
                                                    "All"
                                            )
                                                    ||
                                                    item.getCategory()
                                                            .equalsIgnoreCase(
                                                                    selectedCategory
                                                            );


                                    boolean statusMatch =
                                            selectedStatus.equals(
                                                    "All"
                                            )
                                                    ||
                                                    item.getStatus()
                                                            .equalsIgnoreCase(
                                                                    selectedStatus
                                                            );


                                    return searchMatch
                                            &&
                                            categoryMatch
                                            &&
                                            statusMatch;
                                }
                        )
                        .toList();


        displayInventory(
                filteredItems
        );
    }


    // =========================
    // SELECTED ITEM
    // =========================

    private InventoryItem getSelectedItem() {

        int row =
                inventoryTable.getSelectedRow();


        if (
                row < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an inventory item first."
            );

            return null;
        }


        int inventoryId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );


        for (
                InventoryItem item :
                allInventoryItems
        ) {

            if (
                    item.getInventoryId()
                            == inventoryId
            ) {

                return item;
            }
        }


        return null;
    }


    // =========================
    // ADD INVENTORY DIALOG
    // =========================

    private void showAddInventoryDialog() {

        JTextField itemName =
                new JTextField();


        JTextField category =
                new JTextField();


        JTextField quantity =
                new JTextField();


        JTextField unit =
                new JTextField();


        JTextField reorderLevel =
                new JTextField();


        JTextField minimumStock =
                new JTextField();


        JTextField supplier =
                new JTextField();


        JTextField cost =
                new JTextField();


        JComboBox<String> status =
                new JComboBox<>(
                        new String[]{
                                "In Stock",
                                "Low Stock",
                                "Out of Stock"
                        }
                );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                9,
                                2,
                                8,
                                8
                        )
                );


        panel.add(
                new JLabel("Item Name:")
        );

        panel.add(itemName);


        panel.add(
                new JLabel("Category:")
        );

        panel.add(category);


        panel.add(
                new JLabel("Quantity:")
        );

        panel.add(quantity);


        panel.add(
                new JLabel("Unit:")
        );

        panel.add(unit);


        panel.add(
                new JLabel("Reorder Level:")
        );

        panel.add(reorderLevel);


        panel.add(
                new JLabel("Minimum Stock:")
        );

        panel.add(minimumStock);


        panel.add(
                new JLabel("Supplier:")
        );

        panel.add(supplier);


        panel.add(
                new JLabel("Cost / Unit:")
        );

        panel.add(cost);


        panel.add(
                new JLabel("Status:")
        );

        panel.add(status);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Inventory Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result
                        != JOptionPane.OK_OPTION
        ) {

            return;
        }


        try {

            String name =
                    itemName.getText().trim();


            if (
                    name.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Item name is required."
                );

                return;
            }


            InventoryItem inventoryItem =
                    new InventoryItem(
                            name,
                            category.getText().trim(),
                            Double.parseDouble(
                                    quantity.getText().trim()
                            ),
                            unit.getText().trim(),
                            Double.parseDouble(
                                    reorderLevel.getText().trim()
                            ),
                            status.getSelectedItem().toString(),
                            Double.parseDouble(
                                    minimumStock.getText().trim()
                            ),
                            supplier.getText().trim(),
                            Double.parseDouble(
                                    cost.getText().trim()
                            )
                    );


            boolean success =
                    inventoryController
                            .addInventoryItem(
                                    inventoryItem
                            );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Inventory item added successfully."
                );

                loadInventory();

            }
            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add inventory item.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // UPDATE INVENTORY DIALOG
    // =========================

    private void showUpdateInventoryDialog() {

        InventoryItem selected =
                getSelectedItem();


        if (
                selected == null
        ) {

            return;
        }


        JTextField itemName =
                new JTextField(
                        selected.getItemName()
                );


        JTextField category =
                new JTextField(
                        selected.getCategory()
                );


        JTextField quantity =
                new JTextField(
                        String.valueOf(
                                selected.getQuantity()
                        )
                );


        JTextField unit =
                new JTextField(
                        selected.getUnit()
                );


        JTextField reorderLevel =
                new JTextField(
                        String.valueOf(
                                selected.getReorderLevel()
                        )
                );


        JTextField minimumStock =
                new JTextField(
                        String.valueOf(
                                selected.getMinimumStock()
                        )
                );


        JTextField supplier =
                new JTextField(
                        selected.getSupplierName() == null
                                ? ""
                                : selected.getSupplierName()
                );


        JTextField cost =
                new JTextField(
                        String.valueOf(
                                selected.getCostPerUnit()
                        )
                );


        JComboBox<String> status =
                new JComboBox<>(
                        new String[]{
                                "In Stock",
                                "Low Stock",
                                "Out of Stock"
                        }
                );


        status.setSelectedItem(
                selected.getStatus()
        );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                9,
                                2,
                                8,
                                8
                        )
                );


        panel.add(
                new JLabel("Item Name:")
        );

        panel.add(itemName);


        panel.add(
                new JLabel("Category:")
        );

        panel.add(category);


        panel.add(
                new JLabel("Quantity:")
        );

        panel.add(quantity);


        panel.add(
                new JLabel("Unit:")
        );

        panel.add(unit);


        panel.add(
                new JLabel("Reorder Level:")
        );

        panel.add(reorderLevel);


        panel.add(
                new JLabel("Minimum Stock:")
        );

        panel.add(minimumStock);


        panel.add(
                new JLabel("Supplier:")
        );

        panel.add(supplier);


        panel.add(
                new JLabel("Cost / Unit:")
        );

        panel.add(cost);


        panel.add(
                new JLabel("Status:")
        );

        panel.add(status);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Inventory Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result
                        != JOptionPane.OK_OPTION
        ) {

            return;
        }


        try {

            selected.setItemName(
                    itemName.getText().trim()
            );


            selected.setCategory(
                    category.getText().trim()
            );


            selected.setQuantity(
                    Double.parseDouble(
                            quantity.getText().trim()
                    )
            );


            selected.setUnit(
                    unit.getText().trim()
            );


            selected.setReorderLevel(
                    Double.parseDouble(
                            reorderLevel.getText().trim()
                    )
            );


            selected.setMinimumStock(
                    Double.parseDouble(
                            minimumStock.getText().trim()
                    )
            );


            selected.setSupplierName(
                    supplier.getText().trim()
            );


            selected.setCostPerUnit(
                    Double.parseDouble(
                            cost.getText().trim()
                    )
            );


            selected.setStatus(
                    status.getSelectedItem().toString()
            );


            boolean success =
                    inventoryController
                            .updateInventoryItem(
                                    selected
                            );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Inventory item updated successfully."
                );

                loadInventory();

            }
            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update inventory item.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // DELETE INVENTORY ITEM
    // =========================

    private void deleteSelectedInventory() {

        InventoryItem selected =
                getSelectedItem();


        if (
                selected == null
        ) {

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete "
                                + selected.getItemName()
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                inventoryController
                        .deleteInventoryItem(
                                selected.getInventoryId()
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Inventory item deleted successfully."
            );

            loadInventory();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete inventory item.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}