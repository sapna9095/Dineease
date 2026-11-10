package com.dineease.model;

public class BillItem {

    private int billItemId;
    private int billId;
    private int menuItemId;

    private String itemName;

    private int quantity;
    private double price;
    private double subtotal;


    // =========================
    // GETTERS
    // =========================

    public int getBillItemId() {
        return billItemId;
    }

    public int getBillId() {
        return billId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }


    // =========================
    // SETTERS
    // =========================

    public void setBillItemId(int billItemId) {
        this.billItemId = billItemId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}