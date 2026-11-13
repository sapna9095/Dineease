package com.dineease.dao;

import com.dineease.model.MenuItem;
import com.dineease.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    // =========================
    // GET ALL MENU ITEMS
    // =========================
    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MenuItem item = new MenuItem();
                
                // Read item_id
                try {
                    item.setItemId(rs.getInt("item_id"));
                } catch (SQLException e) {
                    item.setItemId(rs.getInt("id"));
                }

                // Read item_name
                try {
                    item.setItemName(rs.getString("item_name"));
                } catch (SQLException e) {
                    item.setItemName(rs.getString("name"));
                }

                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));

                try {
                    item.setDescription(rs.getString("description"));
                } catch (SQLException ignored) {}

                try {
                    item.setStatus(rs.getString("status"));
                } catch (SQLException e) {
                    try {
                        item.setStatus(rs.getBoolean("available") ? "Available" : "Unavailable");
                    } catch (SQLException ignored) {}
                }

                try {
                    item.setImagePath(rs.getString("image_path"));
                } catch (SQLException ignored) {}

                menuItems.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    // =========================
    // ADD MENU ITEM
    // =========================
    @Override
    public boolean addMenuItem(MenuItem menuItem) {
        String sql = "INSERT INTO menu_items (item_name, category, price, description, status, image_path) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, menuItem.getItemName());
            ps.setString(2, menuItem.getCategory());
            ps.setDouble(3, menuItem.getPrice());
            ps.setString(4, menuItem.getDescription() != null ? menuItem.getDescription() : "");
            ps.setString(5, menuItem.getStatus() != null ? menuItem.getStatus() : "Available");
            ps.setString(6, menuItem.getImagePath() != null ? menuItem.getImagePath() : "");

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE MENU ITEM
    // =========================
    @Override
    public boolean updateMenuItem(MenuItem menuItem) {
        String sql = "UPDATE menu_items SET item_name=?, category=?, price=?, description=?, status=?, image_path=? " +
                     "WHERE item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, menuItem.getItemName());
            ps.setString(2, menuItem.getCategory());
            ps.setDouble(3, menuItem.getPrice());
            ps.setString(4, menuItem.getDescription() != null ? menuItem.getDescription() : "");
            ps.setString(5, menuItem.getStatus() != null ? menuItem.getStatus() : "Available");
            ps.setString(6, menuItem.getImagePath() != null ? menuItem.getImagePath() : "");
            ps.setInt(7, menuItem.getItemId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // DELETE MENU ITEM
    // =========================
    @Override
    public boolean deleteMenuItem(int itemId) {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                // 1. Delete references in bill_items (if any)
                try (PreparedStatement ps1 = con.prepareStatement("DELETE FROM bill_items WHERE item_id = ?")) {
                    ps1.setInt(1, itemId);
                    ps1.executeUpdate();
                } catch (SQLException ignored) {}

                // 2. Delete references in order_items (column name is item_id)
                try (PreparedStatement ps2 = con.prepareStatement("DELETE FROM order_items WHERE item_id = ?")) {
                    ps2.setInt(1, itemId);
                    ps2.executeUpdate();
                } catch (SQLException ignored) {}

                // 3. Delete from menu_items by item_id
                int affected = 0;
                try (PreparedStatement ps3 = con.prepareStatement("DELETE FROM menu_items WHERE item_id = ?")) {
                    ps3.setInt(1, itemId);
                    affected = ps3.executeUpdate();
                }

                con.commit();
                return affected > 0;

            } catch (Exception ex) {
                con.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
