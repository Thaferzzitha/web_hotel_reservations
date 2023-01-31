/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.model.dao;

import com.web_hotel_reservations.model.PaymentMethod;
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
public class PaymentMethodDAO {

    private final Connection connection;

    public PaymentMethodDAO(Connection connection) {
        this.connection = connection;
    }

    public List<PaymentMethod> list() throws SQLException {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM payment_methods");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String methodName = rs.getString("method_name");
            PaymentMethod paymentMethod = new PaymentMethod(methodName);
            paymentMethod.setPaymentMethodId(rs.getInt("payment_method_id"));
            paymentMethods.add(paymentMethod);
        }
        return paymentMethods;
    }

    public void create(PaymentMethod paymentMethod) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO payment_methods (method_name) VALUES (?)");

        stmt.setString(1, paymentMethod.getMethodName());

        stmt.execute();

    }

    public PaymentMethod read(int paymentMethodId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM payment_methods WHERE payment_method_id = ?");
        stmt.setInt(1, paymentMethodId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String methodName = rs.getString("method_name");
            PaymentMethod paymentMethod = new PaymentMethod(methodName);
            paymentMethod.setPaymentMethodId(paymentMethodId);

            return paymentMethod;
        }
        return null;
    }

    public void update(PaymentMethod paymentMethod) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE payment_methods SET method_name = ? WHERE payment_method_id = ?");
        stmt.setString(1, paymentMethod.getMethodName());
        stmt.setInt(2, paymentMethod.getPaymentMethodId());

        stmt.execute();
    }

    public void delete(int paymentMethodId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM payment_methods WHERE payment_method_id = ?");
        stmt.setInt(1, paymentMethodId);
        stmt.execute();
    }
    
    public boolean checkUniqueName(String methodName, String action, int paymentMethodId) throws SQLException {
        if (action.equals("register")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM payment_methods WHERE method_name = ?");
            stmt.setString(1, methodName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //methodName no es unico
            }
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM payment_methods WHERE method_name = ? and payment_method_id != ?");
            stmt.setString(1, methodName);
            stmt.setInt(2, paymentMethodId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false; //methodName no es unico
            }
        }

        return true; //methodName si es único
    }
}
