package com.dineease.view.admin.user;

import com.dineease.controller.UserController;
import com.dineease.model.User;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateUserPanel extends JPanel {

    private JComboBox<String> userComboBox;
    private JTextField fullNameField;
    private JComboBox<String> roleComboBox;
    private JComboBox<String> statusComboBox;

    private UserController userController;

    private List<User> users;


    public UpdateUserPanel() {

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


        // TITLE

        JLabel title =
                new JLabel(
                        "Update User"
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



        // FORM PANEL

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );


        formPanel.setBackground(
                UITheme.CARD
        );


        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
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


        gbc.gridy = 0;



        addLabel(
                formPanel,
                gbc,
                "Select User:"
        );


        userComboBox =
                new JComboBox<>();


        addField(
                formPanel,
                gbc,
                userComboBox
        );



        addLabel(
                formPanel,
                gbc,
                "Full Name:"
        );


        fullNameField =
                new JTextField();


        addField(
                formPanel,
                gbc,
                fullNameField
        );



        addLabel(
                formPanel,
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
                formPanel,
                gbc,
                roleComboBox
        );



        addLabel(
                formPanel,
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
                formPanel,
                gbc,
                statusComboBox
        );



        JPanel centerPanel =
                new JPanel(
                        new GridBagLayout()
                );


        centerPanel.setBackground(
                UITheme.BACKGROUND
        );


        centerPanel.add(
                formPanel
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );



        // BUTTON

        JButton updateButton =
                new JButton(
                        "Update User"
                );


        UITheme.stylePrimaryButton(
                updateButton
        );


        updateButton.addActionListener(
                e -> updateUser()
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
                updateButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );



        // LOAD DATA

        loadUsers();


        userComboBox.addActionListener(
                e -> loadSelectedUser()
        );

    }



    // =========================
    // HELPER METHODS
    // =========================


    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text
    ){

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
    ){

        gbc.gridx = 1;


        panel.add(
                field,
                gbc
        );


        gbc.gridx = 0;

    }




    // =========================
    // LOAD USERS
    // =========================


    private void loadUsers(){

        users =
                userController.getAllUsers();


        userComboBox.removeAllItems();


        if(users == null || users.isEmpty()){

            return;

        }


        for(User user : users){


            userComboBox.addItem(
                    user.getUserId()
                            + " - "
                            + user.getFullName()
            );

        }



        if(!users.isEmpty()){

            loadSelectedUser();

        }

    }




    // =========================
    // LOAD SELECTED USER
    // =========================


    private void loadSelectedUser(){


        int index =
                userComboBox.getSelectedIndex();



        if(users == null ||
                index < 0 ||
                index >= users.size()){

            return;

        }



        User user =
                users.get(index);



        fullNameField.setText(
                user.getFullName()
        );



        roleComboBox.setSelectedItem(
                user.getRole()
        );



        statusComboBox.setSelectedItem(
                user.getStatus()
        );


    }




    // =========================
    // UPDATE USER
    // =========================


    private void updateUser(){


        int index =
                userComboBox.getSelectedIndex();



        if(index < 0){


            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user."
            );


            return;

        }



        String fullName =
                fullNameField.getText()
                        .trim();



        if(fullName.isEmpty()){


            JOptionPane.showMessageDialog(
                    this,
                    "Full Name cannot be empty."
            );


            return;

        }



        User user =
                users.get(index);



        user.setFullName(
                fullName
        );



        user.setRole(
                (String)
                        roleComboBox.getSelectedItem()
        );



        user.setStatus(
                (String)
                        statusComboBox.getSelectedItem()
        );



        boolean success =
                userController.updateUser(user);



        if(success){

            JOptionPane.showMessageDialog(
                    this,
                    "User updated successfully!"
            );


            loadUsers();

            userComboBox.setSelectedIndex(0);

        }
        else{


            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update user."
            );


        }

    }

}