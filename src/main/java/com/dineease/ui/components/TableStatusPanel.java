package com.dineease.ui.components;

import com.dineease.ui.IconFactory;

import javax.swing.*;
import java.awt.*;

public class TableStatusPanel extends RoundedPanel {
    private final JLabel availableCount;
    private final JLabel occupiedCount;
    private final JLabel reservedCount;
    private final JLabel totalCount;

    public TableStatusPanel() {
        super(14, new Color(251, 246, 234), new Color(230, 222, 210), true);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(18, 20, 16, 20));

        // Header
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titlePanel.setOpaque(false);
        JLabel icon = new JLabel(IconFactory.tableFurniture(18, new Color(197, 139, 43)));
        JLabel title = new JLabel("Table Status");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(new Color(43, 27, 18));
        titlePanel.add(icon);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Center 3 Columns: Available (green), Occupied (red), Reserved (amber)
        JPanel columnsPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        columnsPanel.setOpaque(false);
        columnsPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 14, 0));

        availableCount = new JLabel("0");
        occupiedCount = new JLabel("0");
        reservedCount = new JLabel("0");

        columnsPanel.add(createColumn("Available", availableCount, new Color(46, 158, 91), true));
        columnsPanel.add(createColumn("Occupied", occupiedCount, new Color(224, 79, 74), true));
        columnsPanel.add(createColumn("Reserved", reservedCount, new Color(237, 170, 58), false));

        // Bottom Row: Table icon, "Total Tables", real count on right
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);
        bottomRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(232, 224, 212)),
                BorderFactory.createEmptyBorder(12, 4, 2, 4)
        ));

        JPanel leftBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftBottom.setOpaque(false);
        JLabel bIcon = new JLabel(IconFactory.tableFurniture(16, new Color(110, 95, 80)));
        JLabel bLabel = new JLabel("Total Tables");
        bLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bLabel.setForeground(new Color(60, 48, 38));
        leftBottom.add(bIcon);
        leftBottom.add(bLabel);

        totalCount = new JLabel("0");
        totalCount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalCount.setForeground(new Color(43, 27, 18));

        bottomRow.add(leftBottom, BorderLayout.WEST);
        bottomRow.add(totalCount, BorderLayout.EAST);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setOpaque(false);
        centerContainer.add(columnsPanel, BorderLayout.CENTER);
        centerContainer.add(bottomRow, BorderLayout.SOUTH);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JPanel createColumn(String label, JLabel countLabel, Color circleColor, boolean hasDividerRight) {
        JPanel col = new JPanel(new GridBagLayout());
        col.setOpaque(false);
        if (hasDividerRight) {
            col.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(232, 224, 212)));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 8, 0);

        JLabel circle = new JLabel(IconFactory.utensils(18, Color.WHITE)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(circleColor);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        circle.setPreferredSize(new Dimension(42, 42));
        circle.setHorizontalAlignment(SwingConstants.CENTER);
        col.add(circle, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 6, 0);
        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(90, 75, 62));
        col.add(nameLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        countLabel.setForeground(new Color(43, 27, 18));
        col.add(countLabel, gbc);

        return col;
    }

    public void updateData(int available, int occupied, int reserved, int total) {
        availableCount.setText(String.valueOf(available));
        occupiedCount.setText(String.valueOf(occupied));
        reservedCount.setText(String.valueOf(reserved));
        totalCount.setText(String.valueOf(total));
        repaint();
    }
}

