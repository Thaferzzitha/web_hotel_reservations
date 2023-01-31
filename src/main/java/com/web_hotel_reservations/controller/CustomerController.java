/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.admin.RoomCategoryController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Customer;
import com.web_hotel_reservations.model.dao.CustomerDAO;
import com.web_hotel_reservations.model.factory.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THALIA
 */
public class CustomerController extends HttpServlet {

    RequestDispatcher dispatcher = null;

    CustomerDAO customerDAO;

    public CustomerController() throws SQLException, SQLException, ClassNotFoundException {
        this.customerDAO = new CustomerDAO(ConnectionManager.getConnection());
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if (action == null) {
            action = "show";
        }

        try {

            switch (action) {

                case "create":
                    dispatcher = request.getRequestDispatcher("/client/customer/form.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "show":
                    getSingleCustomer(request, response);
                    break;

                default:
                    getSingleCustomer(request, response);
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String message;

        ErrorHandler error = new ErrorHandler();
        String[] fields = {
            request.getParameter("identificationNumber"),
            request.getParameter("firstName"),
            request.getParameter("lastName"),
            request.getParameter("email"),
            request.getParameter("phone")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/client/customer/form.jsp").forward(request, response);
        }

        String customerId = request.getParameter("customerId");

        try {

            if (customerId == null || customerId.isEmpty()) {
                registerCustomer(
                        request,
                        response
                );

            } else {
                updateCustomer(
                        request,
                        response
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/client/customer/form.jsp").forward(request, response);
        }
    }

    //TODO cambiar todos los métodos para el cliente en la parte del cliente
    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        session.setAttribute("userId", userId);
        String identificationNumber = request.getParameter("identificationNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Customer customer = new Customer(
                identificationNumber,
                firstName,
                lastName,
                email,
                phone,
                address,
                userId
        );

        customerDAO.create(customer);
        getSingleCustomer(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        session.setAttribute("userId", userId);
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String identificationNumber = request.getParameter("identificationNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Customer customer = new Customer(
                identificationNumber,
                firstName,
                lastName,
                email,
                phone,
                address,
                userId
        );
        
        customer.setCustomerId(customerId);

        customerDAO.update(customer);
        getSingleCustomer(request, response);
    }

    private void getSingleCustomer(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession();    
        int userId = (int) session.getAttribute("userId");

        Customer customer = customerDAO.read(userId);

        request.setAttribute("customer", customer);
        
        session.setAttribute("userId", userId);

        dispatcher = request.getRequestDispatcher("/client/customer/show.jsp");

        dispatcher.forward(request, response);
    }
}
