/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.auth.PasswordHasher;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.User;
import com.web_hotel_reservations.model.dao.UserDAO;
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
public class UserController extends HttpServlet {

    RequestDispatcher dispatcher = null;

    private final UserDAO userDAO;

    public UserController() throws SQLException, ClassNotFoundException {
        this.userDAO = new UserDAO(ConnectionManager.getConnection());
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

        String action = request.getParameter("action");
        String message = "";

        if (action == null) {

            action = "show";
        }

        try {
            switch (action) {
                case "show":
                    getSingleUser(request, response);
                    break;

                case "edit":
                    editUser(request, response);
                    break;

                default:
                    getSingleUser(request, response);
                    break;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/client/user/edit.jsp").forward(request, response);
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
            request.getParameter("email")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
        }

        try {
            updateUser(
                    request,
                    response
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
        }
    }

    private void getSingleUser(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession();

        int userId = Integer.parseInt(request.getParameter("userId"));
        
        System.out.println(userId);
        
        User user = userDAO.read(userId);
        
        session.setAttribute("userId", userId);

        request.setAttribute("user", user);

        dispatcher = request.getRequestDispatcher("/user/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editUser(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        User user = userDAO.read(userId);

        request.setAttribute("user", user);

        dispatcher = request.getRequestDispatcher("/user/edit.jsp");

        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int role = (int) session.getAttribute("role");
        String email = request.getParameter("email");
        String password = null;
        if (request.getParameter("password") != null) {
            password = request.getParameter("password");
            PasswordHasher hasher = new PasswordHasher();
            password = hasher.hashPassword(password);
        }

        User user = new User(
                password,
                email,
                role
        );
        user.setId(userId);

        userDAO.update(user);

        String message = "Perfil actualizado";
        request.setAttribute("message", message);
        getSingleUser(request, response);
    }
}
