package com.dineease.dao;

import com.dineease.model.User;
import com.dineease.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserManagementDAOImpl implements UserManagementDAO {


    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();


            while(rs.next()) {

                User user = new User(
                        rs.getString("username"),
                        rs.getString("password")
                );


                user.setUserId(
                        rs.getInt("user_id")
                );

                user.setFullName(
                        rs.getString("full_name")
                );

                user.setRole(
                        rs.getString("role")
                );

                user.setStatus(
                        rs.getString("status")
                );


                users.add(user);

            }


        } catch(Exception e) {

            e.printStackTrace();

        }


        return users;
    }



    @Override
    public boolean addUser(User user) {

        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "INSERT INTO users(full_name,username,password,role,status) VALUES(?,?,?,?,?)";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setString(1,user.getFullName());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getRole());
            ps.setString(5,user.getStatus());


            return ps.executeUpdate() > 0;


        } catch(Exception e) {

            e.printStackTrace();

        }


        return false;
    }



    @Override
    public boolean updateUser(User user) {


        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "UPDATE users SET full_name=?, role=?, status=? WHERE user_id=?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setString(1,user.getFullName());
            ps.setString(2,user.getRole());
            ps.setString(3,user.getStatus());
            ps.setInt(4,user.getUserId());


            return ps.executeUpdate() > 0;


        } catch(Exception e) {

            e.printStackTrace();

        }


        return false;
    }



    @Override
    public boolean deleteUser(int userId) {


        try {

            Connection con =
                    DBConnection.getConnection();


            String sql =
                    "DELETE FROM users WHERE user_id=?";


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setInt(1,userId);


            return ps.executeUpdate() > 0;


        } catch(Exception e) {

            e.printStackTrace();

        }


        return false;
    }

}