package com.dineease.dao;

import com.dineease.model.User;


public interface UserDAO {

    User login(String username,String password);

}
