package com.dineease.view.admin.user;

import com.dineease.controller.UserController;
import com.dineease.model.User;
import com.dineease.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewUsersPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserController userController;


    public ViewUsersPanel() {

        userController =
                new UserController();


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


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "All Users"
                );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
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
        // TABLE
        // =========================

        String[] columns = {

                "User ID",
                "Full Name",
                "Username",
                "Role",
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


        userTable =
                new JTable(
                        tableModel
                );


        // =========================
        // TABLE STYLE
        // =========================

        userTable.setRowHeight(
                36
        );


        userTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        userTable.setShowGrid(
                true
        );


        userTable.setGridColor(
                UITheme.BORDER
        );


        userTable.setBackground(
                UITheme.CARD
        );


        userTable.setForeground(
                UITheme.TEXT
        );


        // =========================
        // TABLE HEADER
        // =========================

        userTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );


        userTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );


        userTable.getTableHeader()
                .setBackground(
                        UITheme.PRIMARY
                );


        userTable.getTableHeader()
                .setForeground(
                        Color.WHITE
                );


        // =========================
        // CENTER ALIGN USER ID
        // =========================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();


        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        userTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        // =========================
        // SCROLL PANE
        // =========================

        JScrollPane scrollPane =
                new JScrollPane(
                        userTable
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
        // REFRESH BUTTON
        // =========================

        JButton refreshButton =
                new JButton(
                        "↻  Refresh"
                );


        UITheme.stylePrimaryButton(
                refreshButton
        );


        refreshButton.addActionListener(
                e -> loadUsers()
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


        // =========================
        // LOAD USERS
        // =========================

        loadUsers();

    }


    // =========================
    // LOAD USERS FROM DATABASE
    // =========================

    private void loadUsers() {

        tableModel.setRowCount(
                0
        );


        List<User> users =
                userController.getAllUsers();


        for (
                User user :
                users
        ) {

            Object[] row = {

                    user.getUserId(),

                    user.getFullName(),

                    user.getUsername(),

                    user.getRole(),

                    user.getStatus()

            };


            tableModel.addRow(
                    row
            );

        }

    }

}