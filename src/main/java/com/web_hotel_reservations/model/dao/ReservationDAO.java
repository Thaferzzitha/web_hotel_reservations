/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Reservation;
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
public class ReservationDAO {
    private Connection connection;
    
    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Reservation> list() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("customerId");
            String checkIn = rs.getString("check_in");
            String checkOut = rs.getString("check_out");
            int state = rs.getInt("state");
            Reservation reservation = new Reservation(customerId, checkIn, checkOut, state);
            reservation.setReservationId(rs.getInt("reservation_id"));
            reservations.add(reservation);
        }
        return reservations;
    }
    
    public List<Reservation> listByCustomer(int customerId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations WHERE customer_id = ?");
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String checkIn = rs.getString("check_in");
            String checkOut = rs.getString("check_out");
            int state = rs.getInt("state");
            Reservation reservation = new Reservation(customerId, checkIn, checkOut, state);
            reservation.setReservationId(rs.getInt("reservation_id"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public void create(Reservation reservation) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO reservations (customer_id, check_in, check_out, state) VALUES (?, ?, ?, ?)");

        stmt.setInt(1, reservation.getCustomerId());
        stmt.setString(2, reservation.getCheckIn());
        stmt.setString(3, reservation.getCheckOut());
        stmt.setInt(4, reservation.getState());

        stmt.execute();
    }

    public Reservation read(int reservationId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations WHERE reservation_id = ?");
        stmt.setInt(1, reservationId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int customerId = rs.getInt("customer_id");
            String checkIn = rs.getString("check_in");
            String checkOut = rs.getString("check_out");
            int state = rs.getInt("state");
            Reservation reservation = new Reservation(customerId, checkIn, checkOut, state);
            reservation.setReservationId(reservationId);

            return reservation;
        }
        return null;
    }

    public void update(Reservation reservation) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE reservations SET customer_id = ?, check_in = ?, check_out = ?, state = ? WHERE reservation_id = ?");
        stmt.setInt(1, reservation.getCustomerId());
        stmt.setString(2, reservation.getCheckIn());
        stmt.setString(3, reservation.getCheckOut());
        stmt.setInt(4, reservation.getState());
        stmt.setInt(5, reservation.getReservationId());

        stmt.execute();
    }

    public void delete(int reservationId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM reservations WHERE reservation_id = ?");
        stmt.setInt(1, reservationId);
        stmt.execute();
    }
}
