package com.dineease.model;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private double price;

    // Extra legacy fields
    private int orderItemId;
    private int itemId;
    private String itemName;
    private double totalPrice;

    public OrderItem() {
    }

    public OrderItem(int id, int orderId, int menuItemId, int quantity, double price) {
        this.id = id;
        this.orderItemId = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.itemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = quantity * price;
    }

    public OrderItem(int orderId, int menuItemId, int quantity, double price) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.itemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = quantity * price;
    }

    public int getId() { return id > 0 ? id : orderItemId; }
    public void setId(int id) { this.id = id; this.orderItemId = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getMenuItemId() { return menuItemId > 0 ? menuItemId : itemId; }
    public void setMenuItemId(int menuItemId) { this.menuItemId = menuItemId; this.itemId = menuItemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * price;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = quantity * price;
    }

    // Compatibility methods
    public int getOrderItemId() { return getId(); }
    public void setOrderItemId(int orderItemId) { setId(orderItemId); }

    public int getItemId() { return getMenuItemId(); }
    public void setItemId(int itemId) { setMenuItemId(itemId); }

    public String getItemName() { return itemName != null ? itemName : ("Item #" + getItemId()); }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getTotalPrice() { return totalPrice > 0 ? totalPrice : (quantity * price); }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
