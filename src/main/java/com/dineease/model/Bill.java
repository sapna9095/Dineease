package com.dineease.model;

import java.sql.Timestamp;

public class Bill {
    private int id;
    private int orderId;
    private double totalAmount;
    private String paymentStatus;
    private Timestamp paidAt;

    public Bill() {
    }

    public Bill(int id, int orderId, double totalAmount, String paymentStatus, Timestamp paidAt) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }

    public Bill(int orderId, double totalAmount, String paymentStatus) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Timestamp paidAt) {
        this.paidAt = paidAt;
    }

    // Compatibility methods
    public int getBillId() {
        return id;
    }

    public void setBillId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return orderId;
    }

    public void setTableNumber(int num) {
    }

    public double getSubtotal() {
        return totalAmount;
    }

    public void setSubtotal(double amt) {
    }

    public double getTax() {
        return 0;
    }

    public void setTax(double tax) {
    }

    public double getDiscount() {
        return 0;
    }

    public void setDiscount(double discount) {
    }

    public String getPaymentMethod() {
        return "CASH";
    }

    public void setPaymentMethod(String method) {
    }

    public String getStatus() {
        return paymentStatus;
    }

    public void setStatus(String status) {
        this.paymentStatus = status;
    }

    public Timestamp getBillDate() {
        return paidAt;
    }

    public void setBillDate(Timestamp date) {
        this.paidAt = date;
    }

    @Override
    public String toString() {
        return "Bill #" + id + " - Order #" + orderId + " - ₹ " + String.format("%.2f", totalAmount);
    }
}
