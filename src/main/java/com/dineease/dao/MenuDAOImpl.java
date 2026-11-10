package com.dineease.dao;

import com.dineease.model.MenuItem;
import com.dineease.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {


    // =========================
    // GET ALL MENU ITEMS
    // =========================

    @Override
    public List<MenuItem> getAllMenuItems() {

        List<MenuItem> menuItems =
                new ArrayList<>();

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM menu_items";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {

                MenuItem item =
                        new MenuItem(
                                rs.getString("item_name"),
                                rs.getString("category"),
                                rs.getDouble("price"),
                                rs.getString("description"),
                                rs.getString("status")
                        );


                // Item ID
                item.setItemId(
                        rs.getInt("item_id")
                );


                // Image Path
                item.setImagePath(
                        rs.getString("image_path")
                );


                menuItems.add(
                        item
                );
            }


        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return menuItems;
    }


    // =========================
    // ADD MENU ITEM
    // =========================

    @Override
    public boolean addMenuItem(
            MenuItem menuItem
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "INSERT INTO menu_items " +
                            "(item_name, category, price, " +
                            "description, status, image_path) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            // Item Name
            ps.setString(
                    1,
                    menuItem.getItemName()
            );


            // Category
            ps.setString(
                    2,
                    menuItem.getCategory()
            );


            // Price
            ps.setDouble(
                    3,
                    menuItem.getPrice()
            );


            // Description
            ps.setString(
                    4,
                    menuItem.getDescription()
            );


            // Status
            ps.setString(
                    5,
                    menuItem.getStatus()
            );


            // Image Path
            ps.setString(
                    6,
                    menuItem.getImagePath()
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }


    // =========================
    // UPDATE MENU ITEM
    // =========================

    @Override
    public boolean updateMenuItem(
            MenuItem menuItem
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "UPDATE menu_items SET " +
                            "item_name=?, category=?, price=?, " +
                            "description=?, status=?, image_path=? " +
                            "WHERE item_id=?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            // Item Name
            ps.setString(
                    1,
                    menuItem.getItemName()
            );


            // Category
            ps.setString(
                    2,
                    menuItem.getCategory()
            );


            // Price
            ps.setDouble(
                    3,
                    menuItem.getPrice()
            );


            // Description
            ps.setString(
                    4,
                    menuItem.getDescription()
            );


            // Status
            ps.setString(
                    5,
                    menuItem.getStatus()
            );


            // Image Path
            ps.setString(
                    6,
                    menuItem.getImagePath()
            );


            // Item ID
            ps.setInt(
                    7,
                    menuItem.getItemId()
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }


    // =========================
    // DELETE MENU ITEM
    // =========================

    @Override
    public boolean deleteMenuItem(
            int itemId
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "DELETE FROM menu_items " +
                            "WHERE item_id=?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setInt(
                    1,
                    itemId
            );


            return ps.executeUpdate() > 0;

        }
        catch (Exception e) {

            e.printStackTrace();

        }


        return false;
    }

}