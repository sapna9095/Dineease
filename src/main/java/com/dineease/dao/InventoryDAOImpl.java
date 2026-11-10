
        package com.dineease.dao;

import com.dineease.model.InventoryItem;
import com.dineease.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    // =========================
    // GET ALL INVENTORY ITEMS
    // =========================

    @Override
    public List<InventoryItem> getAllInventoryItems() {

        List<InventoryItem> inventoryItems =
                new ArrayList<>();

        String sql =
                "SELECT inventory_id, item_name, category, quantity, " +
                        "unit, reorder_level, status, minimum_stock, " +
                        "supplier_name, cost_per_unit " +
                        "FROM inventory " +
                        "ORDER BY inventory_id DESC";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                InventoryItem item =
                        new InventoryItem(
                                rs.getString("item_name"),
                                rs.getString("category"),
                                rs.getDouble("quantity"),
                                rs.getString("unit"),
                                rs.getDouble("reorder_level"),
                                rs.getString("status"),
                                rs.getDouble("minimum_stock"),
                                rs.getString("supplier_name"),
                                rs.getDouble("cost_per_unit")
                        );


                item.setInventoryId(
                        rs.getInt("inventory_id")
                );


                inventoryItems.add(item);
            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return inventoryItems;
    }


    // =========================
    // ADD INVENTORY ITEM
    // =========================

    @Override
    public boolean addInventoryItem(
            InventoryItem item
    ) {

        String sql =
                "INSERT INTO inventory " +
                        "(item_name, category, quantity, unit, " +
                        "reorder_level, status, minimum_stock, " +
                        "supplier_name, cost_per_unit) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    item.getItemName()
            );

            ps.setString(
                    2,
                    item.getCategory()
            );

            ps.setDouble(
                    3,
                    item.getQuantity()
            );

            ps.setString(
                    4,
                    item.getUnit()
            );

            ps.setDouble(
                    5,
                    item.getReorderLevel()
            );

            ps.setString(
                    6,
                    item.getStatus()
            );

            ps.setDouble(
                    7,
                    item.getMinimumStock()
            );

            ps.setString(
                    8,
                    item.getSupplierName()
            );

            ps.setDouble(
                    9,
                    item.getCostPerUnit()
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }


    // =========================
    // UPDATE INVENTORY ITEM
    // =========================

    @Override
    public boolean updateInventoryItem(
            InventoryItem item
    ) {

        String sql =
                "UPDATE inventory SET " +
                        "item_name=?, " +
                        "category=?, " +
                        "quantity=?, " +
                        "unit=?, " +
                        "reorder_level=?, " +
                        "status=?, " +
                        "minimum_stock=?, " +
                        "supplier_name=?, " +
                        "cost_per_unit=? " +
                        "WHERE inventory_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    item.getItemName()
            );

            ps.setString(
                    2,
                    item.getCategory()
            );

            ps.setDouble(
                    3,
                    item.getQuantity()
            );

            ps.setString(
                    4,
                    item.getUnit()
            );

            ps.setDouble(
                    5,
                    item.getReorderLevel()
            );

            ps.setString(
                    6,
                    item.getStatus()
            );

            ps.setDouble(
                    7,
                    item.getMinimumStock()
            );

            ps.setString(
                    8,
                    item.getSupplierName()
            );

            ps.setDouble(
                    9,
                    item.getCostPerUnit()
            );

            ps.setInt(
                    10,
                    item.getInventoryId()
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }


    // =========================
    // DELETE INVENTORY ITEM
    // =========================

    @Override
    public boolean deleteInventoryItem(
            int inventoryId
    ) {

        String sql =
                "DELETE FROM inventory " +
                        "WHERE inventory_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    inventoryId
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }


    // =========================
    // UPDATE STOCK STATUS
    // =========================

    @Override
    public boolean updateStockStatus(
            int inventoryId,
            String status
    ) {

        String sql =
                "UPDATE inventory SET " +
                        "status=? " +
                        "WHERE inventory_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    inventoryId
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }

}

