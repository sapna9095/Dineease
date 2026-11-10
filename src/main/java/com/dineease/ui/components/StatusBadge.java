package com.dineease.ui.components;

import javax.swing.*;
import java.awt.*;

public class StatusBadge extends JLabel {
    private Color badgeBackground;
    private Color badgeForeground;

    public StatusBadge(String status) {
        super(status);
        applyStatus(status);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));
    }

    public void updateStatus(String status) {
        setText(status);
        applyStatus(status);
        repaint();
    }

    private void applyStatus(String status) {
        if (status == null) status = "";
        String s = status.trim().toUpperCase();
        switch (s) {
            case "SERVED":
                badgeBackground = new Color(228, 219, 245);
                badgeForeground = new Color(124, 77, 190);
                break;
            case "READY":
                badgeBackground = new Color(213, 241, 222);
                badgeForeground = new Color(46, 158, 91);
                break;
            case "PREPARING":
                badgeBackground = new Color(218, 233, 253);
                badgeForeground = new Color(76, 141, 246);
                break;
            case "PENDING":
                badgeBackground = new Color(253, 238, 206);
                badgeForeground = new Color(217, 147, 34);
                break;
            case "CANCELLED":
                badgeBackground = new Color(250, 222, 221);
                badgeForeground = new Color(224, 79, 74);
                break;
            default:
                badgeBackground = new Color(238, 233, 224);
                badgeForeground = new Color(110, 95, 80);
                break;
        }
        setForeground(badgeForeground);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(badgeBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}

