package com.dineease.ui;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.*;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public final class IconFactory {
    private IconFactory() {
    }

    public static Icon chefHat(int size, Color color) {
        return icon(MaterialDesignC.CHEF_HAT, size, color);
    }

    public static Icon user(int size, Color color) {
        return icon(MaterialDesignA.ACCOUNT_OUTLINE, size, color);
    }

    public static Icon lock(int size, Color color) {
        return icon(MaterialDesignL.LOCK_OUTLINE, size, color);
    }

    public static Icon eye(int size, Color color) {
        return icon(MaterialDesignE.EYE_OUTLINE, size, color);
    }

    public static Icon eyeOff(int size, Color color) {
        return icon(MaterialDesignE.EYE_OFF_OUTLINE, size, color);
    }

    public static Icon arrowRight(int size, Color color) {
        return icon(MaterialDesignA.ARROW_RIGHT, size, color);
    }

    public static Icon shieldCheck(int size, Color color) {
        return icon(MaterialDesignS.SHIELD_CHECK_OUTLINE, size, color);
    }

    public static Icon trendingUp(int size, Color color) {
        return icon(MaterialDesignT.TRENDING_UP, size, color);
    }

    public static Icon clock(int size, Color color) {
        return icon(MaterialDesignC.CLOCK_OUTLINE, size, color);
    }

    public static Icon users(int size, Color color) {
        return icon(MaterialDesignA.ACCOUNT_GROUP, size, color);
    }

    public static Icon utensils(int size, Color color) {
        return icon(MaterialDesignS.SILVERWARE_FORK_KNIFE, size, color);
    }

    public static Icon silverware(int size, Color color) {
        return icon(MaterialDesignS.SILVERWARE_FORK_KNIFE, size, color);
    }

    public static Icon clipboard(int size, Color color) {
        return icon(MaterialDesignC.CLIPBOARD_TEXT_OUTLINE, size, color);
    }

    public static Icon clipboardList(int size, Color color) {
        return icon(MaterialDesignC.CLIPBOARD_LIST_OUTLINE, size, color);
    }

    public static Icon cube(int size, Color color) {
        return icon(MaterialDesignC.CUBE_OUTLINE, size, color);
    }

    public static Icon barChart(int size, Color color) {
        return icon(MaterialDesignC.CHART_BAR, size, color);
    }

    public static Icon home(int size, Color color) {
        return icon(MaterialDesignH.HOME, size, color);
    }

    public static Icon account(int size, Color color) {
        return icon(MaterialDesignA.ACCOUNT, size, color);
    }

    public static Icon tableFurniture(int size, Color color) {
        return icon(MaterialDesignT.TABLE_FURNITURE, size, color);
    }

    public static Icon creditCard(int size, Color color) {
        return icon(MaterialDesignC.CREDIT_CARD, size, color);
    }

    public static Icon fileDocument(int size, Color color) {
        return icon(MaterialDesignF.FILE_DOCUMENT, size, color);
    }

    public static Icon cog(int size, Color color) {
        return icon(MaterialDesignC.COG, size, color);
    }

    public static Icon refresh(int size, Color color) {
        return icon(MaterialDesignR.REFRESH, size, color);
    }

    public static Icon calendar(int size, Color color) {
        return icon(MaterialDesignC.CALENDAR, size, color);
    }

    public static Icon roomService(int size, Color color) {
        return icon(MaterialDesignR.ROOM_SERVICE_OUTLINE, size, color);
    }

    public static Icon alert(int size, Color color) {
        return icon(MaterialDesignA.ALERT_OUTLINE, size, color);
    }

    public static Icon triangleUp(int size, Color color) {
        return icon(MaterialDesignT.TRENDING_UP, size, color);
    }

    private static Icon icon(Ikon ikon, int size, Color color) {
        return FontIcon.of(ikon, size, color);
    }
}
