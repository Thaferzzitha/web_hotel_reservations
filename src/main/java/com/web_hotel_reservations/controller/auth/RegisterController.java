/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.auth;

import com.web_hotel_reservations.controller.UserController;
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
 * @author nesch
 */
public class RegisterController extends HttpServlet {

    RequestDispatcher dispatcher = null;

    private final UserDAO userDAO;

    public RegisterController() throws SQLException, ClassNotFoundException {
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String message;

        ErrorHandler error = new ErrorHandler();
        String[] fields = {email, password};
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }

        if (error.isValidPassword(password) == false) {
            System.out.println("Contraseña debe tener minimo 8 caracteres y maximo 16");
            message = "Contraseña debe tener minimo 8 caracteres y maximo 16";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }

        if (error.isValidEmail(email) == false) {
            System.out.println("Correo Electrónico no es válido");
            message = "Correo Electrónico no es válido";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }

        try {
            if (userDAO.checkUniqueEmail(email) == false) {
                System.out.println("Correo Electrónico ya registrado, inicie sesión");
                message = "Correo Electrónico ya registrado, inicie sesión";
                request.setAttribute("errorMessage", message);
                request.setAttribute("userEmail", email);
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
            registerUser(
                    request,
                    response
            );
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        PasswordHasher hasher = new PasswordHasher();
        password = hasher.hashPassword(password);
        User user = new User(
                password,
                email,
                3
        );
        userDAO.create(user);
        String message = "Usuario registrado";
        request.setAttribute("message", message);
        session.setAttribute("userEmail", email);
        session.setAttribute("userRole", 3);
        LoginController login = new LoginController();
        login.doPost(request, response);
        /*dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);*/
    }
}
