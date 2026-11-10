package com.dineease.dao;

import com.dineease.model.MenuItem;

import java.util.List;

public interface MenuDAO {

    // View all menu items
    List<MenuItem> getAllMenuItems();

    // Add menu item
    boolean addMenuItem(MenuItem menuItem);

    // Update menu item
    boolean updateMenuItem(MenuItem menuItem);

    // Delete menu item
    boolean deleteMenuItem(int itemId);

}
