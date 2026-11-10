package com.dineease.view;


import com.dineease.util.UITheme;
import com.dineease.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private JButton loginButton;
    private JButton clearButton;

    public LoginFrame() {

        setTitle("DineEase AI - Login");

        setSize(500, 600);

        setMinimumSize(
                new Dimension(450, 550)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setResizable(false);

        createUI();

        setVisible(true);
    }


    // =========================
    // CREATE UI
    // =========================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                UITheme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );


        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                UITheme.BACKGROUND
        );


        JLabel logo =
                new JLabel(
                        "DineEase"
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logo.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        36
                )
        );

        logo.setForeground(
                UITheme.PRIMARY
        );


        JLabel aiLabel =
                new JLabel(
                        "AI"
                );

        aiLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        aiLabel.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        20
                )
        );

        aiLabel.setForeground(
                UITheme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "RESTAURANT MANAGEMENT SYSTEM"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        subtitle.setForeground(
                Color.GRAY
        );


        header.add(logo);

        header.add(
                Box.createVerticalStrut(2)
        );

        header.add(aiLabel);

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(subtitle);


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =========================
        // LOGIN CARD
        // =========================

        JPanel card =
                new JPanel();

        card.setLayout(
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
                                30,
                                35,
                                30,
                                35
                        )
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        5,
                        8,
                        5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;


        // TITLE

        JLabel loginTitle =
                new JLabel(
                        "Login"
                );

        loginTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        loginTitle.setForeground(
                UITheme.TEXT
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        card.add(
                loginTitle,
                gbc
        );


        // USERNAME

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        card.add(
                new JLabel("Username:"),
                gbc
        );


        usernameField =
                new JTextField();

        usernameField.setPreferredSize(
                new Dimension(
                        220,
                        35
                )
        );

        gbc.gridx = 1;

        card.add(
                usernameField,
                gbc
        );


        // PASSWORD

        gbc.gridx = 0;
        gbc.gridy = 2;

        card.add(
                new JLabel("Password:"),
                gbc
        );


        passwordField =
                new JPasswordField();

        passwordField.setPreferredSize(
                new Dimension(
                        220,
                        35
                )
        );

        gbc.gridx = 1;

        card.add(
                passwordField,
                gbc
        );


        // ROLE

        gbc.gridx = 0;
        gbc.gridy = 3;

        card.add(
                new JLabel("Role:"),
                gbc
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

        gbc.gridx = 1;

        card.add(
                roleComboBox,
                gbc
        );


        // BUTTONS

        loginButton =
                new JButton(
                        "Login"
                );

        clearButton =
                new JButton(
                        "Clear"
                );


        UITheme.stylePrimaryButton(
                loginButton
        );

        UITheme.styleSecondaryButton(
                clearButton
        );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        buttonPanel.setBackground(
                UITheme.CARD
        );

        buttonPanel.add(
                loginButton
        );

        buttonPanel.add(
                clearButton
        );


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        card.add(
                buttonPanel,
                gbc
        );


        mainPanel.add(
                card,
                BorderLayout.CENTER
        );


        // =========================
        // FOOTER
        // =========================

        JLabel footer =
                new JLabel(
                        "© 2026 DineEase AI",
                        SwingConstants.CENTER
                );

        footer.setForeground(
                Color.GRAY
        );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );


        mainPanel.add(
                footer,
                BorderLayout.SOUTH
        );


        add(mainPanel);


        // =========================
        // ACTIONS
        // =========================

        loginButton.addActionListener(
                e -> login()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        passwordField.addActionListener(
                e -> login()
        );
    }


    // =========================
    // LOGIN
    // =========================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String role =
                roleComboBox
                        .getSelectedItem()
                        .toString();


        if (
                username.isEmpty()
                        ||
                        password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String sql =
                "SELECT full_name, role, status " +
                        "FROM users " +
                        "WHERE username=? " +
                        "AND password=? " +
                        "AND role=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    username
            );

            ps.setString(
                    2,
                    password
            );

            ps.setString(
                    3,
                    role
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    String status =
                            rs.getString("status");


                    if (
                            !"Active"
                                    .equalsIgnoreCase(
                                            status
                                    )
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Your account is inactive.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }


                    openDashboard(
                            role
                    );

                }
                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username, password or role.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        }
        catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection error:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // OPEN DASHBOARD
    // =========================

    private void openDashboard(
            String role
    ) {

        if (
                "Admin".equalsIgnoreCase(role)
        ) {

            new com.dineease.view.admin.AdminDashboard();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    role + " dashboard is not implemented yet."
            );

            return;
        }


        dispose();
    }


    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        usernameField.setText("");

        passwordField.setText("");

        roleComboBox.setSelectedIndex(0);

        usernameField.requestFocus();
    }


    // =========================
    // MAIN
    // =========================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                LoginFrame::new
        );
    }
}
