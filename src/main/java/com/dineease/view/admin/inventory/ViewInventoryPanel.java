package com.dineease.view.admin.inventory;

import com.dineease.controller.InventoryController;
import com.dineease.model.InventoryItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewInventoryPanel extends JPanel {

    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    private InventoryController inventoryController;

    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> statusComboBox;

    private List<InventoryItem> inventoryItems;


    // =========================
    // CONSTRUCTOR
    // =========================

    public ViewInventoryPanel() {

        inventoryController = new InventoryController();

        setLayout(new BorderLayout(15, 15));

        setBackground(UITheme.BACKGROUND);

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel("Inventory Management");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(UITheme.TEXT);


        // =========================
        // FILTER PANEL
        // =========================

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                5
                        )
                );

        filterPanel.setBackground(UITheme.BACKGROUND);


        // SEARCH

        searchField =
                new JTextField(18);

        searchField.setToolTipText(
                "Search by item name"
        );


        // CATEGORY

        categoryComboBox =
                new JComboBox<>();

        categoryComboBox.addItem("All Categories");


        // STATUS

        statusComboBox =
                new JComboBox<>();

        statusComboBox.addItem("All Status");


        // REFRESH BUTTON

        JButton refreshButton =
                new JButton("↻ Refresh");

        UITheme.stylePrimaryButton(
                refreshButton
        );


        filterPanel.add(
                new JLabel("Search:")
        );

        filterPanel.add(
                searchField
        );

        filterPanel.add(
                new JLabel("Category:")
        );

        filterPanel.add(
                categoryComboBox
        );

        filterPanel.add(
                new JLabel("Status:")
        );

        filterPanel.add(
                statusComboBox
        );

        filterPanel.add(
                refreshButton
        );


        // =========================
        // HEADER PANEL
        // =========================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );

        headerPanel.add(
                title,
                BorderLayout.NORTH
        );

        headerPanel.add(
                filterPanel,
                BorderLayout.SOUTH
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "ID",
                "Item Name",
                "Category",
                "Quantity",
                "Unit",
                "Reorder Level",
                "Min Stock",
                "Supplier",
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
                35
        );


        inventoryTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        inventoryTable.setBackground(
                UITheme.CARD
        );


        inventoryTable.setForeground(
                UITheme.TEXT
        );


        inventoryTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );


        inventoryTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );


        inventoryTable.setAutoCreateRowSorter(
                true
        );


        // =========================
        // CENTER NUMERIC COLUMNS
        // =========================

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        inventoryTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);


        inventoryTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);


        inventoryTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(center);


        inventoryTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(center);


        // =========================
        // SCROLL PANE
        // =========================

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


        // =========================
        // ACTION LISTENERS
        // =========================

        refreshButton.addActionListener(
                e -> loadInventory()
        );


        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                filterInventory();

                            }


                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                filterInventory();

                            }


                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {

                                filterInventory();

                            }

                        }
                );


        categoryComboBox.addActionListener(
                e -> filterInventory()
        );


        statusComboBox.addActionListener(
                e -> filterInventory()
        );


        // =========================
        // LOAD DATA
        // =========================

        loadInventory();

    }


    // =========================
    // LOAD INVENTORY
    // =========================

    private void loadInventory() {

        tableModel.setRowCount(0);

        inventoryItems =
                inventoryController
                        .getAllInventoryItems();


        categoryComboBox.removeAllItems();

        categoryComboBox.addItem(
                "All Categories"
        );


        statusComboBox.removeAllItems();

        statusComboBox.addItem(
                "All Status"
        );


        java.util.Set<String> categories =
                new java.util.TreeSet<>();


        java.util.Set<String> statuses =
                new java.util.TreeSet<>();


        for (InventoryItem item : inventoryItems) {

            if (item.getCategory() != null &&
                    !item.getCategory().trim().isEmpty()) {

                categories.add(
                        item.getCategory()
                );

            }


            if (item.getStatus() != null &&
                    !item.getStatus().trim().isEmpty()) {

                statuses.add(
                        item.getStatus()
                );

            }

        }


        for (String category : categories) {

            categoryComboBox.addItem(
                    category
            );

        }


        for (String status : statuses) {

            statusComboBox.addItem(
                    status
            );

        }


        displayInventory(
                inventoryItems
        );

    }


    // =========================
    // DISPLAY INVENTORY
    // =========================

    private void displayInventory(
            List<InventoryItem> items
    ) {

        tableModel.setRowCount(0);


        for (InventoryItem item : items) {

            Object[] row = {

                    item.getInventoryId(),

                    item.getItemName(),

                    item.getCategory(),

                    item.getQuantity(),

                    item.getUnit(),

                    item.getReorderLevel(),

                    item.getMinimumStock(),

                    item.getSupplierName(),

                    item.getStatus()

            };


            tableModel.addRow(
                    row
            );

        }


        applyStatusRenderer();

    }


    // =========================
    // FILTER INVENTORY
    // =========================

    private void filterInventory() {

        if (inventoryItems == null) {

            return;

        }


        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String selectedCategory =
                (String)
                        categoryComboBox
                                .getSelectedItem();


        String selectedStatus =
                (String)
                        statusComboBox
                                .getSelectedItem();


        java.util.List<InventoryItem> filteredItems =
                new java.util.ArrayList<>();


        for (InventoryItem item : inventoryItems) {

            boolean matchesSearch =
                    searchText.isEmpty()
                            ||
                            (
                                    item.getItemName() != null
                                            &&
                                            item.getItemName()
                                                    .toLowerCase()
                                                    .contains(searchText)
                            );


            boolean matchesCategory =
                    selectedCategory == null
                            ||
                            selectedCategory.equals(
                                    "All Categories"
                            )
                            ||
                            selectedCategory.equals(
                                    item.getCategory()
                            );


            boolean matchesStatus =
                    selectedStatus == null
                            ||
                            selectedStatus.equals(
                                    "All Status"
                            )
                            ||
                            selectedStatus.equals(
                                    item.getStatus()
                            );


            if (
                    matchesSearch
                            &&
                            matchesCategory
                            &&
                            matchesStatus
            ) {

                filteredItems.add(
                        item
                );

            }

        }


        displayInventory(
                filteredItems
        );

    }


    // =========================
    // STATUS CELL RENDERER
    // =========================

    private void applyStatusRenderer() {

        inventoryTable
                .getColumnModel()
                .getColumn(8)
                .setCellRenderer(
                        new DefaultTableCellRenderer() {

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


                                String status =
                                        value == null
                                                ? ""
                                                : value.toString();


                                if (
                                        status.equalsIgnoreCase(
                                                "Out of Stock"
                                        )
                                ) {

                                    component.setForeground(
                                            Color.RED
                                    );

                                    component.setFont(
                                            component
                                                    .getFont()
                                                    .deriveFont(
                                                            Font.BOLD
                                                    )
                                    );

                                }

                                else if (
                                        status.equalsIgnoreCase(
                                                "Low Stock"
                                        )
                                ) {

                                    component.setForeground(
                                            Color.ORANGE
                                    );

                                    component.setFont(
                                            component
                                                    .getFont()
                                                    .deriveFont(
                                                            Font.BOLD
                                                    )

                                    );

                                }

                                else {

                                    component.setForeground(
                                            UITheme.TEXT
                                    );

                                    component.setFont(
                                            component
                                                    .getFont()
                                                    .deriveFont(
                                                            Font.PLAIN
                                                    )
                                    );

                                }


                                return component;

                            }

                        }
                );

    }

}

