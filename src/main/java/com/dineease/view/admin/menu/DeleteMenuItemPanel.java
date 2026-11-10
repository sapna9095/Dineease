package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.dineease.util.UITheme;

public class DeleteMenuItemPanel extends JPanel {

    private JComboBox<String> itemComboBox;
    private JButton deleteButton;

    private MenuController menuController;

    private List<MenuItem> menuItems;


    public DeleteMenuItemPanel() {

        menuController = new MenuController();

        setLayout(new BorderLayout(20, 20));

        setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );


        JLabel title =
                new JLabel("Delete Menu Item");

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


        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                15,
                                15
                        )
                );


        centerPanel.add(
                new JLabel("Select Item:")
        );


        itemComboBox =
                new JComboBox<>();


        centerPanel.add(
                itemComboBox
        );


        centerPanel.add(
                new JLabel("Action:")
        );


        deleteButton =
                new JButton(
                        "Delete Item"
                );

        UITheme.styleDangerButton(
                deleteButton
        );


        centerPanel.add(
                deleteButton
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );


        loadMenuItems();


        deleteButton.addActionListener(
                e -> deleteMenuItem()
        );

    }


    // =========================
    // LOAD ITEMS
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

    }


    // =========================
    // DELETE ITEM
    // =========================

    private void deleteMenuItem() {

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


        MenuItem item =
                menuItems.get(index);


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + item.getItemName()
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;

        }


        boolean success =
                menuController
                        .deleteMenuItem(
                                item.getItemId()
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Menu item deleted successfully!"
            );


            loadMenuItems();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete menu item."
            );

        }

    }

}