package com.dineease.model;

import java.sql.Timestamp;

public class MenuItem {
    private int id;
    private String name;
    private String category;
    private double price;
    private boolean available = true;
    private Timestamp createdAt;

    // Extra legacy fields
    private String itemName;
    private String description;
    private String status = "Available";
    private String imagePath;

    public MenuItem() {
    }

    public MenuItem(String itemName, String category, double price, String description, String status) {
        this.itemName = itemName;
        this.name = itemName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.status = status;
        this.available = !"Unavailable".equalsIgnoreCase(status);
    }

    public MenuItem(int id, String name, String category, double price, boolean available, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.itemName = name;
        this.category = category;
        this.price = price;
        this.available = available;
        this.status = available ? "Available" : "Unavailable";
        this.createdAt = createdAt;
    }

    public MenuItem(String name, String category, double price, boolean available) {
        this.name = name;
        this.itemName = name;
        this.category = category;
        this.price = price;
        this.available = available;
        this.status = available ? "Available" : "Unavailable";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name != null ? name : itemName; }
    public void setName(String name) { this.name = name; this.itemName = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) {
        this.available = available;
        this.status = available ? "Available" : "Unavailable";
    }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // Compatibility methods
    public int getItemId() { return id; }
    public void setItemId(int id) { this.id = id; }

    public String getItemName() { return getItemNameSafe(); }
    public void setItemName(String name) { this.itemName = name; this.name = name; }
    private String getItemNameSafe() { return itemName != null ? itemName : name; }

    public String getStatus() { return status != null ? status : (available ? "Available" : "Unavailable"); }
    public void setStatus(String status) {
        this.status = status;
        this.available = !"Unavailable".equalsIgnoreCase(status);
    }

    public String getDescription() { return description != null ? description : ""; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String path) { this.imagePath = path; }

    @Override
    public String toString() {
        return getName() + " (₹ " + String.format("%.2f", price) + ")";
    }
}
