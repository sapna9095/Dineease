package com.dineease.dao;

import com.dineease.model.RestaurantTable;

import java.util.List;

public interface RestaurantTableDAO {

    // =========================
    // GET ALL TABLES
    // =========================

    List<RestaurantTable> getAllTables();


    // =========================
    // ADD TABLE
    // =========================

    boolean addTable(
            RestaurantTable table
    );


    // =========================
    // UPDATE TABLE
    // =========================

    boolean updateTable(
            RestaurantTable table
    );


    // =========================
    // DELETE TABLE
    // =========================

    boolean deleteTable(
            int tableId
    );


    // =========================
    // UPDATE TABLE STATUS
    // =========================

    boolean updateTableStatus(
            int tableId,
            String status
    );
}