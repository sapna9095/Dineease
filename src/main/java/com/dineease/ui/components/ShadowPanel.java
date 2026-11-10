package com.dineease.ui.components;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    private int cornerRadius = 25;

    public ShadowPanel() {
        setOpaque(false);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        Graphics2D g2 = (Graphics2D) graphics.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Shadow
        g2.setColor(new Color(0, 0, 0, 40));

        g2.fillRoundRect(
                8,
                8,
                getWidth() - 16,
                getHeight() - 16,
                cornerRadius,
                cornerRadius
        );

        // White Card
        g2.setColor(getBackground());

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 16,
                getHeight() - 16,
                cornerRadius,
                cornerRadius
        );

        g2.dispose();

        super.paintComponent(graphics);
    }
}