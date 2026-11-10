package com.dineease.view.admin.user;

import com.dineease.controller.UserController;
import com.dineease.model.User;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class AddUserPanel extends JPanel {

    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JComboBox<String> roleComboBox;
    private JComboBox<String> statusComboBox;

    private UserController userController;


    public AddUserPanel() {

        userController =
                new UserController();


        setLayout(
                new BorderLayout()
        );


        setBackground(
                UITheme.BACKGROUND
        );


        setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );


        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "Add New User"
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


        add(
                title,
                BorderLayout.NORTH
        );


        // =========================
        // FORM CARD
        // =========================

        JPanel formCard =
                new JPanel();


        formCard.setLayout(
                new GridBagLayout()
        );


        formCard.setBackground(
                UITheme.CARD
        );


        formCard.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                UITheme.BORDER
                        ),

                        BorderFactory.createEmptyBorder(
                                30,
                                40,
                                30,
                                40
                        )

                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;


        gbc.gridy = 0;


        // Full Name

        addLabel(
                formCard,
                gbc,
                "Full Name:"
        );


        fullNameField =
                new JTextField();


        addField(
                formCard,
                gbc,
                fullNameField
        );


        // Username

        addLabel(
                formCard,
                gbc,
                "Username:"
        );


        usernameField =
                new JTextField();


        addField(
                formCard,
                gbc,
                usernameField
        );


        // Password

        addLabel(
                formCard,
                gbc,
                "Password:"
        );


        passwordField =
                new JPasswordField();


        addField(
                formCard,
                gbc,
                passwordField
        );


        // Role

        addLabel(
                formCard,
                gbc,
                "Role:"
        );


        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Admin",
                                "Manager",
                                "Cashier",
                                "Chef"
                        }
                );


        addField(
                formCard,
                gbc,
                roleComboBox
        );


        // Status

        addLabel(
                formCard,
                gbc,
                "Status:"
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Inactive"
                        }
                );


        addField(
                formCard,
                gbc,
                statusComboBox
        );


        add(
                formCard,
                BorderLayout.CENTER
        );


        // =========================
        // BUTTON
        // =========================

        JButton addButton =
                new JButton(
                        "Add User"
                );


        UITheme.stylePrimaryButton(
                addButton
        );


        addButton.addActionListener(
                e -> addUser()
        );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        buttonPanel.add(
                addButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

    }


    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text
    ) {

        gbc.gridx = 0;

        gbc.gridy++;


        JLabel label =
                new JLabel(text);


        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        panel.add(
                label,
                gbc
        );

    }


    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            JComponent field
    ) {

        gbc.gridx = 1;

        panel.add(
                field,
                gbc
        );

        gbc.gridx = 0;

    }



    // =========================
    // ADD USER LOGIC
    // =========================

    private void addUser() {

        String fullName =
                fullNameField.getText().trim();


        String username =
                usernameField.getText().trim();


        String password =
                new String(
                        passwordField.getPassword()
                );


        String role =
                (String) roleComboBox.getSelectedItem();


        String status =
                (String) statusComboBox.getSelectedItem();



        if(fullName.isEmpty()
                || username.isEmpty()
                || password.isEmpty()) {


            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );


            return;

        }



        User user =
                new User(
                        fullName,
                        username,
                        password,
                        role,
                        status
                );



        boolean success =
                userController.addUser(user);



        if(success) {


            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully!"
            );


            clearFields();


        }
        else {


            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add user."
            );


        }

    }



    private void clearFields() {

        fullNameField.setText("");

        usernameField.setText("");

        passwordField.setText("");

        roleComboBox.setSelectedIndex(0);

        statusComboBox.setSelectedIndex(0);

    }

}