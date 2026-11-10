package com.dineease.ui.components;

import com.dineease.model.SalesData;
import com.dineease.model.SalesPoint;
import com.dineease.ui.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SalesChartPanel extends RoundedPanel {
    private final JComboBox<String> rangeCombo;
    private final JLabel totalLabel;
    private final JLabel percentLabel;
    private final ChartCanvas canvas;
    private Consumer<String> onRangeChanged;

    public SalesChartPanel() {
        super(14, new Color(251, 246, 234), new Color(230, 222, 210), true);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        // Header Panel: Title with icon + Dropdown
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titlePanel.setOpaque(false);
        JLabel icon = new JLabel(IconFactory.barChart(18, new Color(197, 139, 43)));
        JLabel title = new JLabel("Sales Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(new Color(43, 27, 18));
        titlePanel.add(icon);
        titlePanel.add(title);

        rangeCombo = new JComboBox<>(new String[]{"Today", "Last 7 Days", "Last 30 Days", "This Month"});
        rangeCombo.setSelectedItem("Last 7 Days");
        rangeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rangeCombo.setFocusable(false);
        rangeCombo.setBackground(Color.WHITE);
        rangeCombo.setBorder(BorderFactory.createLineBorder(new Color(220, 210, 195)));
        rangeCombo.addActionListener(e -> {
            if (onRangeChanged != null) {
                onRangeChanged.accept((String) rangeCombo.getSelectedItem());
            }
        });

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(rangeCombo, BorderLayout.EAST);

        // Subheader Panel: "Total Sales", big ₹ total, green % change
        JPanel subheaderPanel = new JPanel();
        subheaderPanel.setOpaque(false);
        subheaderPanel.setLayout(new BoxLayout(subheaderPanel, BoxLayout.Y_AXIS));
        subheaderPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel totalSalesSub = new JLabel("Total Sales");
        totalSalesSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        totalSalesSub.setForeground(new Color(110, 95, 80));

        JPanel numbersRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        numbersRow.setOpaque(false);

        totalLabel = new JLabel("₹ 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        totalLabel.setForeground(new Color(43, 27, 18));

        percentLabel = new JLabel("↗ +0%");
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        percentLabel.setForeground(new Color(46, 158, 91));

        numbersRow.add(totalLabel);
        numbersRow.add(percentLabel);

        subheaderPanel.add(totalSalesSub);
        subheaderPanel.add(Box.createVerticalStrut(4));
        subheaderPanel.add(numbersRow);

        JPanel topGroup = new JPanel(new BorderLayout());
        topGroup.setOpaque(false);
        topGroup.add(headerPanel, BorderLayout.NORTH);
        topGroup.add(subheaderPanel, BorderLayout.SOUTH);

        canvas = new ChartCanvas();

        add(topGroup, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    public void setOnRangeChanged(Consumer<String> onRangeChanged) {
        this.onRangeChanged = onRangeChanged;
    }

    public void updateData(SalesData salesData) {
        if (salesData == null) {
            totalLabel.setText("₹ 0");
            percentLabel.setText("↗ +0%");
            canvas.setData(new ArrayList<>());
            return;
        }

        DecimalFormat df = new DecimalFormat("#,##,##0");
        totalLabel.setText("₹ " + df.format(salesData.getTotalSales()));

        double p = salesData.getPercentChange();
        if (p >= 0) {
            percentLabel.setText("↗ +" + String.format("%.0f%%", p));
            percentLabel.setForeground(new Color(46, 158, 91));
        } else {
            percentLabel.setText("↘ " + String.format("%.0f%%", p));
            percentLabel.setForeground(new Color(224, 79, 74));
        }

        canvas.setData(salesData.getPoints());
    }

    private static class ChartCanvas extends JPanel {
        private List<SalesPoint> points = new ArrayList<>();
        private int hoveredIndex = -1;

        public ChartCanvas() {
            setOpaque(false);
            setPreferredSize(new Dimension(500, 210));
            setToolTipText("");

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    checkHover(e.getX(), e.getY());
                }
            });
        }

        public void setData(List<SalesPoint> points) {
            this.points = points != null ? points : new ArrayList<>();
            this.hoveredIndex = -1;
            repaint();
        }

        private void checkHover(int mx, int my) {
            if (points == null || points.isEmpty()) return;
            int left = 48, right = 20, top = 20, bottom = 32;
            int w = getWidth() - left - right;
            int n = points.size();
            int newHover = -1;

            for (int i = 0; i < n; i++) {
                int px = n == 1 ? left + w / 2 : left + (int) ((long) i * w / (n - 1));
                if (Math.abs(mx - px) <= 15) {
                    newHover = i;
                    break;
                }
            }

            if (newHover != hoveredIndex) {
                hoveredIndex = newHover;
                if (hoveredIndex >= 0 && hoveredIndex < points.size()) {
                    SalesPoint sp = points.get(hoveredIndex);
                    DecimalFormat df = new DecimalFormat("#,##,##0");
                    setToolTipText(sp.getLabel() + ": ₹ " + df.format(sp.getAmount()));
                } else {
                    setToolTipText(null);
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

            int left = 48;
            int right = 20;
            int top = 20;
            int bottom = 32;

            int chartW = width - left - right;
            int chartH = height - top - bottom;
            if (chartW <= 0 || chartH <= 0) {
                g2.dispose();
                return;
            }

            // Determine maximum scale
            double maxAmount = 10000;
            for (SalesPoint sp : points) {
                if (sp.getAmount() > maxAmount) {
                    maxAmount = sp.getAmount();
                }
            }
            // Round max up to nice ticks (e.g. 10k, 20k, 30k, 40k)
            int steps = 4;
            double tickStep = Math.ceil((maxAmount / steps) / 1000.0) * 1000.0;
            if (tickStep < 2500) tickStep = 2500;
            double chartMax = tickStep * steps;

            // Draw Y-axis grid lines and labels
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.setColor(new Color(130, 115, 100));

            for (int i = 0; i <= steps; i++) {
                int y = top + (int) ((long) i * chartH / steps);
                // Grid line
                g2.setColor(new Color(235, 226, 215));
                g2.drawLine(left, y, width - right, y);

                // Y label
                double val = chartMax - (i * tickStep);
                String yLabel;
                if (val >= 1000) {
                    yLabel = (int) (val / 1000) + "K";
                } else {
                    yLabel = String.valueOf((int) val);
                }
                g2.setColor(new Color(130, 115, 100));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(yLabel, left - fm.stringWidth(yLabel) - 8, y + 4);
            }

            if (points == null || points.isEmpty()) {
                g2.dispose();
                return;
            }

            int n = points.size();
            Point[] ptCoords = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = n == 1 ? left + chartW / 2 : left + (int) ((long) i * chartW / (n - 1));
                double amt = points.get(i).getAmount();
                int y = top + chartH - (int) Math.round((amt / chartMax) * chartH);
                ptCoords[i] = new Point(x, y);
            }

            // Gradient area under the line
            GeneralPath areaPath = new GeneralPath();
            areaPath.moveTo(ptCoords[0].x, top + chartH);
            for (int i = 0; i < n; i++) {
                areaPath.lineTo(ptCoords[i].x, ptCoords[i].y);
            }
            areaPath.lineTo(ptCoords[n - 1].x, top + chartH);
            areaPath.closePath();

            GradientPaint gp = new GradientPaint(
                    0, top, new Color(200, 144, 46, 90),
                    0, top + chartH, new Color(200, 144, 46, 5)
            );
            g2.setPaint(gp);
            g2.fill(areaPath);

            // Gold line connecting markers
            g2.setColor(new Color(200, 144, 46));
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            GeneralPath linePath = new GeneralPath();
            linePath.moveTo(ptCoords[0].x, ptCoords[0].y);
            for (int i = 1; i < n; i++) {
                linePath.lineTo(ptCoords[i].x, ptCoords[i].y);
            }
            g2.draw(linePath);

            // X-axis labels and points
            for (int i = 0; i < n; i++) {
                Point p = ptCoords[i];
                // Dot
                g2.setColor(Color.WHITE);
                g2.fillOval(p.x - 4, p.y - 4, 8, 8);
                g2.setColor(new Color(200, 144, 46));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawOval(p.x - 4, p.y - 4, 8, 8);

                if (i == hoveredIndex) {
                    g2.setColor(new Color(200, 144, 46, 60));
                    g2.fillOval(p.x - 7, p.y - 7, 14, 14);
                }

                // X label
                String xLabel = points.get(i).getLabel();
                g2.setColor(new Color(120, 105, 90));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                FontMetrics fm = g2.getFontMetrics();
                int lx = p.x - (fm.stringWidth(xLabel) / 2);
                g2.drawString(xLabel, lx, top + chartH + 18);
            }

            g2.dispose();
        }
    }
}

