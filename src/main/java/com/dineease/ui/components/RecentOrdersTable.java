package com.dineease.ui.components;

import com.dineease.model.OrderRow;
import com.dineease.ui.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;

public class RecentOrdersTable extends RoundedPanel {
    private final JPanel rowsPanel;
    private Runnable onViewAllAction;

    public RecentOrdersTable() {
        super(14, new Color(251, 246, 234), new Color(230, 222, 210), true);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(18, 20, 16, 20));

        // Header: Title with icon + "View All" link
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new FlowLayout(FlowLayout.LEFT, 8, 0) == null ? new JPanel() : new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titlePanel.setOpaque(false);
        JLabel icon = new JLabel(IconFactory.creditCard(18, new Color(197, 139, 43)));
        JLabel title = new JLabel("Recent Orders");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(new Color(43, 27, 18));
        titlePanel.add(icon);
        titlePanel.add(title);

        JLabel viewAll = new JLabel("View All");
        viewAll.setFont(new Font("Segoe UI", Font.BOLD, 13));
        viewAll.setForeground(new Color(197, 139, 43));
        viewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onViewAllAction != null) {
                    onViewAllAction.run();
                }
            }
        });

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(viewAll, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Body: Table header + rows
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        // Table Header
        JPanel thead = new JPanel(new GridLayout(1, 5, 8, 0));
        thead.setBackground(new Color(245, 238, 224));
        thead.setPreferredSize(new Dimension(0, 32));
        thead.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 222, 210)));

        String[] headers = {"Order ID", "Table No.", "Time", "Amount", "Status"};
        for (String h : headers) {
            JLabel lbl = new JLabel(h);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(new Color(90, 75, 60));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            thead.add(lbl);
        }

        tableContainer.add(thead, BorderLayout.NORTH);

        rowsPanel = new JPanel();
        rowsPanel.setOpaque(false);
        rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
        tableContainer.add(rowsPanel, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);
    }

    public void setOnViewAllAction(Runnable action) {
        this.onViewAllAction = action;
    }

    public void updateData(List<OrderRow> orders) {
        rowsPanel.removeAll();
        if (orders == null || orders.isEmpty()) {
            JPanel emptyRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 24));
            emptyRow.setOpaque(false);
            JLabel empty = new JLabel("No recent orders");
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            empty.setForeground(new Color(140, 125, 110));
            emptyRow.add(empty);
            rowsPanel.add(emptyRow);
        } else {
            DecimalFormat df = new DecimalFormat("#,##,##0");
            for (OrderRow row : orders) {
                JPanel r = new JPanel(new GridLayout(1, 5, 8, 0));
                r.setOpaque(false);
                r.setPreferredSize(new Dimension(0, 34));
                r.setMaximumSize(new Dimension(Short.MAX_VALUE, 34));
                r.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 232, 222)));

                JLabel idLbl = makeCell(row.getOrderIdText());
                JLabel tblLbl = makeCell(row.getTableText());
                JLabel timeLbl = makeCell(row.getTimeText());
                JLabel amtLbl = makeCell("₹ " + df.format(row.getAmount()));

                JPanel statusCell = new JPanel(new GridBagLayout());
                statusCell.setOpaque(false);
                StatusBadge badge = new StatusBadge(row.getStatus());
                statusCell.add(badge);

                r.add(idLbl);
                r.add(tblLbl);
                r.add(timeLbl);
                r.add(amtLbl);
                r.add(statusCell);

                rowsPanel.add(r);
            }
        }
        rowsPanel.revalidate();
        rowsPanel.repaint();
    }

    private JLabel makeCell(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(new Color(43, 27, 18));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}

