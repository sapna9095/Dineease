package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateMenuItemPanel extends JPanel {

    private JComboBox<String> itemComboBox;
    private JTextField itemNameField;
    private JComboBox<String> categoryComboBox;
    private JTextField priceField;
    private JTextField descriptionField;
    private JComboBox<String> statusComboBox;

    private MenuController menuController;

    private List<MenuItem> menuItems;


    public UpdateMenuItemPanel() {

        menuController = new MenuController();

        setLayout(new BorderLayout(20, 20));

        setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );


        JLabel title =
                new JLabel("Update Menu Item");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(
                title,
                BorderLayout.NORTH
        );


        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                15,
                                15
                        )
                );


        // Select Item

        formPanel.add(
                new JLabel("Select Item:")
        );

        itemComboBox =
                new JComboBox<>();

        formPanel.add(
                itemComboBox
        );


        // Item Name

        formPanel.add(
                new JLabel("Item Name:")
        );

        itemNameField =
                new JTextField();

        formPanel.add(
                itemNameField
        );


        // Category

        formPanel.add(
                new JLabel("Category:")
        );

        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "Starter",
                                "Main Course",
                                "Beverage",
                                "Dessert",
                                "Salad",
                                "Soup"
                        }
                );

        formPanel.add(
                categoryComboBox
        );


        // Price

        formPanel.add(
                new JLabel("Price:")
        );

        priceField =
                new JTextField();

        formPanel.add(
                priceField
        );


        // Description

        formPanel.add(
                new JLabel("Description:")
        );

        descriptionField =
                new JTextField();

        formPanel.add(
                descriptionField
        );


        // Status

        formPanel.add(
                new JLabel("Status:")
        );

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Unavailable"
                        }
                );

        formPanel.add(
                statusComboBox
        );


        add(
                formPanel,
                BorderLayout.CENTER
        );


        JButton updateButton =
                new JButton(
                        "Update Menu Item"
                );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        buttonPanel.add(
                updateButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        loadMenuItems();


        itemComboBox.addActionListener(
                e -> loadSelectedItem()
        );


        updateButton.addActionListener(
                e -> updateMenuItem()
        );

    }


    // =========================
    // LOAD MENU ITEMS
    // =========================

    private void loadMenuItems() {

        menuItems =
                menuController
                        .getAllMenuItems();


        itemComboBox.removeAllItems();


        for (MenuItem item : menuItems) {

            itemComboBox.addItem(
                    item.getItemId()
                            + " - "
                            + item.getItemName()
            );

        }


        if (!menuItems.isEmpty()) {

            loadSelectedItem();

        }

    }


    // =========================
    // LOAD SELECTED ITEM
    // =========================

    private void loadSelectedItem() {

        int index =
                itemComboBox
                        .getSelectedIndex();


        if (
                index < 0
                        ||
                        index >= menuItems.size()
        ) {

            return;

        }


        MenuItem item =
                menuItems.get(index);


        itemNameField.setText(
                item.getItemName()
        );


        categoryComboBox.setSelectedItem(
                item.getCategory()
        );


        priceField.setText(
                String.valueOf(
                        item.getPrice()
                )
        );


        descriptionField.setText(
                item.getDescription()
        );


        statusComboBox.setSelectedItem(
                item.getStatus()
        );

    }


    // =========================
    // UPDATE ITEM
    // =========================

    private void updateMenuItem() {

        int index =
                itemComboBox
                        .getSelectedIndex();


        if (
                index < 0
                        ||
                        index >= menuItems.size()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a menu item."
            );

            return;

        }


        String itemName =
                itemNameField
                        .getText()
                        .trim();


        String priceText =
                priceField
                        .getText()
                        .trim();


        if (
                itemName.isEmpty()
                        ||
                        priceText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Item Name and Price are required."
            );

            return;

        }


        double price;


        try {

            price =
                    Double.parseDouble(
                            priceText
                    );

        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price."
            );

            return;

        }


        if (price < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price cannot be negative."
            );

            return;

        }


        MenuItem item =
                menuItems.get(index);


        item.setItemName(
                itemName
        );


        item.setCategory(
                (String)
                        categoryComboBox
                                .getSelectedItem()
        );


        item.setPrice(
                price
        );


        item.setDescription(
                descriptionField
                        .getText()
                        .trim()
        );


        item.setStatus(
                (String)
                        statusComboBox
                                .getSelectedItem()
        );


        boolean success =
                menuController
                        .updateMenuItem(item);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Menu item updated successfully!"
            );


            loadMenuItems();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update menu item."
            );

        }

    }

}