package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class ViewMenuPanel extends JPanel {

    private MenuController menuController;

    private JPanel categoryPanel;
    private JPanel menuGridPanel;

    private String selectedCategory = "All";

    private final String[] categories = {
            "All",
            "Starters",
            "Main Course",
            "Beverages",
            "Desserts",
            "Salads",
            "Soups"
    };


    // =========================
    // CONSTRUCTOR
    // =========================

    public ViewMenuPanel() {

        menuController = new MenuController();

        setLayout(new BorderLayout());

        setBackground(UITheme.BACKGROUND);

        createHeader();

        createCategoryNavigation();

        createMenuGrid();

        loadMenuItems();
    }


    // =========================
    // HEADER
    // =========================

    private void createHeader() {

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        10,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Our Menu"
                );

        title.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                UITheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Freshly prepared • Delicious • Made with love"
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


        JPanel textPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        textPanel.setOpaque(false);

        textPanel.add(title);
        textPanel.add(subtitle);


        headerPanel.add(
                textPanel,
                BorderLayout.WEST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );
    }


    // =========================
    // CATEGORY NAVIGATION
    // =========================

    private void createCategoryNavigation() {

        categoryPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                5
                        )
                );

        categoryPanel.setBackground(
                UITheme.BACKGROUND
        );

        categoryPanel.setBorder(
                new EmptyBorder(
                        5,
                        25,
                        15,
                        25
                )
        );


        for (String category : categories) {

            JButton categoryButton =
                    createCategoryButton(
                            category
                    );

            categoryPanel.add(
                    categoryButton
            );
        }


        JPanel wrapper =
                new JPanel(
                        new BorderLayout()
                );

        wrapper.setBackground(
                UITheme.BACKGROUND
        );

        wrapper.add(
                categoryPanel,
                BorderLayout.CENTER
        );


        add(
                wrapper,
                BorderLayout.PAGE_START
        );
    }


    // =========================
    // CATEGORY BUTTON
    // =========================

    private JButton createCategoryButton(
            String category
    ) {

        JButton button =
                new JButton(
                        category.toUpperCase()
                );


        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );


        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setContentAreaFilled(false);

        button.setOpaque(true);


        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );


        updateCategoryButton(
                button,
                category
        );


        button.addActionListener(
                e -> {

                    selectedCategory =
                            category;

                    refreshCategoryButtons();

                    loadMenuItems();
                }
        );


        return button;
    }


    // =========================
    // CATEGORY BUTTON STYLE
    // =========================

    private void updateCategoryButton(
            JButton button,
            String category
    ) {

        if (
                category.equals(
                        selectedCategory
                )
        ) {

            button.setBackground(
                    new Color(
                            198,
                            155,
                            75
                    )
            );

            button.setForeground(
                    Color.WHITE
            );

        }
        else {

            button.setBackground(
                    UITheme.CARD
            );

            button.setForeground(
                    UITheme.TEXT
            );
        }
    }


    // =========================
    // REFRESH CATEGORY BUTTONS
    // =========================

    private void refreshCategoryButtons() {

        Component[] components =
                categoryPanel.getComponents();


        for (
                Component component :
                components
        ) {

            if (
                    component instanceof JButton
            ) {

                JButton button =
                        (JButton) component;


                String category =
                        button.getText();


                category =
                        convertCategoryName(
                                category
                        );


                updateCategoryButton(
                        button,
                        category
                );
            }
        }


        categoryPanel.repaint();
    }


    // =========================
    // CATEGORY NAME
    // =========================

    private String convertCategoryName(
            String category
    ) {

        switch (category) {

            case "ALL":
                return "All";

            case "STARTERS":
                return "Starters";

            case "MAIN COURSE":
                return "Main Course";

            case "BEVERAGES":
                return "Beverages";

            case "DESSERTS":
                return "Desserts";

            case "SALADS":
                return "Salads";

            case "SOUPS":
                return "Soups";

            default:
                return category;
        }
    }


    // =========================
    // MENU GRID
    // =========================

    private void createMenuGrid() {

        menuGridPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                3,
                                20,
                                20
                        )
                );

        menuGridPanel.setBackground(
                UITheme.BACKGROUND
        );

        menuGridPanel.setBorder(
                new EmptyBorder(
                        10,
                        25,
                        25,
                        25
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        menuGridPanel
                );


        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }


    // =========================
    // LOAD MENU ITEMS
    // =========================

    private void loadMenuItems() {

        menuGridPanel.removeAll();


        List<MenuItem> menuItems =
                menuController
                        .getAllMenuItems();


        if (
                menuItems == null
                        ||
                        menuItems.isEmpty()
        ) {

            JLabel emptyLabel =
                    new JLabel(
                            "No menu items available."
                    );

            emptyLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            16
                    )
            );

            emptyLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            menuGridPanel.add(
                    emptyLabel
            );

        }
        else {

            for (
                    MenuItem item :
                    menuItems
            ) {

                if (
                        selectedCategory.equals(
                                "All"
                        )
                                ||
                                item.getCategory()
                                        .equalsIgnoreCase(
                                                selectedCategory
                                        )
                ) {

                    menuGridPanel.add(
                            createFoodCard(
                                    item
                            )
                    );
                }
            }
        }


        menuGridPanel.revalidate();

        menuGridPanel.repaint();
    }


    // =========================
    // FOOD CARD
    // =========================

    private JPanel createFoodCard(
            MenuItem item
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );


        card.setBackground(
                UITheme.CARD
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        225,
                                        225
                                )
                        ),
                        new EmptyBorder(
                                0,
                                0,
                                15,
                                0
                        )
                )
        );


        // =========================
        // IMAGE
        // =========================

        JLabel imageLabel =
                createFoodImage(
                        item
                );


        card.add(
                imageLabel,
                BorderLayout.NORTH
        );


        // =========================
        // INFORMATION
        // =========================

        JPanel infoPanel =
                new JPanel(
                        new BorderLayout()
                );

        infoPanel.setBackground(
                UITheme.CARD
        );

        infoPanel.setBorder(
                new EmptyBorder(
                        12,
                        15,
                        0,
                        15
                )
        );


        JLabel nameLabel =
                new JLabel(
                        item.getItemName()
                );

        nameLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        20
                )
        );

        nameLabel.setForeground(
                UITheme.TEXT
        );


        JLabel categoryLabel =
                new JLabel(
                        item.getCategory()
                );

        categoryLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        categoryLabel.setForeground(
                new Color(
                        150,
                        150,
                        150
                )
        );


        JPanel namePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        namePanel.setOpaque(false);

        namePanel.add(
                nameLabel
        );

        namePanel.add(
                categoryLabel
        );


        infoPanel.add(
                namePanel,
                BorderLayout.WEST
        );


        JLabel priceLabel =
                new JLabel(
                        String.format(
                                "₹%.2f",
                                item.getPrice()
                        )
                );

        priceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        priceLabel.setForeground(
                new Color(
                        198,
                        155,
                        75
                )
        );


        infoPanel.add(
                priceLabel,
                BorderLayout.EAST
        );


        card.add(
                infoPanel,
                BorderLayout.CENTER
        );


        // =========================
        // DESCRIPTION
        // =========================

        if (
                item.getDescription() != null
                        &&
                        !item.getDescription().trim().isEmpty()
        ) {

            JLabel description =
                    new JLabel(
                            "<html>"
                                    + item.getDescription()
                                    + "</html>"
                    );

            description.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            12
                    )
            );

            description.setForeground(
                    Color.GRAY
            );

            description.setBorder(
                    new EmptyBorder(
                            8,
                            15,
                            0,
                            15
                    )
            );


            card.add(
                    description,
                    BorderLayout.SOUTH
            );
        }


        // =========================
        // HOVER EFFECT
        // =========================

        card.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                new Color(
                                                        198,
                                                        155,
                                                        75
                                                ),
                                                2
                                        ),
                                        new EmptyBorder(
                                                0,
                                                0,
                                                15,
                                                0
                                        )
                                )
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                new Color(
                                                        225,
                                                        225,
                                                        225
                                                )
                                        ),
                                        new EmptyBorder(
                                                0,
                                                0,
                                                15,
                                                0
                                        )
                                )
                        );
                    }
                }
        );


        return card;
    }


    // =========================
    // FOOD IMAGE
    // =========================

    private JLabel createFoodImage(
            MenuItem item
    ) {

        JLabel imageLabel =
                new JLabel();


        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        imageLabel.setPreferredSize(
                new Dimension(
                        0,
                        220
                )
        );


        String imagePath =
                item.getImagePath();


        if (
                imagePath != null
                        &&
                        !imagePath.trim().isEmpty()
        ) {

            File imageFile =
                    new File(
                            imagePath
                    );


            if (
                    imageFile.exists()
            ) {

                ImageIcon originalIcon =
                        new ImageIcon(
                                imagePath
                        );


                Image image =
                        originalIcon
                                .getImage()
                                .getScaledInstance(
                                        350,
                                        220,
                                        Image.SCALE_SMOOTH
                                );


                imageLabel.setIcon(
                        new ImageIcon(
                                image
                        )
                );


                return imageLabel;
            }
        }


        imageLabel.setText(
                "No Image"
        );


        imageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        imageLabel.setForeground(
                Color.GRAY
        );


        return imageLabel;
    }
}