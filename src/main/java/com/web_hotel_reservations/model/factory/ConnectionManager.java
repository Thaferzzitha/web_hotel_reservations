/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author THALIA
 */
public class ConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/web_hotel_reservations";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
