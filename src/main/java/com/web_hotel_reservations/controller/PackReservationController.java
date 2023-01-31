/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.admin.RoomCategoryController;
import com.web_hotel_reservations.controller.admin.PackServiceController;
import com.web_hotel_reservations.model.PackReservation;
import com.web_hotel_reservations.model.dao.PackReservationDAO;
import com.web_hotel_reservations.model.factory.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THALIA
 */
public class PackReservationController extends HttpServlet {
    PackReservationDAO packReservationDAO;
    
    public PackReservationController() throws SQLException, ClassNotFoundException {
        this.packReservationDAO = new PackReservationDAO(ConnectionManager.getConnection());
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            deletePackReservation(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PackServiceController.class.getName()).log(Level.SEVERE, null, ex);
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

        try {
            registerPackReservation(
                    request,
                    response
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/reservations/show.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PackServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerPackReservation(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int packId = Integer.parseInt(request.getParameter("packId"));
        
        PackReservation packReservation = new PackReservation();
        
        packReservation.setReservationId(reservationId);
        
        packReservation.setPackId(packId);

        packReservationDAO.create(packReservation);

        String message = "Pack registrado en la reservación";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);
    }

    private void deletePackReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int packId = Integer.parseInt(request.getParameter("packId"));
        
        packReservationDAO.delete(packId, reservationId);

        String message = "Paquete eliminado del paquete";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);

    }
}
