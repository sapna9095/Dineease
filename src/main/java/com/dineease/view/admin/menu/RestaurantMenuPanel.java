package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class RestaurantMenuPanel extends JPanel {

    private MenuController menuController;

    private JPanel menuGridPanel;

    private JTextField searchField;

    private JComboBox<String> categoryComboBox;

    private List<MenuItem> allMenuItems;


    // =========================
    // COLORS
    // =========================

    private static final Color MENU_BACKGROUND =
            new Color(248, 246, 241);

    private static final Color CARD_BACKGROUND =
            Color.WHITE;

    private static final Color MUTED_TEXT =
            new Color(120, 115, 105);

    private static final Color PRICE_COLOR =
            UITheme.PRIMARY;


    // =========================
    // CONSTRUCTOR
    // =========================

    public RestaurantMenuPanel() {

        menuController =
                new MenuController();

        setLayout(
                new BorderLayout(
                        0,
                        15
                )
        );

        setBackground(
                MENU_BACKGROUND
        );

        setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        createHeader();

        createMenuArea();

        loadMenuItems();
    }


    // =========================
    // HEADER
    // =========================

    private void createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout(
                                20,
                                15
                        )
                );

        header.setBackground(
                MENU_BACKGROUND
        );


        // =========================
        // RESTAURANT TITLE
        // =========================

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                MENU_BACKGROUND
        );


        JLabel restaurantName =
                new JLabel(
                        "DINEEASE AI"
                );

        restaurantName.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        30
                )
        );

        restaurantName.setForeground(
                PRICE_COLOR
        );


        JLabel restaurantSubtitle =
                new JLabel(
                        "RESTAURANT & CAFE"
                );

        restaurantSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        restaurantSubtitle.setForeground(
                MUTED_TEXT
        );


        JLabel menuTitle =
                new JLabel(
                        "Our Menu"
                );

        menuTitle.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        22
                )
        );

        menuTitle.setForeground(
                new Color(
                        55,
                        50,
                        45
                )
        );


        titlePanel.add(
                restaurantName
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        2
                )
        );

        titlePanel.add(
                restaurantSubtitle
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        titlePanel.add(
                menuTitle
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );


        // =========================
        // SEARCH
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
                MENU_BACKGROUND
        );


        searchField =
                new JTextField(
                        15
                );

        searchField.setPreferredSize(
                new Dimension(
                        160,
                        34
                )
        );

        searchField.setToolTipText(
                "Search menu items"
        );


        JButton searchButton =
                new JButton(
                        "Search"
                );

        UITheme.stylePrimaryButton(
                searchButton
        );


        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Starter",
                                "Main Course",
                                "Beverage",
                                "Dessert",
                                "Salad",
                                "Soup"
                        }
                );

        categoryComboBox.setPreferredSize(
                new Dimension(
                        140,
                        34
                )
        );


        searchPanel.add(
                new JLabel(
                        "Search:"
                )
        );

        searchPanel.add(
                searchField
        );

        searchPanel.add(
                searchButton
        );

        searchPanel.add(
                new JLabel(
                        "Category:"
                )
        );

        searchPanel.add(
                categoryComboBox
        );


        header.add(
                searchPanel,
                BorderLayout.EAST
        );


        add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> filterMenu()
        );


        searchField.addActionListener(
                e -> filterMenu()
        );


        categoryComboBox.addActionListener(
                e -> filterMenu()
        );
    }


    // =========================
    // MENU AREA
    // =========================

    private void createMenuArea() {

        menuGridPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                3,
                                22,
                                22
                        )
                );

        menuGridPanel.setBackground(
                MENU_BACKGROUND
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        menuGridPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setBackground(
                MENU_BACKGROUND
        );

        scrollPane.getViewport()
                .setBackground(
                        MENU_BACKGROUND
                );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        18
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }


    // =========================
    // LOAD ITEMS
    // =========================

    private void loadMenuItems() {

        allMenuItems =
                menuController.getAllMenuItems();


        displayMenuItems(
                allMenuItems
        );
    }


    // =========================
    // DISPLAY ITEMS
    // =========================

    private void displayMenuItems(
            List<MenuItem> menuItems
    ) {

        menuGridPanel.removeAll();


        menuGridPanel.setLayout(
                new GridLayout(
                        0,
                        3,
                        22,
                        22
                )
        );


        if (
                menuItems == null
                        ||
                        menuItems.isEmpty()
        ) {

            JLabel emptyLabel =
                    new JLabel(
                            "No menu items available"
                    );

            emptyLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            18
                    )
            );

            emptyLabel.setForeground(
                    MUTED_TEXT
            );

            emptyLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            menuGridPanel.setLayout(
                    new BorderLayout()
            );

            menuGridPanel.add(
                    emptyLabel,
                    BorderLayout.CENTER
            );

        }
        else {

            for (
                    MenuItem item :
                    menuItems
            ) {

                menuGridPanel.add(
                        createMenuCard(
                                item
                        )
                );
            }
        }


        menuGridPanel.revalidate();

        menuGridPanel.repaint();
    }


    // =========================
    // MENU CARD
    // =========================

    private JPanel createMenuCard(
            MenuItem item
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                CARD_BACKGROUND
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        221,
                                        213
                                )
                        ),
                        new EmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        // =========================
        // IMAGE
        // =========================

        JLabel imageLabel =
                new JLabel();

        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        imageLabel.setVerticalAlignment(
                SwingConstants.CENTER
        );

        imageLabel.setPreferredSize(
                new Dimension(
                        250,
                        170
                )
        );


        ImageIcon icon =
                loadImage(
                        item.getImagePath()
                );


        if (icon != null) {

            imageLabel.setIcon(
                    icon
            );

        }
        else {

            imageLabel.setText(
                    "No Image Available"
            );

            imageLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            13
                    )
            );

            imageLabel.setForeground(
                    MUTED_TEXT
            );
        }


        card.add(
                imageLabel,
                BorderLayout.NORTH
        );


        // =========================
        // INFORMATION
        // =========================

        JPanel information =
                new JPanel();

        information.setLayout(
                new BoxLayout(
                        information,
                        BoxLayout.Y_AXIS
                )
        );

        information.setBackground(
                CARD_BACKGROUND
        );

        information.setBorder(
                new EmptyBorder(
                        12,
                        5,
                        5,
                        5
                )
        );


        // ITEM NAME

        JLabel itemName =
                new JLabel(
                        item.getItemName()
                );

        itemName.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        19
                )
        );

        itemName.setForeground(
                new Color(
                        45,
                        42,
                        38
                )
        );


        // CATEGORY

        JLabel category =
                new JLabel(
                        item.getCategory()
                );

        category.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        category.setForeground(
                PRICE_COLOR
        );


        // DESCRIPTION

        String description =
                item.getDescription();


        if (
                description == null
                        ||
                        description.trim().isEmpty()
        ) {

            description =
                    "Delicious restaurant special.";
        }


        JLabel descriptionLabel =
                new JLabel(
                        "<html><div style='width:220px;'>"
                                + description
                                + "</div></html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        descriptionLabel.setForeground(
                MUTED_TEXT
        );


        // PRICE

        JLabel price =
                new JLabel(
                        String.format(
                                "₹%.2f",
                                item.getPrice()
                        )
                );

        price.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        price.setForeground(
                PRICE_COLOR
        );


        // STATUS

        JLabel status =
                new JLabel(
                        getStatusText(
                                item.getStatus()
                        )
                );

        status.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );


        if (
                "Available".equalsIgnoreCase(
                        item.getStatus()
                )
        ) {

            status.setForeground(
                    new Color(
                            45,
                            145,
                            75
                    )
            );

        }
        else {

            status.setForeground(
                    new Color(
                            190,
                            65,
                            55
                    )
            );
        }


        information.add(
                itemName
        );

        information.add(
                Box.createVerticalStrut(
                        3
                )
        );

        information.add(
                category
        );

        information.add(
                Box.createVerticalStrut(
                        7
                )
        );

        information.add(
                descriptionLabel
        );

        information.add(
                Box.createVerticalStrut(
                        10
                )
                );

        information.add(
                price
        );

        information.add(
                Box.createVerticalStrut(
                        3
                )
        );

        information.add(
                status
        );


        card.add(
                information,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTONS (View Details & Delete)
        // =========================

        JButton detailsButton =
                new JButton(
                        "Details"
                );

        UITheme.styleSecondaryButton(
                detailsButton
        );

        detailsButton.addActionListener(
                e -> showItemDetails(
                        item
                )
        );

        JButton deleteCardBtn =
                new JButton(
                        "Delete"
                );

        UITheme.styleDangerButton(
                deleteCardBtn
        );

        deleteCardBtn.addActionListener(
                e -> deleteItemDirectly(
                        item
                )
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                5
                        )
                );

        buttonPanel.setBackground(
                CARD_BACKGROUND
        );

        buttonPanel.add(
                detailsButton
        );

        buttonPanel.add(
                deleteCardBtn
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        return card;
    }


    // =========================
    // STATUS TEXT
    // =========================

    private String getStatusText(
            String status
    ) {

        if (
                "Available".equalsIgnoreCase(
                        status
                )
        ) {

            return "● Available";
        }


        return "● Unavailable";
    }


    // =========================
    // LOAD IMAGE
    // =========================

    private ImageIcon loadImage(
            String imagePath
    ) {

        if (
                imagePath == null
                        ||
                        imagePath.trim().isEmpty()
        ) {

            return null;
        }


        try {

            File imageFile =
                    new File(
                            imagePath
                    );


            if (
                    !imageFile.exists()
            ) {

                return null;
            }


            ImageIcon original =
                    new ImageIcon(
                            imageFile.getAbsolutePath()
                    );


            Image image =
                    original.getImage();


            Image scaled =
                    image.getScaledInstance(
                            250,
                            170,
                            Image.SCALE_SMOOTH
                    );


            return new ImageIcon(
                    scaled
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }


    // =========================
    // FILTER
    // =========================

    private void filterMenu() {

        if (
                allMenuItems == null
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


        List<MenuItem> filtered =
                allMenuItems
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
                                                    item.getCategory()
                                                            .toLowerCase()
                                                            .contains(
                                                                    searchText
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


                                    return searchMatch
                                            &&
                                            categoryMatch;
                                }
                        )
                        .toList();


        displayMenuItems(
                filtered
        );
    }


    // =========================
    // DETAILS
    // =========================

    private void showItemDetails(
            MenuItem item
    ) {

        String message =
                "Item: "
                        + item.getItemName()
                        + "\n\n"
                        + "Category: "
                        + item.getCategory()
                        + "\n\n"
                        + "Price: ₹"
                        + String.format(
                        "%.2f",
                        item.getPrice()
                )
                        + "\n\n"
                        + "Description: "
                        + item.getDescription()
                        + "\n\n"
                        + "Status: "
                        + item.getStatus();


        JOptionPane.showMessageDialog(
                this,
                message,
                "Menu Item Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void deleteItemDirectly(MenuItem item) {
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \"" + item.getItemName() + "\" (ID: #" + item.getItemId() + ")?",
                "Confirm Delete Menu Item",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    return menuController.deleteMenuItem(item.getItemId());
                }

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane.showMessageDialog(
                                    RestaurantMenuPanel.this,
                                    "\"" + item.getItemName() + "\" has been deleted successfully!",
                                    "Item Deleted",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            refreshMenu();
                        } else {
                            JOptionPane.showMessageDialog(
                                    RestaurantMenuPanel.this,
                                    "Failed to delete menu item.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                RestaurantMenuPanel.this,
                                "Error: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            };
            worker.execute();
        }
    }


    // =========================
    // REFRESH
    // =========================

    public void refreshMenu() {

        loadMenuItems();
    }
}