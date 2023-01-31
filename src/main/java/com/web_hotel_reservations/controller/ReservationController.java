/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller;

import com.web_hotel_reservations.controller.admin.RoomCategoryController;
import com.web_hotel_reservations.controller.admin.RoomController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Customer;
import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.Reservation;
import com.web_hotel_reservations.model.Room;
import com.web_hotel_reservations.model.Service;
import com.web_hotel_reservations.model.dao.CustomerDAO;
import com.web_hotel_reservations.model.dao.PackReservationDAO;
import com.web_hotel_reservations.model.dao.ReservationDAO;
import com.web_hotel_reservations.model.dao.ReservationRoomDAO;
import com.web_hotel_reservations.model.dao.RoomDAO;
import com.web_hotel_reservations.model.dao.ServiceReservationDAO;
import com.web_hotel_reservations.model.factory.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THALIA
 */
public class ReservationController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    ReservationDAO reservationDAO;

    public ReservationController() throws SQLException, ClassNotFoundException {
        this.reservationDAO = new ReservationDAO(ConnectionManager.getConnection());
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

        if (action == null) {
            action = "list";
        }

        try {

            switch (action) {

                case "list":
                    listReservation(request, response);
                    break;

                case "create":
                    createReservation(request, response);
                    break;

                case "show":
                    getSingleReservation(request, response);
                    break;

                case "delete":
                    deleteReservation(request, response);
                    break;

                default:
                    listReservation(request, response);
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
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
            request.getParameter("checkIn"),
            request.getParameter("checkOut")
        };

        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/client/reservation/create.jsp").forward(request, response);
        }

        String reservationId = request.getParameter("reservationId");

        try {
            if (reservationId == null || reservationId.isEmpty()) {

                registerReservation(request, response);
            } else {
                updateReservation(
                        request,
                        response
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/client/reservation/create.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerReservation(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        HttpSession session = request.getSession();
        int userId = 1;
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        CustomerDAO customerDAO = new CustomerDAO(ConnectionManager.getConnection());
        Customer customer = customerDAO.read(userId);
        int customerId = customer.getCustomerId();
        int DRAFT = 1;

        Reservation reservation = new Reservation(
                customerId,
                checkIn,
                checkOut,
                DRAFT
        );

        reservationDAO.create(reservation);

        listReservation(request, response);
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        HttpSession session = request.getSession();
        int userId = 1;
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        CustomerDAO customerDAO = new CustomerDAO(ConnectionManager.getConnection());
        Customer customer = customerDAO.readByUser(userId);
        int customerId = customer.getCustomerId();
        int DRAFT = 1;

        Reservation reservation = new Reservation(
                customerId,
                checkIn,
                checkOut,
                DRAFT
        );
        reservation.setReservationId(reservationId);

        reservationDAO.update(reservation);

        String message = "Reservación actualizada";
        request.setAttribute("message", message);
        listReservation(request, response);
    }

    private void listReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        HttpSession session = request.getSession();
        int userId = 1;
        CustomerDAO customerDAO = new CustomerDAO(ConnectionManager.getConnection());
        Customer customer = customerDAO.read(userId);
        int customerId = customer.getCustomerId();
        List<Reservation> reservationList = reservationDAO.listByCustomer(customerId);
        
        System.out.println(reservationList);

        request.setAttribute("reservationList", reservationList);

        dispatcher = request.getRequestDispatcher("/client/reservation/index.jsp");

        dispatcher.forward(request, response);

    }

    private void createReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        dispatcher = request.getRequestDispatcher("/client/reservation/create.jsp");

        dispatcher.forward(request, response);

    }

    private void getSingleReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        Reservation reservation = reservationDAO.read(reservationId);

        ReservationRoomDAO reservationRoomDAO = new ReservationRoomDAO(ConnectionManager.getConnection());

        PackReservationDAO packReservationDAO = new PackReservationDAO(ConnectionManager.getConnection());

        ServiceReservationDAO serviceReservationDAO = new ServiceReservationDAO(ConnectionManager.getConnection());
        
        RoomDAO roomDAO = new RoomDAO(ConnectionManager.getConnection());

        List<Room> roomList = reservationRoomDAO.listRoomsByReservation(reservationId);
        
        for (Room room : roomList) {
            room.setCategoryName(roomDAO.getCategoryName(room.getCategoryId()));
        }

        List<Pack> packList = packReservationDAO.listPackagesByReservation(reservationId);

        List<Service> serviceList = serviceReservationDAO.listServicesByReservation(reservationId);
        
        List<Room> notInRoomList = reservationRoomDAO.listNotInRoomsByReservation(reservationId);
        
        for (Room room : notInRoomList) {
            room.setCategoryName(roomDAO.getCategoryName(room.getCategoryId()));
        }

        List<Pack> notInPackList = packReservationDAO.listNotInPackagesByReservation(reservationId);

        List<Service> notInServiceList = serviceReservationDAO.listNotInServicesByReservation(reservationId);

        request.setAttribute("packList", packList);

        request.setAttribute("serviceList", serviceList);
        
        request.setAttribute("roomList", roomList);

        request.setAttribute("notInRoomList", notInRoomList);

        request.setAttribute("notInPackList", notInPackList);

        request.setAttribute("notInServiceList", notInServiceList);

        request.setAttribute("reservation", reservation);

        dispatcher = request.getRequestDispatcher("/cliente/reservation/show.jsp");

        dispatcher.forward(request, response);
    }

    private void deleteReservation(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        reservationDAO.delete(reservationId);

        request.setAttribute("message", "Reservación eliminada");

        listReservation(request, response);

    }
}
