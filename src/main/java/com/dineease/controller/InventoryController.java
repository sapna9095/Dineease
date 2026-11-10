package com.dineease.controller;

import com.dineease.dao.InventoryDAO;
import com.dineease.dao.InventoryDAOImpl;
import com.dineease.model.InventoryItem;

import java.util.List;

public class InventoryController {

    private InventoryDAO inventoryDAO;


    // =========================
    // CONSTRUCTOR
    // =========================

    public InventoryController() {

        inventoryDAO =
                new InventoryDAOImpl();
    }


    // =========================
    // GET ALL INVENTORY
    // =========================

    public List<InventoryItem> getAllInventoryItems() {

        return inventoryDAO.getAllInventoryItems();
    }


    // =========================
    // ADD INVENTORY ITEM
    // =========================

    public boolean addInventoryItem(
            InventoryItem item
    ) {

        return inventoryDAO.addInventoryItem(
                item
        );
    }


    // =========================
    // UPDATE INVENTORY ITEM
    // =========================

    public boolean updateInventoryItem(
            InventoryItem item
    ) {

        return inventoryDAO.updateInventoryItem(
                item
        );
    }


    // =========================
    // DELETE INVENTORY ITEM
    // =========================

    public boolean deleteInventoryItem(
            int inventoryId
    ) {

        return inventoryDAO.deleteInventoryItem(
                inventoryId
        );
    }


    // =========================
    // UPDATE STOCK STATUS
    // =========================

    public boolean updateStockStatus(
            int inventoryId,
            String status
    ) {

        return inventoryDAO.updateStockStatus(
                inventoryId,
                status
        );
    }
}