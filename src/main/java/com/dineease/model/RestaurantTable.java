package com.dineease.model;

public class RestaurantTable {
    private int id;
    private int tableNumber;
    private int capacity;
    private String status; // AVAILABLE, OCCUPIED, RESERVED

    public RestaurantTable() {
    }

    public RestaurantTable(int id, int tableNumber, int capacity, String status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public RestaurantTable(int tableNumber, int capacity, String status) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public RestaurantTable(int tableNumber, int capacity, String location, String status) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Compatibility methods
    public int getTableId() {
        return id;
    }

    public void setTableId(int tableId) {
        this.id = tableId;
    }

    public String getLocation() {
        return "Indoor";
    }

    public void setLocation(String location) {
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + " - " + status;
    }
}
