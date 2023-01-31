/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.PackService;
import com.web_hotel_reservations.model.Service;
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
public class PackServiceDAO {

    private final Connection connection;

    public PackServiceDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Service> listServicesByPackage(int packId) throws SQLException {
        List<Service> services = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT s.service_id, s.service_name, s.service_price, s.pay_by_person, s.pay_by_day\n"
                + "FROM services s\n"
                + "JOIN pack_services ps ON s.service_id = ps.service_id\n"
                + "WHERE ps.pack_id = ?");
        stmt.setInt(1, packId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String serviceName = rs.getString("service_name");
            float servicePrice = rs.getFloat("service_price");
            boolean payByPerson = rs.getBoolean("pay_by_person");
            boolean payByDay = rs.getBoolean("pay_by_day");

            Service service = new Service(serviceName, servicePrice, payByPerson, payByDay);
            service.setServiceId(rs.getInt("service_id"));
            services.add(service);
        }
        return services;
    }

    public List<Pack> listPackagesByService(int serviceId) throws SQLException {
        List<Pack> packs = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT p.pack_id, p.name, p.discount\n"
                + "FROM packs p\n"
                + "JOIN pack_services ps ON p.pack_id = ps.pack_id\n"
                + "WHERE ps.service_id = ?;");
        stmt.setInt(1, serviceId);
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

    public List<Service> listNotInServicesByPackage(int packId) throws SQLException {
        List<Service> services = new ArrayList<>();
        List<Service> notInServices = new ArrayList<>();
        notInServices = this.listServicesByPackage(packId);
        String notIn = "(";
        for (Service service : notInServices) {
            if (!notIn.equals("(")) {
                notIn = notIn + ", " + service.getServiceId();
            } else {
                notIn = notIn + service.getServiceId();

            }
        }
        notIn = notIn + ")";

        PreparedStatement stmt;

        if (notIn.equals("()")) {
            stmt = connection.prepareStatement("SELECT * FROM services");
        } else {
            stmt = connection.prepareStatement("SELECT * FROM services WHERE service_id NOT IN " + notIn);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String serviceName = rs.getString("service_name");
            float servicePrice = rs.getFloat("service_price");
            boolean payByPerson = rs.getBoolean("pay_by_person");
            boolean payByDay = rs.getBoolean("pay_by_day");

            Service service = new Service(serviceName, servicePrice, payByPerson, payByDay);
            service.setServiceId(rs.getInt("service_id"));
            services.add(service);
        }
        return services;
    }
    
    public List<Pack> listNotInPackagesByService(int serviceId) throws SQLException {
        List<Pack> packs = new ArrayList<>();
        List<Pack> notInPackages = new ArrayList<>();
        notInPackages = this.listPackagesByService(serviceId);
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

    public void create(PackService packService) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO pack_services (pack_id, service_id) VALUES (?, ?)");

        stmt.setInt(1, packService.getPackId());
        stmt.setInt(2, packService.getServiceId());

        stmt.execute();
    }

    public void delete(int packId, int serviceId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM pack_services WHERE pack_id = ? and service_id = ?");
        stmt.setInt(1, packId);
        stmt.setInt(2, serviceId);
        stmt.execute();
    }
}
