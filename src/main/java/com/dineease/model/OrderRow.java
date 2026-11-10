package com.dineease.model;

import java.time.LocalDateTime;

public class OrderRow {
    private final int id;
    private final String orderIdText; // e.g. "#1025"
    private final String tableText;   // e.g. "Table 4"
    private final String timeText;    // e.g. "10:12 AM"
    private final double amount;
    private final String status;      // SERVED, READY, PREPARING, PENDING, CANCELLED
    private final LocalDateTime createdAt;

    public OrderRow(int id, String orderIdText, String tableText, String timeText, double amount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.orderIdText = orderIdText;
        this.tableText = tableText;
        this.timeText = timeText;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getOrderIdText() {
        return orderIdText;
    }

    public String getTableText() {
        return tableText;
    }

    public String getTimeText() {
        return timeText;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
