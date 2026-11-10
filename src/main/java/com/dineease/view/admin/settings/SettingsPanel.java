package com.dineease.view.admin.settings;

import com.dineease.db.DBConnection;
import com.dineease.ui.IconFactory;
import com.dineease.ui.components.RoundedPanel;
import com.dineease.util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Properties;

public class SettingsPanel extends JPanel {

    private static final Color BG_CREAM = new Color(247, 241, 229);
    private static final Color GOLD = new Color(200, 144, 46);
    private static final Color TEXT_DARK = new Color(43, 27, 18);
    private static final Color TEXT_MUTED = new Color(110, 95, 80);

    // Restaurant profile fields
    private final JTextField restaurantNameField;
    private final JTextField taglineField;
    private final JTextField phoneField;
    private final JTextField addressField;
    private final JTextField currencySymbolField;

    // Database config fields
    private final JTextField dbHostField;
    private final JTextField dbPortField;
    private final JTextField dbNameField;
    private final JTextField dbUserField;
    private final JPasswordField dbPasswordField;
    private final JLabel dbStatusLabel;

    public SettingsPanel() {
        setBackground(BG_CREAM);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(26, 32, 26, 32));

        // =========================
        // HEADER
        // =========================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("System Settings");
        title.setFont(FontUtil.getPlayfairDisplay(28f, Font.BOLD));
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Configure restaurant profile, database connection and system preferences");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        headerPanel.add(titleBlock, BorderLayout.WEST);
        root.add(headerPanel);
        root.add(Box.createVerticalStrut(24));

        // =========================
        // TWO-COLUMN CARDS: Restaurant Profile & Database Config
        // =========================
        JPanel cardsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        cardsRow.setOpaque(false);

        // 1. Restaurant Profile Card
        RoundedPanel profileCard = new RoundedPanel(14, new Color(251, 246, 234));
        profileCard.setLayout(new BorderLayout());
        profileCard.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pHead = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pHead.setOpaque(false);
        pHead.add(new JLabel(IconFactory.chefHat(20, GOLD)));
        JLabel pTitle = new JLabel("Restaurant Information");
        pTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        pTitle.setForeground(TEXT_DARK);
        pHead.add(pTitle);
        profileCard.add(pHead, BorderLayout.NORTH);

        JPanel pForm = new JPanel(new GridBagLayout());
        pForm.setOpaque(false);
        pForm.setBorder(new EmptyBorder(16, 0, 16, 0));

        restaurantNameField = createField("DineEase AI Restaurant");
        taglineField = createField("Good Food • Good Mood • DineEase");
        phoneField = createField("+91 98765 43210");
        addressField = createField("Central Plaza, MG Road");
        currencySymbolField = createField("₹ (INR)");

        addFormRow(pForm, 0, "Restaurant Name:", restaurantNameField);
        addFormRow(pForm, 1, "Slogan / Tagline:", taglineField);
        addFormRow(pForm, 2, "Contact Phone:", phoneField);
        addFormRow(pForm, 3, "Address / Location:", addressField);
        addFormRow(pForm, 4, "Currency:", currencySymbolField);

        JButton saveProfileBtn = createPrimaryButton("Save Profile");
        saveProfileBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Restaurant profile updated successfully!",
                    "Settings",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBottom.setOpaque(false);
        pBottom.add(saveProfileBtn);

        profileCard.add(pForm, BorderLayout.CENTER);
        profileCard.add(pBottom, BorderLayout.SOUTH);

        // 2. MySQL Database Connection Card
        RoundedPanel dbCard = new RoundedPanel(14, new Color(251, 246, 234));
        dbCard.setLayout(new BorderLayout());
        dbCard.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel dbHead = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        dbHead.setOpaque(false);
        dbHead.add(new JLabel(IconFactory.cog(20, GOLD)));
        JLabel dbTitle = new JLabel("MySQL Database Configuration");
        dbTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        dbTitle.setForeground(TEXT_DARK);
        dbHead.add(dbTitle);
        dbCard.add(dbHead, BorderLayout.NORTH);

        JPanel dbForm = new JPanel(new GridBagLayout());
        dbForm.setOpaque(false);
        dbForm.setBorder(new EmptyBorder(16, 0, 16, 0));

        dbHostField = createField(DBConnection.getHost());
        dbPortField = createField(String.valueOf(DBConnection.getPort()));
        dbNameField = createField(DBConnection.getDbName());
        dbUserField = createField(DBConnection.getUser());
        dbPasswordField = new JPasswordField("");
        styleField(dbPasswordField);

