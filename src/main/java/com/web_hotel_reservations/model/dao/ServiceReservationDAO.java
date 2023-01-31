/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Service;
import com.web_hotel_reservations.model.ServiceReservation;
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
public class ServiceReservationDAO {
    
    private final Connection connection;
    
    public ServiceReservationDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Service> listServicesByReservation(int reservationId) throws SQLException {
        List<Service> services = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT s.service_id, s.service_name, s.service_price, s.pay_by_person, s.pay_by_day\n" +
        "FROM services s\n" +
        "JOIN reservation_services rs ON s.service_id = rs.service_id\n" +
        "WHERE rs.reservation_id = ?;");
        stmt.setInt(1, reservationId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String serviceName = rs.getString("service_name");
            float servicePrice = rs.getFloat("service_price");
            boolean payByPerson = rs.getBoolean("pay_by_person");
            boolean payByDay = rs.getBoolean("pay_by_day");
            
            Service service = new Service (serviceName, servicePrice, payByPerson, payByDay);
            service.setServiceId(rs.getInt("service_id"));
            services.add(service);
        }
        return services;
    }
    
    public List<Service> listNotInServicesByReservation(int reservationId) throws SQLException {
        List<Service> services = new ArrayList<>();
        List<Service> notInServices = new ArrayList<>();
        notInServices = this.listServicesByReservation(reservationId);
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
    
    public void create(ServiceReservation serviceReservation) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO reservation_services (reservation_id, service_id) VALUES (?, ?)");

        stmt.setInt(1, serviceReservation.getReservationId());
        stmt.setInt(2, serviceReservation.getServiceId());

        stmt.execute();
    }

    public void delete(int reservationId, int serviceId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM reservation_services WHERE reservation_id = ? and service_id = ?");
        stmt.setInt(1, reservationId);
        stmt.setInt(2, serviceId);
        stmt.execute();
    }
    
}
