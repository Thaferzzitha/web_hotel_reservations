/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.ReservationRoom;
import com.web_hotel_reservations.model.Room;
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
public class ReservationRoomDAO {
    
    private final Connection connection;
    
    public ReservationRoomDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Room> listRoomsByReservation(int reservationId) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT r.room_id, r.room_number, r.available, r.floor, r.category_id\n" +
        "FROM rooms r\n" +
        "JOIN reservation_rooms rr ON r.room_id = rr.room_id\n" +
        "WHERE rr.reservation_id = ?;");
        stmt.setInt(1, reservationId);
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
    
    public List<Room> listNotInRoomsByReservation(int reservationId) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        List<Room> notInRooms = new ArrayList<>();
        notInRooms = this.listRoomsByReservation(reservationId);
        String notIn = "(";
        for (Room room : notInRooms) {
            if (!notIn.equals("(")) {
                notIn = notIn + ", " + room.getRoomId();
            } else {
                notIn = notIn + room.getRoomId();
            }
        }
        notIn = notIn + ")";

        PreparedStatement stmt;

        if (notIn.equals("()")) {
            stmt = connection.prepareStatement("SELECT * FROM rooms ");
        } else {
            stmt = connection.prepareStatement("SELECT * FROM rooms WHERE room_id NOT IN " + notIn);
        }

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

    public void create(ReservationRoom reservationRoom) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO reservation_rooms (reservation_id, room_id) VALUES (?, ?)");

        stmt.setInt(1, reservationRoom.getReservationId());
        stmt.setInt(2, reservationRoom.getRoomId());

        stmt.execute();
    }

    public void delete(int reservationId, int roomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM reservation_rooms WHERE reservation_id = ? and room_id = ?");
        stmt.setInt(1, reservationId);
        stmt.setInt(2, roomId);
        stmt.execute();
    }
}
