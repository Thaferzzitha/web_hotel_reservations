/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.admin.RoomCategoryController;
import com.web_hotel_reservations.model.ServiceReservation;
import com.web_hotel_reservations.model.dao.ServiceReservationDAO;
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
public class ServiceReservationController extends HttpServlet {
    ServiceReservationDAO serviceReservationDAO;
    
    public ServiceReservationController() throws SQLException, ClassNotFoundException {
        this.serviceReservationDAO = new ServiceReservationDAO(ConnectionManager.getConnection());
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            deleteServiceReservation(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
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
            registerServiceReservation(
                    request,
                    response
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/reservations/show.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerServiceReservation(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        
        ServiceReservation serviceReservation = new ServiceReservation();
        
        serviceReservation.setReservationId(reservationId);
        
        serviceReservation.setServiceId(serviceId);

        serviceReservationDAO.create(serviceReservation);

        String message = "Servicio registrado en la reservación";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);
    }

    private void deleteServiceReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        
        serviceReservationDAO.delete(reservationId, serviceId);

        String message = "Servicio eliminado del paquete";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);

    }
}
