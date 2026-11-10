package com.dineease.dao;


import com.dineease.model.User;
import com.dineease.util.DBConnection;

import java.sql.*;


public class UserDAOImpl implements UserDAO {


    @Override
    public User login(String username,String password){


        User user=null;


        try{


            String query=
                    "SELECT * FROM users WHERE username=? AND password=?";


            Connection con=
                    DBConnection.getConnection();


            PreparedStatement ps=
                    con.prepareStatement(query);


            ps.setString(1,username);
            ps.setString(2,password);


            ResultSet rs=
                    ps.executeQuery();



            if(rs.next()){


                user=new User(
                        rs.getString("username"),
                        rs.getString("password")
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


            }


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return user;

    }

}
