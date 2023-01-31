/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.RoomCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THALIA
 */
public class RoomCategoryDAO {

    private final Connection connection;

    public RoomCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public List<RoomCategory> list() throws SQLException {
        List<RoomCategory> roomCategories = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM room_categories");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String categoryName = rs.getString("category_name");
            String categoryDescription = rs.getString("category_description");
            int capacity = rs.getInt("capacity");
            int beds = rs.getInt("beds");
            float price = rs.getFloat("price");
            RoomCategory roomCategory = new RoomCategory(categoryName, categoryDescription, capacity, beds, price);
            roomCategory.setCategoryId(rs.getInt("category_id"));
            roomCategories.add(roomCategory);
        }
        return roomCategories;
    }

    public void create(RoomCategory roomCategory) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO room_categories (category_name, category_description, capacity, beds, price) VALUES (?, ?, ?, ?, ?)");

        stmt.setString(1, roomCategory.getCategoryName());
        stmt.setString(2, roomCategory.getCategoryDescription());
        stmt.setInt(3, roomCategory.getCapacity());
        stmt.setInt(4, roomCategory.getBeds());
        stmt.setFloat(5, roomCategory.getPrice());

        stmt.execute();

    }

    public RoomCategory read(int categoryId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM room_categories WHERE category_id = ?");
        stmt.setInt(1, categoryId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String categoryName = rs.getString("category_name");
            String categoryDescription = rs.getString("category_description");
            int capacity = rs.getInt("capacity");
            int beds = rs.getInt("beds");
            float price = rs.getFloat("price");

            RoomCategory roomCategory = new RoomCategory(categoryName, categoryDescription, capacity, beds, price);
            roomCategory.setCategoryId(categoryId);

            return roomCategory;
        }
        return null;
    }

    public void update(RoomCategory roomCategory) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE room_categories SET category_name = ?, category_description = ?, capacity = ?, beds = ?, price = ? WHERE category_id = ?");
        stmt.setString(1, roomCategory.getCategoryName());
        stmt.setString(2, roomCategory.getCategoryDescription());
        stmt.setInt(3, roomCategory.getCapacity());
        stmt.setInt(4, roomCategory.getBeds());
        stmt.setFloat(5, roomCategory.getPrice());
        stmt.setInt(6, roomCategory.getCategoryId());

        stmt.execute();
    }

    public void delete(int categoryId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM room_categories WHERE category_id = ?");
        stmt.setInt(1, categoryId);
        stmt.execute();
    }

    public boolean checkUniqueName(String categoryName, String action, int categoryId) throws SQLException {
        if (action.equals("register")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM room_categories WHERE category_name = ?");
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //categoryName no es unico
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM room_categories WHERE category_name = ? and category_id != ?");
            stmt.setString(1, categoryName);
            stmt.setInt(2, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //categoryName no es unico
            }
        }

        return true; //categoryName si es único
    }

    public List<RoomCategory> listByName(String searchName) throws SQLException {
        List<RoomCategory> roomCategories = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM room_categories WHERE category_name LIKE ?");
        stmt.setString(1, "%" + searchName + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String categoryName = rs.getString("category_name");
            String categoryDescription = rs.getString("category_description");
            int capacity = rs.getInt("capacity");
            int beds = rs.getInt("beds");
            float price = rs.getFloat("price");
            RoomCategory roomCategory = new RoomCategory(categoryName, categoryDescription, capacity, beds, price);
            roomCategory.setCategoryId(rs.getInt("category_id"));
            roomCategories.add(roomCategory);
        }
        System.out.println(roomCategories);
        return roomCategories;
    }
}
