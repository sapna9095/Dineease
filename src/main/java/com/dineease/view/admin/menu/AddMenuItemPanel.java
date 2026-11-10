package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;
import com.dineease.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddMenuItemPanel extends JPanel {

    private JTextField itemNameField;
    private JComboBox<String> categoryComboBox;
    private JTextField priceField;
    private JTextField descriptionField;
    private JComboBox<String> statusComboBox;

    private JLabel imagePreviewLabel;
    private JLabel imagePathLabel;

    private JButton chooseImageButton;

    private String selectedImagePath;

    private MenuController menuController;


    // =========================
    // CONSTRUCTOR
    // =========================

    public AddMenuItemPanel() {

        menuController =
                new MenuController();

        selectedImagePath = null;


        setLayout(
                new BorderLayout(
                        20,
                        20
                )
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


        createTitle();

        createForm();

        createButtons();

    }


    // =========================
    // TITLE
    // =========================

    private void createTitle() {

        JPanel titlePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        titlePanel.setBackground(
                UITheme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "Add Menu Item"
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
                        "Add a new food item to your restaurant menu"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                Color.GRAY
        );


        titlePanel.add(
                title
        );

        titlePanel.add(
                subtitle
        );


        add(
                titlePanel,
                BorderLayout.NORTH
        );

    }


    // =========================
    // FORM
    // =========================

    private void createForm() {

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setBackground(
                UITheme.CARD
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35
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

        gbc.anchor =
                GridBagConstraints.NORTHWEST;


        // =========================
        // ITEM NAME
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Item Name:"
        );


        itemNameField =
                new JTextField();

        itemNameField.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        addField(
                formPanel,
                gbc,
                itemNameField
        );


        // =========================
        // CATEGORY
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Category:"
        );


        categoryComboBox =
                new JComboBox<>(
                        new String[]{
                                "Starter",
                                "Main Course",
                                "Beverage",
                                "Dessert",
                                "Salad",
                                "Soup",
                                "Pizza",
                                "Burger",
                                "Pasta"
                        }
                );


        categoryComboBox.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        addField(
                formPanel,
                gbc,
                categoryComboBox
        );


        // =========================
        // PRICE
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Price (₹):"
        );


        priceField =
                new JTextField();

        priceField.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        addField(
                formPanel,
                gbc,
                priceField
        );


        // =========================
        // DESCRIPTION
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Description:"
        );


        descriptionField =
                new JTextField();

        descriptionField.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        addField(
                formPanel,
                gbc,
                descriptionField
        );


        // =========================
        // STATUS
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Status:"
        );


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Unavailable"
                        }
                );


        statusComboBox.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        addField(
                formPanel,
                gbc,
                statusComboBox
        );


        // =========================
        // IMAGE
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Food Image:"
        );


        JPanel imagePanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        imagePanel.setBackground(
                UITheme.CARD
        );


        chooseImageButton =
                new JButton(
                        "Choose Image"
                );

        UITheme.styleSecondaryButton(
                chooseImageButton
        );


        imagePathLabel =
                new JLabel(
                        "No image selected"
                );

        imagePathLabel.setForeground(
                Color.GRAY
        );


        imagePanel.add(
                chooseImageButton,
                BorderLayout.WEST
        );

        imagePanel.add(
                imagePathLabel,
                BorderLayout.CENTER
        );


        gbc.gridx = 1;
        gbc.gridy++;

        formPanel.add(
                imagePanel,
                gbc
        );


        chooseImageButton.addActionListener(
                e -> chooseImage()
        );


        // =========================
        // IMAGE PREVIEW
        // =========================

        addLabel(
                formPanel,
                gbc,
                "Preview:"
        );


        imagePreviewLabel =
                new JLabel(
                        "No Image",
                        SwingConstants.CENTER
                );

        imagePreviewLabel.setPreferredSize(
                new Dimension(
                        220,
                        150
                )
        );

        imagePreviewLabel.setBorder(
                BorderFactory.createLineBorder(
                        UITheme.BORDER
                )
        );

        imagePreviewLabel.setForeground(
                Color.GRAY
        );

        imagePreviewLabel.setBackground(
                UITheme.BACKGROUND
        );

        imagePreviewLabel.setOpaque(
                true
        );


        gbc.gridx = 1;
        gbc.gridy++;

        formPanel.add(
                imagePreviewLabel,
                gbc
        );


        add(
                formPanel,
                BorderLayout.CENTER
        );

    }


    // =========================
    // BUTTONS
    // =========================

    private void createButtons() {

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        buttonPanel.setBackground(
                UITheme.BACKGROUND
        );


        JButton clearButton =
                new JButton(
                        "Clear"
                );

        UITheme.styleSecondaryButton(
                clearButton
        );


        JButton addButton =
                new JButton(
                        "Add Menu Item"
                );

        UITheme.stylePrimaryButton(
                addButton
        );


        clearButton.addActionListener(
                e -> clearFields()
        );


        addButton.addActionListener(
                e -> addMenuItem()
        );


        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                addButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

    }


    // =========================
    // ADD LABEL
    // =========================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text
    ) {

        gbc.gridx = 0;
        gbc.gridy++;


        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(
                UITheme.TEXT
        );


        panel.add(
                label,
                gbc
        );

    }


    // =========================
    // ADD FIELD
    // =========================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            JComponent component
    ) {

        gbc.gridx = 1;


        panel.add(
                component,
                gbc
        );


        gbc.gridx = 0;

    }


    // =========================
    // CHOOSE IMAGE
    // =========================

    private void chooseImage() {

        JFileChooser fileChooser =
                new JFileChooser();


        fileChooser.setDialogTitle(
                "Select Food Image"
        );


        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Image Files",
                        "jpg",
                        "jpeg",
                        "png",
                        "webp"
                )
        );


        int result =
                fileChooser.showOpenDialog(
                        this
                );


        if (
                result != JFileChooser.APPROVE_OPTION
        ) {

            return;
        }


        File selectedFile =
                fileChooser.getSelectedFile();


        try {

            // =========================
            // CREATE IMAGE DIRECTORY
            // =========================

            File imageFolder =
                    new File(
                            "images/menu"
                    );


            if (
                    !imageFolder.exists()
            ) {

                imageFolder.mkdirs();

            }


            // =========================
            // CREATE UNIQUE FILE NAME
            // =========================

            String originalName =
                    selectedFile.getName();


            String extension =
                    "";


            int dotIndex =
                    originalName.lastIndexOf(".");


            if (
                    dotIndex >= 0
            ) {

                extension =
                        originalName.substring(
                                dotIndex
                        );

            }


            String newFileName =
                    "menu_"
                            + System.currentTimeMillis()
                            + extension;


            File destination =
                    new File(
                            imageFolder,
                            newFileName
                    );


            // =========================
            // COPY IMAGE
            // =========================

            Files.copy(
                    selectedFile.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );


            // =========================
            // SAVE RELATIVE PATH
            // =========================

            selectedImagePath =
                    "images/menu/"
                            + newFileName;


            imagePathLabel.setText(
                    newFileName
            );


            imagePathLabel.setForeground(
                    UITheme.TEXT
            );


            // =========================
            // SHOW PREVIEW
            // =========================

            showImagePreview(
                    destination
            );


        }
        catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to select image.\n"
                            + e.getMessage(),
                    "Image Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    // =========================
    // IMAGE PREVIEW
    // =========================

    private void showImagePreview(
            File imageFile
    ) {

        try {

            ImageIcon originalIcon =
                    new ImageIcon(
                            imageFile.getAbsolutePath()
                    );


            Image originalImage =
                    originalIcon.getImage();


            int previewWidth = 220;
            int previewHeight = 150;


            Image scaledImage =
                    originalImage.getScaledInstance(
                            previewWidth,
                            previewHeight,
                            Image.SCALE_SMOOTH
                    );


            ImageIcon previewIcon =
                    new ImageIcon(
                            scaledImage
                    );


            imagePreviewLabel.setText(
                    ""
            );


            imagePreviewLabel.setIcon(
                    previewIcon
            );

        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }


    // =========================
    // ADD MENU ITEM
    // =========================

    private void addMenuItem() {

        String itemName =
                itemNameField
                        .getText()
                        .trim();


        String category =
                categoryComboBox
                        .getSelectedItem()
                        .toString();


        String priceText =
                priceField
                        .getText()
                        .trim();


        String description =
                descriptionField
                        .getText()
                        .trim();


        String status =
                statusComboBox
                        .getSelectedItem()
                        .toString();


        // =========================
        // VALIDATION
        // =========================

        if (
                itemName.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter item name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            itemNameField.requestFocus();

            return;
        }


        if (
                priceText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter price.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            priceField.requestFocus();

            return;
        }


        double price;


        try {

            price =
                    Double.parseDouble(
                            priceText
                    );

        }
        catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            priceField.requestFocus();

            return;
        }


        if (
                price < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price cannot be negative.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // IMAGE REQUIRED
        // =========================

        if (
                selectedImagePath == null
                        ||
                        selectedImagePath.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a food image.",
                    "Image Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =========================
        // CREATE MENU ITEM
        // =========================

        MenuItem menuItem =
                new MenuItem(
                        itemName,
                        category,
                        price,
                        description,
                        status
                );


        menuItem.setImagePath(
                selectedImagePath
        );


        // =========================
        // SAVE
        // =========================

        boolean success =
                menuController.addMenuItem(
                        menuItem
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Menu item added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            clearFields();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add menu item.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        itemNameField.setText("");

        priceField.setText("");

        descriptionField.setText("");

        categoryComboBox.setSelectedIndex(
                0
        );

        statusComboBox.setSelectedIndex(
                0
        );


        selectedImagePath = null;


        imagePathLabel.setText(
                "No image selected"
        );

        imagePathLabel.setForeground(
                Color.GRAY
        );


        imagePreviewLabel.setIcon(
                null
        );

        imagePreviewLabel.setText(
                "No Image"
        );

    }

}