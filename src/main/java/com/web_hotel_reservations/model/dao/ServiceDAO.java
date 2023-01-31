/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.Service;
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
public class ServiceDAO {
    private final Connection connection;

    public ServiceDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Service> list() throws SQLException {
        List<Service> services = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM services");
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
    
    public void create(Service service) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO services (service_name, service_price, pay_by_person, pay_by_day) VALUES (?, ?, ?, ?)");
        
        stmt.setString(1, service.getServiceName());
        stmt.setFloat(2, service.getServicePrice());
        stmt.setBoolean(3, service.isPayByPerson());
        stmt.setBoolean(4, service.isPayByDay());
        
        stmt.execute();
    }

    public Service read(int serviceId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM services WHERE service_id = ?");
        stmt.setInt(1, serviceId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String serviceName = rs.getString("service_name");
            float servicePrice = rs.getFloat("service_price");
            boolean payByPerson = rs.getBoolean("pay_by_person");   
            boolean payByDay = rs.getBoolean("pay_by_day");
            Service service = new Service (serviceName, servicePrice, payByPerson, payByDay);
            service.setServiceId(serviceId);
            
            return service;
        }
        return null;
    }

    public void update(Service service) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE services SET service_name = ?, service_price = ?, pay_by_person = ?, pay_by_day = ? WHERE service_id = ?");
        stmt.setString(1, service.getServiceName());
        stmt.setFloat(2, service.getServicePrice());
        stmt.setBoolean(3, service.isPayByPerson());
        stmt.setBoolean(4, service.isPayByDay());
        stmt.setInt(5, service.getServiceId());
        stmt.execute();
    }

    public void delete(int serviceId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM services WHERE service_id = ?");
        stmt.setInt(1, serviceId);
        stmt.execute();
    }
    
    public boolean checkUniqueName(String serviceName, String action, int serviceId) throws SQLException {
        if (action.equals("register")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM services WHERE service_name = ?");
            stmt.setString(1, serviceName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //serviceName no es unico
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM services WHERE service_name = ? and service_id != ?");
            stmt.setString(1, serviceName);
            stmt.setInt(2, serviceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //serviceName no es unico
            }
        }

        return true; //serviceName si es único
    }
}
