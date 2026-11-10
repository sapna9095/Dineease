package com.dineease.ui.components;

import javax.swing.*;
import java.awt.*;

public class StatCard extends RoundedPanel {
    private final JLabel iconLabel;
    private final JLabel titleLabel;
    private final JLabel valueLabel;
    private final JLabel trendLabel;
    private final Color iconBgColor;

    public StatCard(String title, String initialValue, String initialTrend, Icon icon, Color iconBg) {
        super(14, new Color(251, 246, 234), new Color(230, 222, 210), true);
        this.iconBgColor = iconBg;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 18, 14, 18));

        // Top Row: Icon circle + Title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        topPanel.setOpaque(false);

        iconLabel = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(iconBgColor != null ? iconBgColor : new Color(241, 224, 190));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconLabel.setPreferredSize(new Dimension(38, 38));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(85, 68, 54));

        topPanel.add(iconLabel);
        topPanel.add(titleLabel);

        // Center / Value
        valueLabel = new JLabel(initialValue);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        valueLabel.setForeground(new Color(43, 27, 18));
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));

        // Bottom / Trend
        trendLabel = new JLabel(initialTrend);
        trendLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        trendLabel.setForeground(new Color(46, 158, 91));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(topPanel);
        centerPanel.add(valueLabel);
        centerPanel.add(trendLabel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void updateData(String value, String trendText, boolean isPositive, boolean isNeutral) {
        valueLabel.setText(value);
        if (isNeutral) {
            trendLabel.setText("↗ " + trendText);
            trendLabel.setForeground(new Color(130, 120, 110));
        } else if (isPositive) {
            trendLabel.setText("↗ " + trendText);
            trendLabel.setForeground(new Color(46, 158, 91));
        } else {
            trendLabel.setText("↘ " + trendText);
            trendLabel.setForeground(new Color(224, 79, 74));
        }
        repaint();
    }
}

