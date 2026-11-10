package com.dineease.view.admin.user;


import com.dineease.controller.UserController;
import com.dineease.model.User;
import com.dineease.util.UITheme;


import javax.swing.*;
import java.awt.*;
import java.util.List;


public class DeleteUserPanel extends JPanel {


    private JComboBox<String> userComboBox;

    private JButton deleteButton;

    private UserController userController;

    private List<User> users;



    public DeleteUserPanel() {


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
                        "Delete User"
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
        // CARD PANEL
        // =========================


        JPanel card =
                new JPanel(
                        new GridBagLayout()
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
                                40,
                                50,
                                40,
                                50
                        )

                )
        );



        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        15,
                        15,
                        15,
                        15
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;



        // =========================
        // SELECT USER LABEL
        // =========================


        JLabel selectLabel =
                new JLabel(
                        "Select User:"
                );


        selectLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        gbc.gridx = 0;

        gbc.gridy = 0;


        card.add(
                selectLabel,
                gbc
        );



        // =========================
        // USER COMBO BOX
        // =========================


        userComboBox =
                new JComboBox<>();


        gbc.gridx = 1;


        card.add(
                userComboBox,
                gbc
        );



        // =========================
        // DELETE BUTTON
        // =========================


        deleteButton =
                new JButton(
                        "Delete User"
                );



        UITheme.styleDangerButton(
                deleteButton
        );



        gbc.gridx = 1;

        gbc.gridy = 1;


        card.add(
                deleteButton,
                gbc
        );



        add(
                card,
                BorderLayout.CENTER
        );



        // =========================
        // LOAD USERS
        // =========================


        loadUsers();



        // =========================
        // BUTTON ACTION
        // =========================


        deleteButton.addActionListener(
                e -> deleteUser()
        );


    }




    // =========================
    // LOAD USERS
    // =========================


    private void loadUsers(){


        users =
                userController.getAllUsers();



        userComboBox.removeAllItems();



        if(users == null || users.isEmpty()){


            userComboBox.addItem(
                    "No users available"
            );


            deleteButton.setEnabled(
                    false
            );


            return;

        }



        deleteButton.setEnabled(
                true
        );



        for(User user : users){


            userComboBox.addItem(

                    user.getUserId()
                            + " - "
                            + user.getFullName()
                            + " ("
                            + user.getUsername()
                            + ")"

            );

        }


    }




    // =========================
    // DELETE USER
    // =========================


    private void deleteUser(){



        int index =
                userComboBox.getSelectedIndex();



        if(users == null ||
                users.isEmpty() ||
                index < 0 ||
                index >= users.size()){



            JOptionPane.showMessageDialog(
                    this,
                    "Please select a valid user."
            );


            return;

        }




        User user =
                users.get(index);





        int confirmation =
                JOptionPane.showConfirmDialog(

                        this,

                        "Are you sure you want to delete "
                                + user.getFullName()
                                + "?",

                        "Confirm Delete",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE

                );




        if(confirmation != JOptionPane.YES_OPTION){

            return;

        }




        boolean success =
                userController.deleteUser(
                        user.getUserId()
                );




        if(success){


            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully!"
            );



            loadUsers();



        }
        else{


            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete user."
            );


        }


    }


}