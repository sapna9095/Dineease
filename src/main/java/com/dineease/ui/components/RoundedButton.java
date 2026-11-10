package com.dineease.ui.components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private final int cornerRadius = 20;

    public RoundedButton(String text) {
        super(text);

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        setForeground(Color.WHITE);

        setBackground(new Color(46, 125, 50));

        setFont(new Font("Segoe UI", Font.BOLD, 16));

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        Graphics2D g2 = (Graphics2D) graphics.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                cornerRadius,
                cornerRadius
        );

        super.paintComponent(g2);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        // No border
    }
}