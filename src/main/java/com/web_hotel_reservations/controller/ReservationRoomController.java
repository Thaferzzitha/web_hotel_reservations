/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.admin.RoomCategoryController;
import com.web_hotel_reservations.controller.admin.PackServiceController;
import com.web_hotel_reservations.model.ReservationRoom;
import com.web_hotel_reservations.model.dao.ReservationRoomDAO;
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
public class ReservationRoomController  extends HttpServlet {

    ReservationRoomDAO reservationRoomDAO;
    
    public ReservationRoomController()  throws SQLException, ClassNotFoundException {
        this.reservationRoomDAO = new ReservationRoomDAO((ConnectionManager.getConnection()));
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            deleteReservationRoom(request, response);
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
            registerReservationRoom(
                    request,
                    response
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/reservation/show.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PackServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerReservationRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        
        ReservationRoom reservationRoom = new ReservationRoom();
        
        reservationRoom.setReservationId(reservationId);
        
        reservationRoom.setRoomId(roomId);

        reservationRoomDAO.create(reservationRoom);

        String message = "Cuarto registrado en la reservación";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);
    }

    private void deleteReservationRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        
        reservationRoomDAO.delete(reservationId, roomId);

        String message = "Cuarto eliminado del paquete";

        request.setAttribute("message", message);
        
        ReservationController resController = new ReservationController();
        
        resController.doGet(request, response);

    }
}
