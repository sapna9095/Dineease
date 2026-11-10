package com.dineease.dao;

import com.dineease.model.InventoryItem;

import java.util.List;

public interface InventoryDAO {

    // =========================
    // GET ALL INVENTORY ITEMS
    // =========================

    List<InventoryItem> getAllInventoryItems();


    // =========================
    // ADD INVENTORY ITEM
    // =========================

    boolean addInventoryItem(
            InventoryItem item
    );


    // =========================
    // UPDATE INVENTORY ITEM
    // =========================

    boolean updateInventoryItem(
            InventoryItem item
    );


    // =========================
    // DELETE INVENTORY ITEM
    // =========================

    boolean deleteInventoryItem(
            int inventoryId
    );


    // =========================
    // UPDATE STOCK STATUS
    // =========================

    boolean updateStockStatus(
            int inventoryId,
            String status
    );
}