        addFormRow(dbForm, 0, "Host Server:", dbHostField);
        addFormRow(dbForm, 1, "Port Number:", dbPortField);
        addFormRow(dbForm, 2, "Database Name:", dbNameField);
        addFormRow(dbForm, 3, "Username:", dbUserField);
        addFormRow(dbForm, 4, "Password:", dbPasswordField);

        dbStatusLabel = new JLabel("Ready");
        dbStatusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        dbStatusLabel.setForeground(TEXT_MUTED);

        JPanel dbBottom = new JPanel(new BorderLayout());
        dbBottom.setOpaque(false);

        JButton testDbBtn = new JButton("Test Connection");
        testDbBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        testDbBtn.setBackground(new Color(240, 233, 222));
        testDbBtn.setForeground(TEXT_DARK);
        testDbBtn.setFocusPainted(false);
        testDbBtn.setBorder(new EmptyBorder(8, 14, 8, 14));
        testDbBtn.addActionListener(e -> testDatabaseConnection());

        JButton saveDbBtn = createPrimaryButton("Save & Apply");
        saveDbBtn.addActionListener(e -> saveDbProperties());

        JPanel dbBtnGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        dbBtnGroup.setOpaque(false);
        dbBtnGroup.add(testDbBtn);
        dbBtnGroup.add(saveDbBtn);

        dbBottom.add(dbStatusLabel, BorderLayout.WEST);
        dbBottom.add(dbBtnGroup, BorderLayout.EAST);

        dbCard.add(dbForm, BorderLayout.CENTER);
        dbCard.add(dbBottom, BorderLayout.SOUTH);

        cardsRow.add(profileCard);
        cardsRow.add(dbCard);
        root.add(cardsRow);

        JScrollPane scrollPane = new JScrollPane(root);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadCurrentConfig();
    }

    private void addFormRow(JPanel panel, int row, String labelText, JComponent field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 4, 6, 14);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 4);
        panel.add(field, gbc);
    }

    private JTextField createField(String text) {
        JTextField tf = new JTextField(text);
        styleField(tf);
        return tf;
    }

    private void styleField(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comp.setPreferredSize(new Dimension(0, 36));
        comp.setBackground(Color.WHITE);
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 205), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(GOLD);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        return btn;
    }

    private void loadCurrentConfig() {
        File file = new File("db.properties");
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                Properties p = new Properties();
                p.load(in);
                dbHostField.setText(p.getProperty("db.host", "localhost"));
                dbPortField.setText(p.getProperty("db.port", "3306"));
                dbNameField.setText(p.getProperty("db.name", "dineease"));
                dbUserField.setText(p.getProperty("db.user", "root"));
                dbPasswordField.setText(p.getProperty("db.password", ""));
            } catch (Exception ignored) {}
        }
    }

    private void testDatabaseConnection() {
        dbStatusLabel.setText("Connecting...");
        dbStatusLabel.setForeground(TEXT_MUTED);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            String message = "";

            @Override
            protected Boolean doInBackground() {
                try (Connection conn = DBConnection.getConnection()) {
                    return conn != null && !conn.isClosed();
                } catch (Exception ex) {
                    message = ex.getMessage();
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) {
                        dbStatusLabel.setText("✓ Connected successfully to MySQL!");
                        dbStatusLabel.setForeground(new Color(46, 158, 91));
                    } else {
                        dbStatusLabel.setText("✕ Connection failed: " + message);
                        dbStatusLabel.setForeground(new Color(224, 79, 74));
                    }
                } catch (Exception ex) {
                    dbStatusLabel.setText("✕ Connection error");
                    dbStatusLabel.setForeground(new Color(224, 79, 74));
                }
            }
        };
        worker.execute();
    }

    private void saveDbProperties() {
        Properties p = new Properties();
        p.setProperty("db.host", dbHostField.getText().trim());
        p.setProperty("db.port", dbPortField.getText().trim());
        p.setProperty("db.name", dbNameField.getText().trim());
        p.setProperty("db.user", dbUserField.getText().trim());
        p.setProperty("db.password", new String(dbPasswordField.getPassword()));

        try (FileOutputStream out = new FileOutputStream("db.properties")) {
            p.store(out, "DineEase Database Configuration");
            DBConnection.loadConfig();
            JOptionPane.showMessageDialog(this,
                    "Database configuration saved successfully in db.properties!",
                    "Settings",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to save db.properties: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
