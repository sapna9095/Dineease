package com.dineease.view.admin.menu;

import com.dineease.controller.MenuController;
import com.dineease.model.MenuItem;
import com.dineease.ui.IconFactory;
import com.dineease.ui.components.RoundedPanel;
import com.dineease.util.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DeleteMenuItemPanel extends JPanel {

    private static final Color BG_CREAM = new Color(247, 241, 229);
    private static final Color GOLD = new Color(200, 144, 46);
    private static final Color TEXT_DARK = new Color(43, 27, 18);
    private static final Color TEXT_MUTED = new Color(110, 95, 80);
    private static final Color DANGER_RED = new Color(224, 79, 74);

    private final JComboBox<String> itemComboBox;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final MenuController menuController;
    private List<MenuItem> menuItems;

    private final JLabel itemIdVal = new JLabel("-");
    private final JLabel itemNameVal = new JLabel("-");
    private final JLabel itemCategoryVal = new JLabel("-");
    private final JLabel itemPriceVal = new JLabel("-");
    private final JLabel itemStatusVal = new JLabel("-");

    public DeleteMenuItemPanel() {
        menuController = new MenuController();

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

        JLabel title = new JLabel("Delete Menu Item");
        title.setFont(FontUtil.getPlayfairDisplay(28f, Font.BOLD));
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Remove or archive items from the restaurant menu database");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitle);

        headerPanel.add(titleBlock, BorderLayout.WEST);
        root.add(headerPanel);
        root.add(Box.createVerticalStrut(22));

        // =========================
        // CARD CONTAINER
        // =========================
        RoundedPanel card = new RoundedPanel(14, new Color(251, 246, 234));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(22, 26, 22, 26));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 12);

        // Select Row
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel selectLbl = new JLabel("Select Menu Item:");
        selectLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectLbl.setForeground(TEXT_DARK);
        formPanel.add(selectLbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel selectRow = new JPanel(new BorderLayout(8, 0));
        selectRow.setOpaque(false);

        itemComboBox = new JComboBox<>();
        itemComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        itemComboBox.setBackground(Color.WHITE);
        itemComboBox.setPreferredSize(new Dimension(300, 38));

        refreshButton = new JButton();
        refreshButton.setIcon(IconFactory.refresh(15, TEXT_DARK));
        refreshButton.setToolTipText("Reload Menu Items");
        refreshButton.setPreferredSize(new Dimension(38, 38));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.addActionListener(e -> loadMenuItems());

        selectRow.add(itemComboBox, BorderLayout.CENTER);
        selectRow.add(refreshButton, BorderLayout.EAST);
        formPanel.add(selectRow, gbc);

        // Preview Box for Selected Item Details
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 16, 8);

        JPanel previewCard = new JPanel(new GridLayout(5, 2, 8, 8));
        previewCard.setBackground(new Color(244, 238, 226));
        previewCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 218, 204), 1),
                new EmptyBorder(12, 16, 12, 16)
        ));

        addDetailRow(previewCard, "Item ID:", itemIdVal);
        addDetailRow(previewCard, "Item Name:", itemNameVal);
        addDetailRow(previewCard, "Category:", itemCategoryVal);
        addDetailRow(previewCard, "Price:", itemPriceVal);
        addDetailRow(previewCard, "Current Status:", itemStatusVal);

        formPanel.add(previewCard, gbc);

        // Action Buttons Row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 8, 8, 8);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonBar.setOpaque(false);

        deleteButton = new JButton("Delete Menu Item");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setBackground(DANGER_RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setBorder(new EmptyBorder(10, 22, 10, 22));

        buttonBar.add(deleteButton);
        formPanel.add(buttonBar, gbc);

        card.add(formPanel, BorderLayout.CENTER);
        root.add(card);

        JScrollPane scrollPane = new JScrollPane(root);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Item selection event listener
        itemComboBox.addActionListener(e -> updateSelectedItemPreview());

        // Delete button listener
        deleteButton.addActionListener(e -> deleteMenuItem());

        loadMenuItems();
    }

    private void addDetailRow(JPanel p, String label, JLabel val) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_MUTED);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        val.setForeground(TEXT_DARK);
        p.add(l);
        p.add(val);
    }

    private void updateSelectedItemPreview() {
        int index = itemComboBox.getSelectedIndex();
        if (menuItems != null && index >= 0 && index < menuItems.size()) {
            MenuItem item = menuItems.get(index);
            itemIdVal.setText("#" + item.getItemId());
            itemNameVal.setText(item.getItemName());
            itemCategoryVal.setText(item.getCategory());
            itemPriceVal.setText(String.format("₹ %.2f", item.getPrice()));
            itemStatusVal.setText(item.getStatus());
        } else {
            itemIdVal.setText("-");
            itemNameVal.setText("-");
            itemCategoryVal.setText("-");
            itemPriceVal.setText("-");
            itemStatusVal.setText("-");
        }
    }

    // =========================
    // LOAD ITEMS
    // =========================
    public void loadMenuItems() {
        SwingWorker<List<MenuItem>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<MenuItem> doInBackground() {
                return menuController.getAllMenuItems();
            }

            @Override
            protected void done() {
                try {
                    menuItems = get();
                    itemComboBox.removeAllItems();

                    if (menuItems == null || menuItems.isEmpty()) {
                        itemComboBox.addItem("-- No Menu Items Found --");
                        deleteButton.setEnabled(false);
                    } else {
                        deleteButton.setEnabled(true);
                        for (MenuItem item : menuItems) {
                            itemComboBox.addItem("#" + item.getItemId() + " - " + item.getItemName() + " (" + item.getCategory() + ")");
                        }
                    }
                    updateSelectedItemPreview();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    // =========================
    // DELETE ITEM
    // =========================
    private void deleteMenuItem() {
        int index = itemComboBox.getSelectedIndex();
        if (menuItems == null || index < 0 || index >= menuItems.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a menu item to delete.",
                    "No Item Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        MenuItem item = menuItems.get(index);

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \"" + item.getItemName() + "\" (ID: #" + item.getItemId() + ")?\n" +
                "Any linked past order and bill line items will also be cleanly unlinked.",
                "Confirm Delete Menu Item",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        deleteButton.setEnabled(false);
        deleteButton.setText("Deleting...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return menuController.deleteMenuItem(item.getItemId());
            }

            @Override
            protected void done() {
                deleteButton.setEnabled(true);
                deleteButton.setText("Delete Menu Item");
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(
                                DeleteMenuItemPanel.this,
                                "\"" + item.getItemName() + "\" has been deleted successfully!",
                                "Item Deleted",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        loadMenuItems();
                    } else {
                        JOptionPane.showMessageDialog(
                                DeleteMenuItemPanel.this,
                                "Failed to delete menu item. Please check database logs.",
                                "Delete Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            DeleteMenuItemPanel.this,
                            "Error while deleting: " + ex.getMessage(),
                            "Delete Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }
}
