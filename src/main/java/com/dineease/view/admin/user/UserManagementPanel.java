package com.dineease.view.admin.user;

import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class UserManagementPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton viewUsersButton;
    private JButton addUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;


    public UserManagementPanel() {

        setLayout(new BorderLayout());

        setBackground(UITheme.BACKGROUND);


        // =========================
        // TOP HEADER
        // =========================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                UITheme.BACKGROUND
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        10,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "User Management"
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
                        "Manage restaurant system users"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                UITheme.MUTED_TEXT
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                UITheme.BACKGROUND
        );


        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(4)
        );

        titlePanel.add(subtitle);


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        25,
                        10,
                        25
                )
        );


        viewUsersButton =
                new JButton(
                        "View Users"
                );


        addUserButton =
                new JButton(
                        "Add User"
                );


        updateUserButton =
                new JButton(
                        "Update User"
                );


        deleteUserButton =
                new JButton(
                        "Delete User"
                );


        // =========================
        // STYLE BUTTONS
        // =========================

        UITheme.styleSecondaryButton(
                viewUsersButton
        );

        UITheme.stylePrimaryButton(
                addUserButton
        );

        UITheme.styleSecondaryButton(
                updateUserButton
        );

        UITheme.styleSecondaryButton(
                deleteUserButton
        );


        buttonPanel.add(
                viewUsersButton
        );

        buttonPanel.add(
                addUserButton
        );

        buttonPanel.add(
                updateUserButton
        );

        buttonPanel.add(
                deleteUserButton
        );


        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setBackground(
                UITheme.BACKGROUND
        );

        topPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =========================
        // CONTENT AREA
        // =========================

        cardLayout =
                new CardLayout();


        contentPanel =
                new JPanel(
                        cardLayout
                );


        contentPanel.setBackground(
                UITheme.BACKGROUND
        );


        contentPanel.add(
                new ViewUsersPanel(),
                "VIEW"
        );


        contentPanel.add(
                new AddUserPanel(),
                "ADD"
        );


        contentPanel.add(
                new UpdateUserPanel(),
                "UPDATE"
        );


        contentPanel.add(
                new DeleteUserPanel(),
                "DELETE"
        );


        add(
                contentPanel,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON ACTIONS
        // =========================

        viewUsersButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "VIEW"
                )
        );


        addUserButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "ADD"
                )
        );


        updateUserButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "UPDATE"
                )
        );


        deleteUserButton.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "DELETE"
                )
        );


        // Show View Users initially

        cardLayout.show(
                contentPanel,
                "VIEW"
        );

    }

}
