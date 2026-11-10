package com.dineease.dao;

import com.dineease.model.User;
import java.util.List;

public interface UserManagementDAO {

    // Get all users
    List<User> getAllUsers();


    // Add user
    boolean addUser(User user);


    // Update user
    boolean updateUser(User user);


    // Delete user
    boolean deleteUser(int userId);

}