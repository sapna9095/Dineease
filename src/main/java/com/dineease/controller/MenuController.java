package com.dineease.controller;

import com.dineease.dao.MenuDAO;
import com.dineease.dao.MenuDAOImpl;
import com.dineease.model.MenuItem;

import java.util.List;

public class MenuController {

    private MenuDAO menuDAO;


    public MenuController() {

        menuDAO = new MenuDAOImpl();

    }


    // Get all menu items

    public List<MenuItem> getAllMenuItems() {

        return menuDAO.getAllMenuItems();

    }


    // Add menu item

    public boolean addMenuItem(MenuItem menuItem) {

        return menuDAO.addMenuItem(menuItem);

    }


    // Update menu item

    public boolean updateMenuItem(MenuItem menuItem) {

        return menuDAO.updateMenuItem(menuItem);

    }


    // Delete menu item

    public boolean deleteMenuItem(int itemId) {

        return menuDAO.deleteMenuItem(itemId);

    }

}
