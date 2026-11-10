package com.dineease.dao;

import com.dineease.model.RestaurantTable;
import com.dineease.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableDAOImpl implements TableDAO {

    // =========================
    // GET ALL TABLES
    // =========================

    @Override
    public List<RestaurantTable> getAllTables() {

        List<RestaurantTable> tables =
                new ArrayList<>();

        String sql =
                "SELECT table_id, table_number, capacity, " +
                        "location, status " +
                        "FROM restaurant_tables " +
                        "ORDER BY table_number ASC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                RestaurantTable table =
                        new RestaurantTable(
                                rs.getInt("table_number"),
                                rs.getInt("capacity"),
                                rs.getString("location"),
                                rs.getString("status")
                        );

                table.setTableId(
                        rs.getInt("table_id")
                );

                tables.add(table);
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return tables;
    }


    // =========================
    // GET AVAILABLE TABLES
    // =========================

    @Override
    public List<RestaurantTable> getAvailableTables() {

        List<RestaurantTable> tables =
                new ArrayList<>();

        String sql =
                "SELECT table_id, table_number, capacity, " +
                        "location, status " +
                        "FROM restaurant_tables " +
                        "WHERE status = 'Available' " +
                        "ORDER BY table_number ASC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                RestaurantTable table =
                        new RestaurantTable(
                                rs.getInt("table_number"),
                                rs.getInt("capacity"),
                                rs.getString("location"),
                                rs.getString("status")
                        );

                table.setTableId(
                        rs.getInt("table_id")
                );

                tables.add(table);
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return tables;
    }


    // =========================
    // GET TABLE BY ID
    // =========================

    @Override
    public RestaurantTable getTableById(
            int tableId
    ) {

        String sql =
                "SELECT table_id, table_number, capacity, " +
                        "location, status " +
                        "FROM restaurant_tables " +
                        "WHERE table_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    tableId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                RestaurantTable table =
                        new RestaurantTable(
                                rs.getInt("table_number"),
                                rs.getInt("capacity"),
                                rs.getString("location"),
                                rs.getString("status")
                        );

                table.setTableId(
                        rs.getInt("table_id")
                );

                return table;
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================
    // ADD TABLE
    // =========================

    @Override
    public boolean addTable(
            RestaurantTable table
    ) {

        String sql =
                "INSERT INTO restaurant_tables " +
                        "(table_number, capacity, location, status) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    table.getTableNumber()
            );

            ps.setInt(
                    2,
                    table.getCapacity()
            );

            ps.setString(
                    3,
                    table.getLocation()
            );

            ps.setString(
                    4,
                    table.getStatus()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // UPDATE TABLE
    // =========================

    @Override
    public boolean updateTable(
            RestaurantTable table
    ) {

        String sql =
                "UPDATE restaurant_tables SET " +
                        "table_number = ?, " +
                        "capacity = ?, " +
                        "location = ?, " +
                        "status = ? " +
                        "WHERE table_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    table.getTableNumber()
            );

            ps.setInt(
                    2,
                    table.getCapacity()
            );

            ps.setString(
                    3,
                    table.getLocation()
            );

            ps.setString(
                    4,
                    table.getStatus()
            );

            ps.setInt(
                    5,
                    table.getTableId()
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // DELETE TABLE
    // =========================

    @Override
    public boolean deleteTable(
            int tableId
    ) {

        String sql =
                "DELETE FROM restaurant_tables " +
                        "WHERE table_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    tableId
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================
    // UPDATE TABLE STATUS
    // =========================

    @Override
    public boolean updateTableStatus(
            int tableId,
            String status
    ) {

        String sql =
                "UPDATE restaurant_tables SET " +
                        "status = ? " +
                        "WHERE table_id = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    tableId
            );

            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}