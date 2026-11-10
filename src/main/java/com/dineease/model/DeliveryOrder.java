package com.dineease.model;

import java.sql.Timestamp;

public class DeliveryOrder {
    private int id;
    private String customerName;
    private String customerPhone;
    private String area; // Sitapura, Mansarovar, Malviya Nagar, Vaishali Nagar, Raja Park
    private String fullAddress;
    private String itemsSummary;
    private double totalAmount;
    private String status; // Order Placed, Preparing, Out for Delivery, Delivered, Cancelled
    private double riderLat;
    private double riderLng;
    private Timestamp createdAt;

    public DeliveryOrder() {
    }

    public DeliveryOrder(int id, String customerName, String customerPhone, String area, String fullAddress,
                         String itemsSummary, double totalAmount, String status, double riderLat, double riderLng, Timestamp createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.area = area;
        this.fullAddress = fullAddress;
        this.itemsSummary = itemsSummary;
        this.totalAmount = totalAmount;
        this.status = status;
        this.riderLat = riderLat;
        this.riderLng = riderLng;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getFullAddress() { return fullAddress; }
    public void setFullAddress(String fullAddress) { this.fullAddress = fullAddress; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getRiderLat() { return riderLat; }
    public void setRiderLat(double riderLat) { this.riderLat = riderLat; }

    public double getRiderLng() { return riderLng; }
    public void setRiderLng(double riderLng) { this.riderLng = riderLng; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

