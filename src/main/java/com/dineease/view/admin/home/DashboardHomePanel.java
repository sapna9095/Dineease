package com.dineease.view.admin.home;

import com.dineease.util.DBConnection;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardHomePanel extends JPanel {

    private JLabel usersValue;
    private JLabel menuValue;
    private JLabel inventoryValue;
    private JLabel lowStockValue;


    public DashboardHomePanel() {

        setLayout(new BorderLayout(20, 20));

        setBackground(UITheme.BACKGROUND);

        setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );


        // =========================
        // HEADER
        // =========================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Welcome to DineEase AI"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                UITheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Restaurant Management Dashboard"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        subtitle.setForeground(
                UITheme.MUTED_TEXT
        );


        headerPanel.add(title);

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

        headerPanel.add(subtitle);


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // STATISTICS CARDS
        // =========================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                20,
                                20
                        )
                );

        cardsPanel.setBackground(
                UITheme.BACKGROUND
        );


        usersValue =
                createCard(
                        cardsPanel,
                        "👥",
                        "Total Users"
                );


        menuValue =
                createCard(
                        cardsPanel,
                        "🍽",
                        "Menu Items"
                );


        inventoryValue =
                createCard(
                        cardsPanel,
                        "📦",
                        "Inventory Items"
                );


        lowStockValue =
                createCard(
                        cardsPanel,
                        "⚠",
                        "Low Stock Items"
                );


        add(
                cardsPanel,
                BorderLayout.CENTER
        );


        // =========================
        // REFRESH BUTTON
        // =========================

        JButton refreshButton =
                new JButton(
                        "Refresh Dashboard"
                );


        UITheme.stylePrimaryButton(
                refreshButton
        );


        refreshButton.addActionListener(
                e -> loadDashboardData()
        );


        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setBackground(
                UITheme.BACKGROUND
        );


        bottomPanel.add(
                refreshButton
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // Load data

        loadDashboardData();

    }


    // =========================
    // CREATE CARD
    // =========================

    private JLabel createCard(
            JPanel parent,
            String icon,
            String title
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
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
                                20,
                                20,
                                20,
                                20
                        )

                )
        );


        JLabel iconLabel =
                new JLabel(icon);


        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        28
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );


        titleLabel.setForeground(
                UITheme.MUTED_TEXT
        );


        JLabel valueLabel =
                new JLabel("0");


        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );


        valueLabel.setForeground(
                UITheme.PRIMARY
        );


        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        topPanel.setBackground(
                UITheme.CARD
        );


        topPanel.add(
                iconLabel,
                BorderLayout.WEST
        );


        topPanel.add(
                titleLabel,
                BorderLayout.CENTER
        );


        card.add(
                topPanel,
                BorderLayout.NORTH
        );


        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        parent.add(card);


        return valueLabel;

    }


    // =========================
    // LOAD DASHBOARD DATA
    // =========================

    private void loadDashboardData() {

        usersValue.setText(
                String.valueOf(
                        getCount(
                                "SELECT COUNT(*) FROM users"
                        )
                )
        );


        menuValue.setText(
                String.valueOf(
                        getCount(
                                "SELECT COUNT(*) FROM menu_items"
                        )
                )
        );


        inventoryValue.setText(
                String.valueOf(
                        getCount(
                                "SELECT COUNT(*) FROM inventory"
                        )
                )
        );


        lowStockValue.setText(
                String.valueOf(
                        getCount(
                                "SELECT COUNT(*) FROM inventory " +
                                        "WHERE status='Low Stock'"
                        )
                )
        );

    }


    // =========================
    // DATABASE COUNT
    // =========================

    private int getCount(
            String sql
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                return rs.getInt(1);

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return 0;

    }

}