package com.dineease.controller;

import com.dineease.dao.TableDAO;
import com.dineease.dao.TableDAOImpl;
import com.dineease.model.RestaurantTable;

import java.util.List;

public class TableController {

    private TableDAO tableDAO;


    // =========================
    // CONSTRUCTOR
    // =========================

    public TableController() {

        tableDAO =
                new TableDAOImpl();
    }


    // =========================
    // GET ALL TABLES
    // =========================

    public List<RestaurantTable> getAllTables() {

        return tableDAO.getAllTables();
    }


    // =========================
    // GET AVAILABLE TABLES
    // =========================

    public List<RestaurantTable> getAvailableTables() {

        return tableDAO.getAvailableTables();
    }


    // =========================
    // GET TABLE BY ID
    // =========================

    public RestaurantTable getTableById(
            int tableId
    ) {

        return tableDAO.getTableById(
                tableId
        );
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