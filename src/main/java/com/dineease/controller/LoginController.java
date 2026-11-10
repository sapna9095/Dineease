package com.dineease.controller;


import com.dineease.dao.*;
import com.dineease.model.User;


public class LoginController {


    private UserDAO userDAO;


    public LoginController(){

        userDAO=new UserDAOImpl();

    }



    public User authenticate(
            String username,
            String password
    ){

        return userDAO.login(
                username,
                password
        );

    }


}