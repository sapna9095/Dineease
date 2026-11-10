package com.dineease.ui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 16;
    private Color backgroundColor;
    private Color borderColor = new Color(230, 222, 210);
    private boolean drawShadow = true;

    public RoundedPanel() {
        this(16, new Color(251, 246, 234));
    }

    public RoundedPanel(int radius, Color bg) {
        this(radius, bg, new Color(230, 222, 210), true);
    }

    public RoundedPanel(int radius, Color bg, Color border, boolean shadow) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bg;
        this.borderColor = border;
        this.drawShadow = shadow;
        setOpaque(false);
    }

    public void setBackgroundColor(Color bg) {
        this.backgroundColor = bg;
        repaint();
    }

    public void setBorderColor(Color border) {
        this.borderColor = border;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int shadowOffset = drawShadow ? 2 : 0;
        int width = getWidth() - (drawShadow ? 4 : 1);
        int height = getHeight() - (drawShadow ? 4 : 1);
        int x = drawShadow ? 2 : 0;
        int y = drawShadow ? 1 : 0;

        if (drawShadow) {
            // Soft shadow layers
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fillRoundRect(x + 1, y + 2, width, height, cornerRadius, cornerRadius);
            g2.setColor(new Color(0, 0, 0, 18));
            g2.fillRoundRect(x, y + 1, width, height, cornerRadius, cornerRadius);
        }

        // Fill background
        g2.setColor(backgroundColor != null ? backgroundColor : getBackground());
        g2.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);

        // Draw border
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}

