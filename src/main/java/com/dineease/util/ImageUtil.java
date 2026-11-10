package com.dineease.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public final class ImageUtil {

    private ImageUtil() {
    }

    public static BufferedImage loadBufferedImage(String resourcePath) {
        try (InputStream inputStream = ImageUtil.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return null;
            }
            return ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ImageIcon loadIcon(String resourcePath, int width, int height) {
        BufferedImage image = loadBufferedImage(resourcePath);
        if (image == null) {
            return null;
        }
        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
