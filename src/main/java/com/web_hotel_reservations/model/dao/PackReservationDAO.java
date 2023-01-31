/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.PackReservation;
import com.web_hotel_reservations.model.PackService;
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
public class PackReservationDAO {
    private final Connection connection;
    
    public PackReservationDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Pack> listPackagesByReservation(int reservationId) throws SQLException {
        List<Pack> packs = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT p.pack_id, p.name, p.discount\n" +
        "FROM packs p\n" +
        "JOIN pack_reservations pr ON p.pack_id = pr.pack_id\n" +
        "WHERE pr.reservation_id = ?;");
        stmt.setInt(1, reservationId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            int discount = rs.getInt("discount");
            Pack pack = new Pack(name, discount);
            pack.setPackId(rs.getInt("pack_id"));
            packs.add(pack);
        }
        return packs;
    }
    
    public List<Pack> listNotInPackagesByReservation(int reservationId) throws SQLException {
        List<Pack> packs = new ArrayList<>();
        List<Pack> notInPackages = new ArrayList<>();
        notInPackages = this.listPackagesByReservation(reservationId);
        String notIn = "(";
        for (Pack pack : notInPackages) {
            if (!notIn.equals("(")) {
                notIn = notIn + ", " + pack.getPackId();
            } else {
                notIn = notIn + pack.getPackId();
            }
        }
        notIn = notIn + ")";

        PreparedStatement stmt;

        if (notIn.equals("()")) {
            stmt = connection.prepareStatement("SELECT * FROM packs");
        } else {
            stmt = connection.prepareStatement("SELECT * FROM packs WHERE pack_id NOT IN " + notIn);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            int discount = rs.getInt("discount");
            Pack pack = new Pack(name, discount);
            pack.setPackId(rs.getInt("pack_id"));
            packs.add(pack);
        }
        return packs;
    }
    
    public void create(PackReservation packReservation) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO pack_reservations (pack_id, reservation_id) VALUES (?, ?)");

        stmt.setInt(1, packReservation.getPackId());
        stmt.setInt(2, packReservation.getReservationId());

        stmt.execute();
    }

    public void delete(int packId, int reservationId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM pack_reservations WHERE pack_id = ? and reservation_id = ?");
        stmt.setInt(1, packId);
        stmt.setInt(2, reservationId);
        stmt.execute();
    }
}
