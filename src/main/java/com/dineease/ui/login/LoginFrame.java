package com.dineease.ui.login;

import com.formdev.flatlaf.FlatLightLaf;
import com.dineease.ui.theme.AppColors;
import com.dineease.ui.theme.AppDimensions;
import com.dineease.ui.components.ShadowPanel;
import com.dineease.ui.theme.AppFonts;
import com.dineease.ui.components.RoundedTextField;
import com.dineease.ui.components.RoundedPasswordField;
import com.dineease.ui.components.RoundedButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private ShadowPanel loginCard;

    private JLabel welcomeLabel;
    private JLabel subtitleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JCheckBox rememberMeCheckBox;

    private JCheckBox showPasswordCheckBox;

    private RoundedButton loginButton;

    private JLabel forgotPasswordLabel;

    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;

    public LoginFrame() {

        initializeFrame();
        createMainPanel();
        createLeftPanel();
        createRightPanel();
        addPanels();
        registerEvents();

    }

    private void initializeFrame() {

        setTitle("DineEase - Restaurant Management System");
        setSize(
                AppDimensions.WINDOW_WIDTH,
                AppDimensions.WINDOW_HEIGHT
        );

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void createMainPanel() {

        mainPanel = new JPanel(new BorderLayout());

    }

    private void createLeftPanel() {

        leftPanel = new JPanel();

        leftPanel.setPreferredSize(new Dimension(430, 700));

        leftPanel.setBackground(AppColors.PRIMARY_GREEN);

    }

    private void createRightPanel() {

        rightPanel = new JPanel(new GridBagLayout());

        rightPanel.setBackground(AppColors.BACKGROUND);

        createLoginCard();

    }
    private void createLoginCard() {

        loginCard = new ShadowPanel();

        loginCard.setPreferredSize(new Dimension(430, 520));

        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));

        loginCard.setBackground(Color.WHITE);

        loginCard.add(Box.createVerticalStrut(35));
        // Username Label
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(AppFonts.LABEL);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(usernameLabel);

        loginCard.add(Box.createVerticalStrut(8));

// Username Field
        usernameField = new RoundedTextField(20);
        usernameField.setMaximumSize(new Dimension(320,45));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(usernameField);

        loginCard.add(Box.createVerticalStrut(20));

// Password Label
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(AppFonts.LABEL);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(passwordLabel);

        loginCard.add(Box.createVerticalStrut(8));

// Password Field
        passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(320,45));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(passwordField);

        loginCard.add(Box.createVerticalStrut(30));
        // Remember Me
        rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setFont(AppFonts.SUBTITLE);
        rememberMeCheckBox.setBackground(Color.WHITE);
        rememberMeCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(rememberMeCheckBox);

        loginCard.add(Box.createVerticalStrut(15));

// Show Password
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.setFont(AppFonts.SUBTITLE);
        showPasswordCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(showPasswordCheckBox);

        loginCard.add(Box.createVerticalStrut(25));

// Login Button
        loginButton = new RoundedButton("LOGIN");
        loginButton.setMaximumSize(new Dimension(250,45));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(loginButton);

        loginCard.add(Box.createVerticalStrut(20));

// Forgot Password
        forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setForeground(AppColors.PRIMARY_GREEN);
        forgotPasswordLabel.setFont(AppFonts.SUBTITLE);
        forgotPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(forgotPasswordLabel);

        welcomeLabel = new JLabel("Welcome Back!");

        welcomeLabel.setFont(AppFonts.TITLE);

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(welcomeLabel);

        loginCard.add(Box.createVerticalStrut(10));

        subtitleLabel = new JLabel("Sign in to continue to DineEase");

        subtitleLabel.setFont(AppFonts.SUBTITLE);

        subtitleLabel.setForeground(AppColors.TEXT_SECONDARY);

        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(subtitleLabel);

        loginCard.add(Box.createVerticalStrut(35));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;

        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.CENTER;

        rightPanel.add(loginCard, gbc);

    }

    private void registerEvents() {

        showPasswordCheckBox.addActionListener(e -> {

            if(showPasswordCheckBox.isSelected()){

                passwordField.setEchoChar((char)0);

            }else{

                passwordField.setEchoChar('•');

            }

        });

        // Wire up the LOGIN button
        loginButton.addActionListener(e -> handleLogin());

    }

    private void handleLogin() {

        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username is required.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Password is required.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Delegate to DBAuthService for real authentication
        try {
            com.dineease.service.AuthService authService = new com.dineease.service.DBAuthService();
            com.dineease.service.UserSession session = authService.login(username, password);

            JOptionPane.showMessageDialog(this,
                    "Welcome, " + session.getUsername() + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
            SwingUtilities.invokeLater(() -> new com.dineease.ui.MainFrame(session));

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void addPanels() {

        mainPanel.add(leftPanel, BorderLayout.WEST);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (Exception e) {

            e.printStackTrace();

        }

        SwingUtilities.invokeLater(() -> {

            LoginFrame frame = new LoginFrame();

            frame.setVisible(true);

        });

    }

}