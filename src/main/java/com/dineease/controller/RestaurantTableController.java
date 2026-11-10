package com.dineease.controller;

import com.dineease.dao.RestaurantTableDAO;
import com.dineease.dao.RestaurantTableDAOImpl;
import com.dineease.model.RestaurantTable;

import java.util.List;

public class RestaurantTableController {

    private RestaurantTableDAO tableDAO;


    // =========================
    // CONSTRUCTOR
    // =========================

    public RestaurantTableController() {

        tableDAO =
                new RestaurantTableDAOImpl();
    }


    // =========================
    // GET ALL TABLES
    // =========================

    public List<RestaurantTable> getAllTables() {

        return tableDAO.getAllTables();
    }


    // =========================
    // ADD TABLE
    // =========================

    public boolean addTable(
            RestaurantTable table
    ) {

        return tableDAO.addTable(
                table
        );
    }


    // =========================
    // UPDATE TABLE
    // =========================

    public boolean updateTable(
            RestaurantTable table
    ) {

        return tableDAO.updateTable(
                table
        );
    }


    // =========================
    // DELETE TABLE
    // =========================

    public boolean deleteTable(
            int tableId
    ) {

        return tableDAO.deleteTable(
                tableId
        );
    }


    // =========================
    // UPDATE TABLE STATUS
    // =========================

    public boolean updateTableStatus(
            int tableId,
            String status
    ) {

        return tableDAO.updateTableStatus(
                tableId,
                status
        );
    }
}