package com.dineease.model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int tableId;
    private String status; // PENDING, PREPARING, READY, SERVED, CANCELLED
    private double totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Order() {
    }

    public Order(int id, int tableId, String status, double totalAmount, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.tableId = tableId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(int tableId, String status, double totalAmount) {
        this.tableId = tableId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Compatibility methods
    public int getOrderId() {
        return id;
    }

    public void setOrderId(int id) {
        this.id = id;
    }

    public Timestamp getOrderDate() {
        return createdAt;
    }

    public void setOrderDate(Timestamp date) {
        this.createdAt = date;
    }

    public String getOrderStatus() {
        return status;
    }

    public void setOrderStatus(String status) {
        this.status = status;
    }

    public double getSubtotal() {
        return totalAmount;
    }

    public void setSubtotal(double subtotal) {
    }

    public double getTax() {
        return 0.0;
    }

    public void setTax(double tax) {
    }

    @Override
    public String toString() {
        return "#" + id + " - " + status + " (₹ " + String.format("%.2f", totalAmount) + ")";
    }
}
