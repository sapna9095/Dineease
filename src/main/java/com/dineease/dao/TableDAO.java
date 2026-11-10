package com.dineease.dao;

import java.util.List;

import com.dineease.model.RestaurantTable;

public interface TableDAO {

    // =========================
    // GET ALL TABLES
    // =========================

    List<RestaurantTable> getAllTables();


    // =========================
    // GET AVAILABLE TABLES
    // =========================

    List<RestaurantTable> getAvailableTables();


    // =========================
    // GET TABLE BY ID
    // =========================

    RestaurantTable getTableById(
            int tableId
    );


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