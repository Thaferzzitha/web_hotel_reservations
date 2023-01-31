/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.auth;

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
public class LoginController extends HttpServlet {

    RequestDispatcher dispatcher = null;

    private final UserDAO userDAO;

    public LoginController() throws SQLException, ClassNotFoundException {
        this.userDAO = new UserDAO(ConnectionManager.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message;
        boolean canLogin;
        String role;

        PasswordHasher hasher = new PasswordHasher();
        password = hasher.hashPassword(password);

        try {
            canLogin = userDAO.login(email, password);
            if (canLogin) {
                role = userDAO.getRole(email);
                int userId = userDAO.getUserId(email);
                session.setAttribute("userId", userId);
                session.setAttribute("userEmail", email);
                session.setAttribute("userRole", role);
                request.getRequestDispatcher("/dashboard.jsp?action=list").forward(request, response);
            } else {
                message = "Correo electrónico o contraseña no coinciden con nuestros registros";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
