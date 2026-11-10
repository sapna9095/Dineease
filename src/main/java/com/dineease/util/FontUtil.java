package com.dineease.util;

import java.awt.*;
import java.io.InputStream;

public final class FontUtil {

    private static final String PLAYFAIR_PATH = "/fonts/PlayfairDisplay-Regular.ttf";
    private static final String NUNITO_PATH = "/fonts/Nunito-Regular.ttf";

    private FontUtil() {
    }

    public static Font getPlayfairDisplay(float size, int style) {
        return loadFont(PLAYFAIR_PATH, style, size, "Playfair Display");
    }

    public static Font getNunito(float size, int style) {
        return loadFont(NUNITO_PATH, style, size, "Nunito");
    }

    private static Font loadFont(String path, int style, float size, String fallbackName) {
        try (InputStream inputStream = FontUtil.class.getResourceAsStream(path)) {
            if (inputStream != null) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(style, size);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
                return font;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Font(fallbackName, style, Math.round(size));
    }
}
