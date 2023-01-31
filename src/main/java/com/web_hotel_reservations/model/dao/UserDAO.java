/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thalia
 */
public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> list() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getString("password"), rs.getString("email"), rs.getInt("role"));
            user.setId(rs.getInt("id"));
            users.add(user);
            System.out.println(rs.getString("username"));
        }
        return users;
    }

    public void create(User user) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (password, email, role) VALUES (?,?,?)");
        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getEmail());
        stmt.setInt(3, user.getRole());
        stmt.execute();
    }

    public User read(int userId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User(rs.getString("password"), rs.getString("email"), rs.getInt("role"));
            user.setId(userId);
            return user;
        }
        return null;
    }

    public void update(User user) throws SQLException {
        PreparedStatement stmt;
        if (user.getPassword() != null) {
            stmt = connection.prepareStatement("UPDATE users SET password = ?, email = ?, role = ? WHERE id = ?");
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getRole());
            stmt.setInt(4, user.getId());
        } else {
            stmt = connection.prepareStatement("UPDATE users SET email = ?, role = ? WHERE id = ?");
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, user.getRole());
            stmt.setInt(3, user.getId());
        }

        stmt.execute();
    }

    public void delete(int userId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        stmt.setInt(1, userId);
        stmt.execute();
    }

    public boolean checkUniqueEmail(String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return false; //Email no es unico
        }
        return true; //Email si es único
    }

    public boolean login(String email, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return true; //Inicio de sesión éxitoso
        }
        return false; //Inicio de sesión fallido
    }

    public String getRole(String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery(); //Colección de datos
        if (rs.next()) {
            int role = rs.getInt("role");

            switch (role) {
                case 1:
                    return "ADMINISTRADOR";
                case 2:
                    return "RECEPCIONISTA";
                case 3:
                    return "CLIENTE";
            }
        }
        return "";
    }

    public int getUserId(String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery(); //Colección de datos
        if (rs.next()) {
            int userId = rs.getInt("id");
            return userId;
        }
        return 0;
    }
}
