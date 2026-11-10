package com.dineease.model;

import java.awt.Color;

public class OrderStatusCount {
    private final String status;
    private final int count;
    private final Color color;

    public OrderStatusCount(String status, int count, Color color) {
        this.status = status;
        this.count = count;
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

    public Color getColor() {
        return color;
    }
}
