package com.dineease.util;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public final class Theme {

    public static final Color DARK_BROWN = new Color(36, 25, 19);
    public static final Color DARK_BROWN_2 = new Color(58, 38, 27);
    public static final Color GOLD = new Color(197, 139, 43);
    public static final Color GOLD_HOVER = new Color(214, 154, 53);
    public static final Color GOLD_SOFT = new Color(212, 160, 58);
    public static final Color CREAM = new Color(250, 243, 232);
    public static final Color CREAM_2 = new Color(241, 224, 190);
    public static final Color TEXT_DARK = new Color(43, 27, 18);
    public static final Color TEXT_MUTED = new Color(107, 91, 78);
    public static final Color INPUT_BG = new Color(255, 253, 248);
    public static final Color INPUT_BORDER = new Color(227, 216, 200);
    public static final Color WHITE = Color.WHITE;

    public static final Color SIDEBAR_BG = new Color(46, 27, 16);
    public static final Color SIDEBAR_BG_ALT = new Color(42, 26, 18);
    public static final Color SIDEBAR_ACTIVE = new Color(139, 106, 50, 178);
    public static final Color SIDEBAR_TEXT = new Color(243, 233, 216);
    public static final Color SIDEBAR_ICON = new Color(227, 201, 143);
    public static final Color MAIN_BG = new Color(250, 243, 232);
    public static final Color CARD_BG = new Color(255, 252, 246);
    public static final Color CARD_BORDER = new Color(234, 223, 203);
    public static final Color GREEN = new Color(46, 139, 87);
    public static final Color RED = new Color(217, 83, 79);
    public static final Color STATUS_PENDING = new Color(240, 176, 58);
    public static final Color STATUS_PREPARING = new Color(74, 143, 240);
    public static final Color STATUS_READY = new Color(46, 158, 91);
    public static final Color STATUS_SERVED = new Color(123, 82, 192);
    public static final Color STATUS_CANCELLED = new Color(224, 82, 74);
    public static final Color PILL_SERVED_BG = new Color(228, 219, 245);
    public static final Color PILL_SERVED_TEXT = new Color(107, 70, 181);
    public static final Color PILL_READY_BG = new Color(213, 236, 217);
    public static final Color PILL_READY_TEXT = new Color(46, 125, 79);
    public static final Color PILL_PREPARING_BG = new Color(214, 230, 251);
    public static final Color PILL_PREPARING_TEXT = new Color(47, 111, 203);
    public static final Color PILL_PENDING_BG = new Color(251, 231, 190);
    public static final Color PILL_PENDING_TEXT = new Color(183, 121, 31);
    public static final Color ICON_CIRCLE = new Color(241, 224, 190);
    public static final Color MUTED = new Color(107, 91, 78);
    public static final Color SIDEBAR_MUTED = new Color(205, 191, 174);
    public static final Color GOLD_LINE = new Color(107, 82, 48);
    public static final Color SOFT_SHADOW = new Color(0, 0, 0, 24);

    private Theme() {
    }

    public static void apply() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        UIManager.put("Panel.background", CREAM);
        UIManager.put("Label.foreground", TEXT_DARK);
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 9);
        UIManager.put("TextField.background", INPUT_BG);
        UIManager.put("TextField.foreground", TEXT_DARK);
        UIManager.put("PasswordField.background", INPUT_BG);
        UIManager.put("CheckBox.focusWidth", 2);
        UIManager.put("Button.focusWidth", 2);
        UIManager.put("Button.borderColor", GOLD);
        UIManager.put("Button.default.startBackground", GOLD);
        UIManager.put("Button.default.endBackground", GOLD_HOVER);
        UIManager.put("Button.default.foreground", WHITE);
        UIManager.put("ScrollBar.thumbArc", 12);
        UIManager.put("Component.focusColor", new Color(197, 139, 43, 80));
    }
}
