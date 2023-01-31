/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Pack;
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
public class PackDAO {

    private final Connection connection;

    public PackDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Pack> list() throws SQLException {
        List<Pack> packs = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM packs");
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

    public void create(Pack pack) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO packs (name, discount) VALUES (?, ?)");

            stmt.setString(1, pack.getName());
            stmt.setInt(2, pack.getDiscount());
        
            stmt.execute();

    }

    public Pack read(int packId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM packs WHERE pack_id = ?");
        stmt.setInt(1, packId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            int discount = rs.getInt("discount");
            Pack pack = new Pack(name, discount);
            pack.setPackId(packId);

            return pack;
        }
        return null;
    }

    public void update(Pack pack) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE packs SET name = ?, discount = ? WHERE pack_id = ?");
        stmt.setString(1, pack.getName());
        stmt.setInt(2, pack.getDiscount());
        stmt.setInt(3, pack.getPackId());

        stmt.execute();
    }

    public void delete(int packId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM packs WHERE pack_id = ?");
        stmt.setInt(1, packId);
        stmt.execute();
    }
    
    public boolean checkUniqueName(String name, String action, int packId) throws SQLException {
        if (action.equals("register")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM packs WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //namename no es unico
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM packs WHERE name = ? and pack_id != ?");
            stmt.setString(1, name);
            stmt.setInt(2, packId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //name no es unico
            }
        }

        return true; //name si es único
    }
}
