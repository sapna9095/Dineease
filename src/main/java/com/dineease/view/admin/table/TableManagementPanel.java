package com.dineease.view.admin.table;

import com.dineease.controller.RestaurantTableController;
import com.dineease.model.RestaurantTable;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableManagementPanel extends JPanel {

    private RestaurantTableController tableController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> statusComboBox;

    private JButton searchButton;
    private JButton refreshButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JLabel totalLabel;
    private JLabel availableLabel;
    private JLabel occupiedLabel;
    private JLabel reservedLabel;

    private List<RestaurantTable> allTables;


    // =========================
    // CONSTRUCTOR
    // =========================

    public TableManagementPanel() {

        tableController =
                new RestaurantTableController();

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

        createTable();

        createBottomPanel();

        loadTables();
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
                        "Table Management"
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
                        "Manage restaurant seating and table status"
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
                        10
                );


        searchField.setToolTipText(
                "Search by table number or location"
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
                new JLabel("Status:")
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Available",
                                "Occupied",
                                "Reserved"
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
                e -> filterTables()
        );


        searchField.addActionListener(
                e -> filterTables()
        );


        statusComboBox.addActionListener(
                e -> filterTables()
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
                                4,
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


        availableLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        occupiedLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        reservedLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );


        summaryPanel.add(
                createSummaryCard(
                        "Total Tables",
                        totalLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Available",
                        availableLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Occupied",
                        occupiedLabel
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "Reserved",
                        reservedLabel
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
    // TABLE
    // =========================

    private void createTable() {

        String[] columns = {

                "ID",
                "Table Number",
                "Capacity",
                "Location",
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


        table =
                new JTable(
                        tableModel
                );


        table.setRowHeight(
                34
        );


        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );


        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        // Center numeric columns

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();


        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);


        table.getColumnModel()
                .getColumn(1)
                .setCellRenderer(center);


        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(center);


        // Status renderer

        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusCellRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        table
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
    // STATUS RENDERER
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


            if (
                    !isSelected
                            &&
                            value != null
            ) {

                String status =
                        value.toString();


                if (
                        status.equalsIgnoreCase(
                                "Available"
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
                                "Occupied"
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
                else if (
                        status.equalsIgnoreCase(
                                "Reserved"
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
                        "Add Table"
                );


        updateButton =
                new JButton(
                        "Update Table"
                );


        deleteButton =
                new JButton(
                        "Delete Table"
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
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    statusComboBox.setSelectedItem(
                            "All"
                    );

                    loadTables();
                }
        );


        addButton.addActionListener(
                e -> showAddTableDialog()
        );


        updateButton.addActionListener(
                e -> showUpdateTableDialog()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedTable()
        );


        add(
                panel,
                BorderLayout.SOUTH
        );
    }


    // =========================
    // LOAD TABLES
    // =========================

    private void loadTables() {

        allTables =
                tableController.getAllTables();


        if (
                allTables == null
        ) {

            allTables =
                    new java.util.ArrayList<>();
        }


        displayTables(
                allTables
        );


        updateSummary(
                allTables
        );
    }


    // =========================
    // DISPLAY TABLES
    // =========================

    private void displayTables(
            List<RestaurantTable> tables
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                RestaurantTable restaurantTable :
                tables
        ) {

            Object[] row = {

                    restaurantTable.getTableId(),

                    restaurantTable.getTableNumber(),

                    restaurantTable.getCapacity(),

                    restaurantTable.getLocation(),

                    restaurantTable.getStatus()

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
            List<RestaurantTable> tables
    ) {

        int total = tables.size();

        int available = 0;

        int occupied = 0;

        int reserved = 0;


        for (
                RestaurantTable restaurantTable :
                tables
        ) {

            String status =
                    restaurantTable.getStatus();


            if (
                    "Available".equalsIgnoreCase(
                            status
                    )
            ) {

                available++;
            }


            if (
                    "Occupied".equalsIgnoreCase(
                            status
                    )
            ) {

                occupied++;
            }


            if (
                    "Reserved".equalsIgnoreCase(
                            status
                    )
            ) {

                reserved++;
            }
        }


        totalLabel.setText(
                String.valueOf(total)
        );


        availableLabel.setText(
                String.valueOf(available)
        );


        occupiedLabel.setText(
                String.valueOf(occupied)
        );


        reservedLabel.setText(
                String.valueOf(reserved)
        );
    }


    // =========================
    // FILTER TABLES
    // =========================

    private void filterTables() {

        if (
                allTables == null
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


        List<RestaurantTable> filteredTables =
                allTables
                        .stream()
                        .filter(
                                restaurantTable -> {

                                    boolean searchMatch =

                                            searchText.isEmpty()

                                                    ||

                                                    String.valueOf(
                                                                    restaurantTable
                                                                            .getTableNumber()
                                                            )
                                                            .contains(
                                                                    searchText
                                                            )

                                                    ||

                                                    (
                                                            restaurantTable
                                                                    .getLocation()
                                                                    != null

                                                                    &&

                                                                    restaurantTable
                                                                            .getLocation()
                                                                            .toLowerCase()
                                                                            .contains(
                                                                                    searchText
                                                                            )
                                                    );


                                    boolean statusMatch =

                                            selectedStatus.equals(
                                                    "All"
                                            )

                                                    ||

                                                    restaurantTable
                                                            .getStatus()
                                                            .equalsIgnoreCase(
                                                                    selectedStatus
                                                            );


                                    return searchMatch
                                            &&
                                            statusMatch;
                                }
                        )
                        .toList();


        displayTables(
                filteredTables
        );
    }


    // =========================
    // SELECTED TABLE
    // =========================

    private RestaurantTable getSelectedTable() {

        int row =
                table.getSelectedRow();


        if (
                row < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a table first."
            );

            return null;
        }


        int tableId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );


        for (
                RestaurantTable restaurantTable :
                allTables
        ) {

            if (
                    restaurantTable.getTableId()
                            == tableId
            ) {

                return restaurantTable;
            }
        }


        return null;
    }


    // =========================
    // ADD TABLE
    // =========================

    private void showAddTableDialog() {

        JTextField tableNumber =
                new JTextField();


        JTextField capacity =
                new JTextField();


        JTextField location =
                new JTextField();


        JComboBox<String> status =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Occupied",
                                "Reserved"
                        }
                );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                8,
                                8
                        )
                );


        panel.add(
                new JLabel(
                        "Table Number:"
                )
        );

        panel.add(
                tableNumber
        );


        panel.add(
                new JLabel(
                        "Capacity:"
                )
        );

        panel.add(
                capacity
        );


        panel.add(
                new JLabel(
                        "Location:"
                )
        );

        panel.add(
                location
        );


        panel.add(
                new JLabel(
                        "Status:"
                )
        );

        panel.add(
                status
        );


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Restaurant Table",
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

            int number =
                    Integer.parseInt(
                            tableNumber
                                    .getText()
                                    .trim()
                    );


            int tableCapacity =
                    Integer.parseInt(
                            capacity
                                    .getText()
                                    .trim()
                    );


            if (
                    number <= 0
                            ||
                            tableCapacity <= 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Table number and capacity must be greater than 0."
                );

                return;
            }


            RestaurantTable restaurantTable =
                    new RestaurantTable(
                            number,
                            tableCapacity,
                            location
                                    .getText()
                                    .trim(),
                            status
                                    .getSelectedItem()
                                    .toString()
                    );


            boolean success =
                    tableController.addTable(
                            restaurantTable
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Table added successfully."
                );

                loadTables();

            }
            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add table.",
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
    // UPDATE TABLE
    // =========================

    private void showUpdateTableDialog() {

        RestaurantTable selected =
                getSelectedTable();


        if (
                selected == null
        ) {

            return;
        }


        JTextField tableNumber =
                new JTextField(
                        String.valueOf(
                                selected.getTableNumber()
                        )
                );


        JTextField capacity =
                new JTextField(
                        String.valueOf(
                                selected.getCapacity()
                        )
                );


        JTextField location =
                new JTextField(
                        selected.getLocation()
                                == null
                                ? ""
                                : selected.getLocation()
                );


        JComboBox<String> status =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Occupied",
                                "Reserved"
                        }
                );


        status.setSelectedItem(
                selected.getStatus()
        );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                8,
                                8
                        )
                );


        panel.add(
                new JLabel(
                        "Table Number:"
                )
        );

        panel.add(
                tableNumber
        );


        panel.add(
                new JLabel(
                        "Capacity:"
                )
        );

        panel.add(
                capacity
        );


        panel.add(
                new JLabel(
                        "Location:"
                )
        );

        panel.add(
                location
        );


        panel.add(
                new JLabel(
                        "Status:"
                )
        );

        panel.add(
                status
        );


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Restaurant Table",
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

            int number =
                    Integer.parseInt(
                            tableNumber
                                    .getText()
                                    .trim()
                    );


            int tableCapacity =
                    Integer.parseInt(
                            capacity
                                    .getText()
                                    .trim()
                    );


            if (
                    number <= 0
                            ||
                            tableCapacity <= 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Table number and capacity must be greater than 0."
                );

                return;
            }


            selected.setTableNumber(
                    number
            );


            selected.setCapacity(
                    tableCapacity
            );


            selected.setLocation(
                    location
                            .getText()
                            .trim()
            );


            selected.setStatus(
                    status
                            .getSelectedItem()
                            .toString()
            );


            boolean success =
                    tableController.updateTable(
                            selected
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Table updated successfully."
                );

                loadTables();

            }
            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update table.",
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
    // DELETE TABLE
    // =========================

    private void deleteSelectedTable() {

        RestaurantTable selected =
                getSelectedTable();


        if (
                selected == null
        ) {

            return;
        }


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete Table "
                                + selected.getTableNumber()
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
                tableController.deleteTable(
                        selected.getTableId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Table deleted successfully."
            );

            loadTables();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete table.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

