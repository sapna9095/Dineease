package com.dineease.ui.components;

import com.dineease.model.OrderStatusCount;
import com.dineease.ui.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class DonutChartPanel extends RoundedPanel {
    private final JPanel listPanel;
    private final DonutCanvas donutCanvas;
    private final List<JLabel> countLabels = new ArrayList<>();

    public DonutChartPanel() {
        super(14, new Color(251, 246, 234), new Color(230, 222, 210), true);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        // Title Header
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titlePanel.setOpaque(false);
        JLabel icon = new JLabel(IconFactory.clipboardList(18, new Color(197, 139, 43)));
        JLabel title = new JLabel("Order Status");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(new Color(43, 27, 18));
        titlePanel.add(icon);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Body: Left list + Right Donut
        JPanel bodyPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        String[] defaultStatuses = {"Pending", "Preparing", "Ready", "Served", "Cancelled"};
        Color[] defaultColors = {
                new Color(237, 170, 58),
                new Color(76, 141, 246),
                new Color(46, 158, 91),
                new Color(124, 77, 190),
                new Color(224, 79, 74)
        };

        for (int i = 0; i < defaultStatuses.length; i++) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setPreferredSize(new Dimension(160, 32));
            row.setMaximumSize(new Dimension(Short.MAX_VALUE, 32));

            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
            left.setOpaque(false);

            final Color dotColor = defaultColors[i];
            JLabel dot = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(dotColor);
                    g2.fillOval(1, 1, getWidth() - 2, getHeight() - 2);
                    g2.dispose();
                }
            };
            dot.setPreferredSize(new Dimension(10, 10));

            JLabel label = new JLabel(defaultStatuses[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(new Color(60, 48, 38));
            left.add(dot);
            left.add(label);

            JLabel count = new JLabel("0");
            count.setFont(new Font("Segoe UI", Font.BOLD, 14));
            count.setForeground(new Color(43, 27, 18));
            count.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            countLabels.add(count);
            row.add(left, BorderLayout.WEST);
            row.add(count, BorderLayout.EAST);
            listPanel.add(row);
        }

        donutCanvas = new DonutCanvas();

        bodyPanel.add(listPanel);
        bodyPanel.add(donutCanvas);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public void updateData(List<OrderStatusCount> statuses) {
        if (statuses == null) return;
        int total = 0;
        for (int i = 0; i < statuses.size() && i < countLabels.size(); i++) {
            OrderStatusCount item = statuses.get(i);
            countLabels.get(i).setText(String.valueOf(item.getCount()));
            total += item.getCount();
        }
        donutCanvas.setData(statuses, total);
    }

    private static class DonutCanvas extends JPanel {
        private List<OrderStatusCount> data = new ArrayList<>();
        private int totalOrders = 0;

        public DonutCanvas() {
            setOpaque(false);
            setPreferredSize(new Dimension(200, 200));
        }

        public void setData(List<OrderStatusCount> data, int total) {
            this.data = data != null ? data : new ArrayList<>();
            this.totalOrders = total;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int size = Math.min(width, height) - 20;
            if (size <= 0) {
                g2.dispose();
                return;
            }

            int x = (width - size) / 2;
            int y = (height - size) / 2;
            int thickness = 32;

            if (totalOrders == 0) {
                // Gray empty ring
                g2.setColor(new Color(225, 218, 206));
                g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                int inset = thickness / 2;
                g2.drawOval(x + inset, y + inset, size - thickness, size - thickness);
            } else {
                double currentAngle = 90.0;
                double gapAngle = 4.0; // Segment gap
                int numSegments = 0;
                for (OrderStatusCount sc : data) {
                    if (sc.getCount() > 0) numSegments++;
                }
                if (numSegments <= 1) gapAngle = 0.0;

                double totalGap = gapAngle * numSegments;
                double availableAngle = 360.0 - totalGap;

                for (OrderStatusCount sc : data) {
                    if (sc.getCount() <= 0) continue;
                    double sweep = (sc.getCount() / (double) totalOrders) * availableAngle;

                    Shape outerArc = new Arc2D.Double(x, y, size, size, currentAngle, -sweep, Arc2D.PIE);
                    Shape innerHole = new Ellipse2D.Double(x + thickness, y + thickness, size - (2 * thickness), size - (2 * thickness));

                    Area donutPiece = new Area(outerArc);
                    donutPiece.subtract(new Area(innerHole));

                    g2.setColor(sc.getColor());
                    g2.fill(donutPiece);

                    currentAngle -= (sweep + gapAngle);
                }
            }

            // Center text: "Total Orders" and total number
            g2.setColor(new Color(120, 105, 90));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();
            String label = "Total Orders";
            int lx = (width - fm.stringWidth(label)) / 2;
            int ly = height / 2 - 4;
            g2.drawString(label, lx, ly);

            g2.setColor(new Color(43, 27, 18));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
            fm = g2.getFontMetrics();
            String num = String.valueOf(totalOrders);
            int nx = (width - fm.stringWidth(num)) / 2;
            int ny = ly + fm.getAscent() + 6;
            g2.drawString(num, nx, ny);

            g2.dispose();
        }
    }
}

