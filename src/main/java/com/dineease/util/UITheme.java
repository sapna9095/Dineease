
package com.dineease.util;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {

    // =========================
    // LUXURY RESTAURANT COLORS
    // =========================

    // Main golden accent
    public static final Color PRIMARY =
            new Color(190, 145, 55);

    // Dark golden accent
    public static final Color PRIMARY_DARK =
            new Color(125, 88, 30);

    // Main application background
    public static final Color BACKGROUND =
            new Color(248, 245, 239);

    // Cards / panels
    public static final Color CARD =
            new Color(255, 252, 246);

    // Main text
    public static final Color TEXT =
            new Color(38, 34, 29);

    // Secondary text
    public static final Color MUTED_TEXT =
            new Color(120, 112, 101);

    // Borders
    public static final Color BORDER =
            new Color(224, 215, 199);

    // Danger / delete
    public static final Color DANGER =
            new Color(190, 55, 45);

    // Success
    public static final Color SUCCESS =
            new Color(54, 125, 75);

    // Warning
    public static final Color WARNING =
            new Color(210, 145, 40);


    // =========================
    // APPLY THEME
    // =========================

    public static void applyTheme() {

        try {

            UIManager.setLookAndFeel(
                    new FlatLightLaf()
            );

            // Panels
            UIManager.put(
                    "Panel.background",
                    BACKGROUND
            );

            // Text
            UIManager.put(
                    "Label.foreground",
                    TEXT
            );

            // Tables
            UIManager.put(
                    "Table.background",
                    CARD
            );

            UIManager.put(
                    "Table.foreground",
                    TEXT
            );

            UIManager.put(
                    "Table.selectionBackground",
                    PRIMARY
            );

            UIManager.put(
                    "Table.selectionForeground",
                    Color.WHITE
            );

            UIManager.put(
                    "Table.gridColor",
                    BORDER
            );

            UIManager.put(
                    "TableHeader.background",
                    PRIMARY_DARK
            );

            UIManager.put(
                    "TableHeader.foreground",
                    Color.WHITE
            );

            // Buttons
            UIManager.put(
                    "Button.arc",
                    10
            );

            // Components
            UIManager.put(
                    "Component.arc",
                    10
            );

            // Text fields
            UIManager.put(
                    "TextComponent.arc",
                    8
            );

            UIManager.put(
                    "TextField.background",
                    Color.WHITE
            );

            UIManager.put(
                    "ComboBox.background",
                    Color.WHITE
            );

            // Scroll bars
            UIManager.put(
                    "ScrollBar.thumbArc",
                    10
            );

        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }


    // =========================
    // PRIMARY BUTTON
    // =========================

    public static void stylePrimaryButton(
            JButton button
    ) {

        button.setBackground(
                PRIMARY
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // =========================
    // SECONDARY BUTTON
    // =========================

    public static void styleSecondaryButton(
            JButton button
    ) {

        button.setBackground(
                CARD
        );

        button.setForeground(
                TEXT
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER
                        ),

                        new EmptyBorder(
                                9,
                                18,
                                9,
                                18
                        )

                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // =========================
    // DANGER BUTTON
    // =========================

    public static void styleDangerButton(
            JButton button
    ) {

        button.setBackground(
                DANGER
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // =========================
    // SUCCESS BUTTON
    // =========================

    public static void styleSuccessButton(
            JButton button
    ) {

        button.setBackground(
                SUCCESS
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // =========================
    // WARNING BUTTON
    // =========================

    public static void styleWarningButton(
            JButton button
    ) {

        button.setBackground(
                WARNING
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // =========================
    // CARD BORDER
    // =========================

    public static javax.swing.border.Border cardBorder() {

        return BorderFactory.createCompoundBorder(

                BorderFactory.createLineBorder(
                        BORDER
                ),

                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )

        );

    }

}


