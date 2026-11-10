package com.dineease.model;

import java.sql.Timestamp;

public class InventoryItem {
    private int id;
    private String name;
    private double quantity;
    private String unit = "kg";
    private double reorderLevel;
    private Timestamp createdAt;

    // Extra legacy fields
    private String itemName;
    private String category = "General";
    private String status = "In Stock";
    private double minimumStock;
    private String supplierName = "";
    private double costPerUnit = 0.0;

    public InventoryItem() {
    }

    public InventoryItem(
            String itemName,
            String category,
            double quantity,
            String unit,
            double reorderLevel,
            String status,
            double minimumStock,
            String supplierName,
            double costPerUnit
    ) {
        this.itemName = itemName;
        this.name = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.status = status;
        this.minimumStock = minimumStock;
        this.supplierName = supplierName;
        this.costPerUnit = costPerUnit;
    }

    public InventoryItem(int id, String name, double quantity, String unit, double reorderLevel, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.itemName = name;
        this.quantity = quantity;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.createdAt = createdAt;
        this.status = quantity <= reorderLevel ? "Low Stock" : "In Stock";
    }

    public InventoryItem(String name, double quantity, String unit, double reorderLevel) {
        this.name = name;
        this.itemName = name;
        this.quantity = quantity;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.status = quantity <= reorderLevel ? "Low Stock" : "In Stock";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name != null ? name : itemName; }
    public void setName(String name) { this.name = name; this.itemName = name; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.status = quantity <= reorderLevel ? "Low Stock" : "In Stock";
    }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public double getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(double reorderLevel) {
        this.reorderLevel = reorderLevel;
        this.minimumStock = reorderLevel;
        this.status = quantity <= reorderLevel ? "Low Stock" : "In Stock";
    }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // Compatibility methods
    public int getInventoryId() { return id; }
    public void setInventoryId(int id) { this.id = id; }

    public String getItemName() { return itemName != null ? itemName : name; }
    public void setItemName(String name) { this.itemName = name; this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return quantity <= reorderLevel ? "Low Stock" : (status != null ? status : "In Stock"); }
    public void setStatus(String status) { this.status = status; }

    public double getMinimumStock() { return minimumStock > 0 ? minimumStock : reorderLevel; }
    public void setMinimumStock(double minimumStock) { this.minimumStock = minimumStock; this.reorderLevel = minimumStock; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String name) { this.supplierName = name; }

    public double getCostPerUnit() { return costPerUnit; }
    public void setCostPerUnit(double cost) { this.costPerUnit = cost; }

    @Override
    public String toString() {
        return (name != null ? name : itemName) + " (" + quantity + " " + unit + ")";
    }
}
