package com.dineease.controller;


import com.dineease.dao.UserManagementDAO;
import com.dineease.dao.UserManagementDAOImpl;
import com.dineease.model.User;

import java.util.List;


public class UserController {


    private UserManagementDAO userDAO;



    public UserController(){

        userDAO = new UserManagementDAOImpl();

    }



    // Get all users

    public List<User> getAllUsers(){

        return userDAO.getAllUsers();

    }




    // Add user

    public boolean addUser(User user){

        return userDAO.addUser(user);

    }




    // Update user

    public boolean updateUser(User user){

        return userDAO.updateUser(user);

    }




    // Delete user

    public boolean deleteUser(int userId){

        return userDAO.deleteUser(userId);

    }


}
