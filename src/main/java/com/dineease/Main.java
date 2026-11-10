package com.dineease;

import com.dineease.ui.LoginFrame;
import com.dineease.util.Theme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1");
        System.setProperty("flatlaf.uiScale", "1");
        Theme.apply();
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}