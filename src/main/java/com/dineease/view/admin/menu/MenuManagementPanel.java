package com.dineease.view.admin.menu;

import javax.swing.*;
import java.awt.*;

public class MenuManagementPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    // =========================
    // CONSTRUCTOR
    // =========================

    public MenuManagementPanel() {

        setLayout(
                new BorderLayout()
        );


        // =========================
        // TOP BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );


        JButton viewMenuButton =
                new JButton(
                        "View Menu"
                );


        JButton addMenuButton =
                new JButton(
                        "Add Item"
                );


        JButton updateMenuButton =
                new JButton(
                        "Update Item"
                );


        JButton deleteMenuButton =
                new JButton(
                        "Delete Item"
                );


        buttonPanel.add(
                viewMenuButton
        );

        buttonPanel.add(
                addMenuButton
        );

        buttonPanel.add(
                updateMenuButton
        );

        buttonPanel.add(
                deleteMenuButton
        );


        add(
                buttonPanel,
                BorderLayout.NORTH
        );


        // =========================
        // CARD LAYOUT
        // =========================

        cardLayout =
                new CardLayout();


        contentPanel =
                new JPanel(
                        cardLayout
                );


        // =========================
        // VISUAL RESTAURANT MENU
        // =========================

        RestaurantMenuPanel restaurantMenuPanel =
                new RestaurantMenuPanel();


        contentPanel.add(
                restaurantMenuPanel,
                "VIEW"
        );


        // =========================
        // ADD ITEM
        // =========================

        AddMenuItemPanel addPanel =
                new AddMenuItemPanel();


        contentPanel.add(
                addPanel,
                "ADD"
        );


        // =========================
        // UPDATE ITEM
        // =========================

        UpdateMenuItemPanel updatePanel =
                new UpdateMenuItemPanel();


        contentPanel.add(
                updatePanel,
                "UPDATE"
        );


        // =========================
        // DELETE ITEM
        // =========================

        DeleteMenuItemPanel deletePanel =
                new DeleteMenuItemPanel();


        contentPanel.add(
                deletePanel,
                "DELETE"
        );


        add(
                contentPanel,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        viewMenuButton.addActionListener(
                e -> {

                    restaurantMenuPanel.refreshMenu();

                    cardLayout.show(
                            contentPanel,
                            "VIEW"
                    );
                }
        );


        addMenuButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "ADD"
                )
        );


        updateMenuButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "UPDATE"
                )
        );


        deleteMenuButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "DELETE"
                )
        );


        // =========================
        // SHOW MENU BY DEFAULT
        // =========================

        cardLayout.show(
                contentPanel,
                "VIEW"
        );
    }
}