package com.dineease.ui.delivery;

import com.dineease.model.DeliveryOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class JaipurDeliveryMapPanel extends JPanel {

    // Jaipur Areas Coordinates (Lat, Lng)
    // Restaurant base: Sitapura RIICO Hub
    public static class GeoLocation {
        public final String name;
        public final double lat;
        public final double lng;
        public final String description;

        public GeoLocation(String name, double lat, double lng, String description) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.description = description;
        }
    }

    public static final GeoLocation RESTAURANT_HUB =
            new GeoLocation("DineEase Kitchen (Sitapura)", 26.7818, 75.8234, "Central Kitchen & Hub");

    public static final Map<String, GeoLocation> JAIPUR_AREAS = new HashMap<>();

    static {
        JAIPUR_AREAS.put("Sitapura", new GeoLocation("Sitapura", 26.7818, 75.8234, "Industrial & Institutional Area"));
        JAIPUR_AREAS.put("Mansarovar", new GeoLocation("Mansarovar", 26.8524, 75.7674, "Metro & Market Sector"));
        JAIPUR_AREAS.put("Malviya Nagar", new GeoLocation("Malviya Nagar", 26.8530, 75.8052, "Gaurav Tower & WTP Zone"));
        JAIPUR_AREAS.put("Vaishali Nagar", new GeoLocation("Vaishali Nagar", 26.9076, 75.7432, "Queens Road & Amrapali"));
        JAIPUR_AREAS.put("Raja Park", new GeoLocation("Raja Park", 26.8970, 75.8270, "Food Street & Central Jaipur"));
    }

    private DeliveryOrder selectedOrder;
    private double riderProgress = 0.55; // 0.0 to 1.0 animation fraction

    public JaipurDeliveryMapPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(480, 420));
    }

    public void setSelectedOrder(DeliveryOrder order) {
        this.selectedOrder = order;
        if (order != null) {
            if ("Delivered".equalsIgnoreCase(order.getStatus())) {
                riderProgress = 1.0;
            } else if ("Preparing".equalsIgnoreCase(order.getStatus()) || "Order Placed".equalsIgnoreCase(order.getStatus())) {
                riderProgress = 0.05;
            } else {
                riderProgress = 0.60;
            }
        }
        repaint();
    }

    public void advanceRider() {
        if (selectedOrder != null && "Out for Delivery".equalsIgnoreCase(selectedOrder.getStatus())) {
            riderProgress += 0.04;
            if (riderProgress > 0.98) {
                riderProgress = 0.98;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 1. Map Canvas Background (Styled warm cream paper GPS map)
        g2.setColor(new Color(244, 238, 227));
        g2.fillRoundRect(0, 0, width, height, 16, 16);
        g2.setColor(new Color(228, 219, 205));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(0, 0, width - 1, height - 1, 16, 16);

        // Map Grid coordinates (Latitude / Longitude range of Jaipur coverage)
        // Lat: 26.75 (South Sitapura) to 26.93 (North Vaishali)
        // Lng: 75.72 (West Vaishali/Mansarovar) to 75.85 (East Raja Park/Jagatpura)
        double minLat = 26.75;
        double maxLat = 26.93;
        double minLng = 75.72;
        double maxLng = 75.86;

        int pad = 35;
        int mapW = width - (2 * pad);
        int mapH = height - (2 * pad);

        // 2. Draw Simulated Jaipur Road Network (NH48, Tonk Road, JLN Marg, Ring Road)
        g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(235, 224, 206)); // Major roads

        Point pSitapura = toScreen(26.7818, 75.8234, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);
        Point pMansarovar = toScreen(26.8524, 75.7674, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);
        Point pMalviya = toScreen(26.8530, 75.8052, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);
        Point pVaishali = toScreen(26.9076, 75.7432, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);
        Point pRajaPark = toScreen(26.8970, 75.8270, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);

        // Connecting Arteries
        g2.drawLine(pSitapura.x, pSitapura.y, pMalviya.x, pMalviya.y); // Tonk Rd / JLN Marg
        g2.drawLine(pSitapura.x, pSitapura.y, pMansarovar.x, pMansarovar.y); // Vande Mataram Road
        g2.drawLine(pMansarovar.x, pMansarovar.y, pVaishali.x, pVaishali.y); // Ajmer Road / Kings Road
        g2.drawLine(pMalviya.x, pMalviya.y, pRajaPark.x, pRajaPark.y); // JLN to Raja Park
        g2.drawLine(pMansarovar.x, pMansarovar.y, pMalviya.x, pMalviya.y); // B2 Bypass

        // 3. Area Zones Labels & Markers
        for (GeoLocation loc : JAIPUR_AREAS.values()) {
            Point pt = toScreen(loc.lat, loc.lng, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);

            // Radius ring
            g2.setColor(new Color(200, 144, 46, 25));
            g2.fillOval(pt.x - 22, pt.y - 22, 44, 44);
            g2.setColor(new Color(200, 144, 46, 60));
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 3}, 0));
            g2.drawOval(pt.x - 22, pt.y - 22, 44, 44);

            // Area Pin
            g2.setColor(new Color(110, 85, 60));
            g2.fillOval(pt.x - 5, pt.y - 5, 10, 10);
            g2.setColor(Color.WHITE);
            g2.fillOval(pt.x - 2, pt.y - 2, 4, 4);

            // Label
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.setColor(new Color(60, 45, 30));
            FontMetrics fm = g2.getFontMetrics();
            int tx = pt.x - (fm.stringWidth(loc.name) / 2);
            g2.drawString(loc.name, tx, pt.y + 18);
        }

        // 4. Restaurant Hub Marker
        g2.setColor(new Color(46, 158, 91));
        g2.fillOval(pSitapura.x - 9, pSitapura.y - 9, 18, 18);
        g2.setColor(Color.WHITE);
        g2.fillOval(pSitapura.x - 4, pSitapura.y - 4, 8, 8);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        g2.setColor(new Color(30, 120, 65));
        g2.drawString("★ Kitchen Hub", pSitapura.x + 12, pSitapura.y + 4);

        // 5. Active Order Tracking Path & Animated Rider
        if (selectedOrder != null) {
            String destArea = selectedOrder.getArea();
            GeoLocation destLoc = JAIPUR_AREAS.get(destArea);
            if (destLoc == null) {
                destLoc = JAIPUR_AREAS.get("Mansarovar");
            }

            Point destPt = toScreen(destLoc.lat, destLoc.lng, minLat, maxLat, minLng, maxLng, pad, mapW, mapH);

            // Draw route dashed line with gold glow
            g2.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(200, 144, 46, 200));
            g2.drawLine(pSitapura.x, pSitapura.y, destPt.x, destPt.y);

            // Destination Pin
            g2.setColor(new Color(224, 79, 74));
            g2.fillOval(destPt.x - 8, destPt.y - 8, 16, 16);
            g2.setColor(Color.WHITE);
            g2.fillOval(destPt.x - 3, destPt.y - 3, 6, 6);

            // Animated Delivery Rider Location
            int riderX = (int) (pSitapura.x + (destPt.x - pSitapura.x) * riderProgress);
            int riderY = (int) (pSitapura.y + (destPt.y - pSitapura.y) * riderProgress);

            // Rider Icon / Circle
            g2.setColor(new Color(76, 141, 246));
            g2.fillOval(riderX - 11, riderY - 11, 22, 22);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawOval(riderX - 11, riderY - 11, 22, 22);

            // Rider Label / Status Pill
            String riderText = "🛵 Rider: " + selectedOrder.getStatus();
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            FontMetrics rfm = g2.getFontMetrics();
            int rw = rfm.stringWidth(riderText) + 12;
            int rx = riderX - (rw / 2);
            int ry = riderY - 26;

            g2.setColor(new Color(40, 30, 20, 220));
            g2.fillRoundRect(rx, ry, rw, 18, 9, 9);
            g2.setColor(Color.WHITE);
            g2.drawString(riderText, rx + 6, ry + 13);
        }

        // 6. Map Legend Overlay (Top Left)
        g2.setColor(new Color(255, 255, 255, 210));
        g2.fillRoundRect(14, 14, 210, 58, 10, 10);
        g2.setColor(new Color(220, 210, 195));
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRoundRect(14, 14, 210, 58, 10, 10);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.setColor(new Color(43, 27, 18));
        g2.drawString("Jaipur Express Delivery Hub", 22, 32);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        g2.setColor(new Color(110, 95, 80));
        g2.drawString("Coverage: Sitapura • Mansarovar • Malviya", 22, 47);
        g2.drawString("Nagar • Vaishali Nagar • Raja Park", 22, 60);

        g2.dispose();
    }

    private Point toScreen(double lat, double lng, double minLat, double maxLat, double minLng, double maxLng,
                           int pad, int mapW, int mapH) {
        // Map projection formula:
        // Lat: North is up (inverted Y)
        double fracX = (lng - minLng) / (maxLng - minLng);
        double fracY = (maxLat - lat) / (maxLat - minLat);

        int x = pad + (int) Math.round(fracX * mapW);
        int y = pad + (int) Math.round(fracY * mapH);
        return new Point(x, y);
    }
}

