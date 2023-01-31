/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author nesch
 */
public class RoomDAO {
    private Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Room> list() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int number = rs.getInt("room_number");
            boolean available = rs.getBoolean("available");
            int floor = rs.getInt("floor");
            int categoryId = rs.getInt("category_id");
            Room room = new Room (number, available, floor, categoryId);
            room.setRoomId(rs.getInt("room_id"));
            rooms.add(room);
        }
        return rooms;
    }
    
    public void create(Room room) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO rooms (room_id, room_number, available, floor, category_id) VALUES (?, ?, ?, ?, ?)");
        
        stmt.setInt(1, room.getRoomId());
        stmt.setInt(2, room.getRoomNumber());
        stmt.setBoolean(3, room.isAvailable());
        stmt.setInt(4, room.getFloor());
        stmt.setInt(5, room.getCategoryId());
        
        stmt.execute();
    }

    public Room read(int roomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms WHERE room_id = ?");
        stmt.setInt(1, roomId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int number = rs.getInt("room_number");
            boolean available = rs.getBoolean("available");
            int floor = rs.getInt("floor");
            int categoryId = rs.getInt("category_id");
            
            Room room = new Room(number, available, floor, categoryId);
            room.setRoomId(roomId);
            
            return room;
        }
        return null;
    }

    public void update(Room room) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE rooms SET room_number = ?, available = ?, floor = ?, category_id = ? WHERE room_id = ?");
        stmt.setInt(1, room.getRoomNumber());
        stmt.setBoolean(2, room.isAvailable());
        stmt.setInt(3, room.getFloor());
        stmt.setInt(4, room.getCategoryId());
        stmt.setInt(5, room.getRoomId());
        stmt.execute();
    }

    public void delete(int roomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM rooms WHERE room_id = ?");
        stmt.setInt(1, roomId);
        stmt.execute();
    }
    
    public boolean checkUniqueNumber(int roomNumber, int floor, String action, int roomId) throws SQLException {
        if (action.equals("register")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms WHERE room_number = ? and floor = ?");
            stmt.setInt(1, roomNumber);
            stmt.setInt(2, floor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //roomNumber no es unico en su piso
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms WHERE room_number = ? and floor = ? and room_id != ?");
            stmt.setInt(1, roomNumber);
            stmt.setInt(2, floor);
            stmt.setInt(3, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //roomNumber no es unico
            }
        }

        return true; //roomNumber si es único
    }

    public List<Room> listRoomByNumber(int searchNumber) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms WHERE room_number = ?");
        stmt.setInt(1, searchNumber);
        ResultSet rs = stmt.executeQuery();
         while (rs.next()) {
            int number = rs.getInt("room_number");
            boolean available = rs.getBoolean("available");
            int floor = rs.getInt("floor");
            int categoryId = rs.getInt("category_id");
            Room room = new Room (number, available, floor, categoryId);
            room.setRoomId(rs.getInt("room_id"));
            rooms.add(room);
        }
        return rooms;
    }
    
    public String getCategoryName(int categoryId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT category_name FROM room_categories WHERE category_id = ?");
        stmt.setInt(1, categoryId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {            
            return rs.getString("category_name");
        }
        return "";
    }
}
