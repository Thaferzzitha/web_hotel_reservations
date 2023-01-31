/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Room;
import com.web_hotel_reservations.model.RoomCategory;
import com.web_hotel_reservations.model.dao.RoomCategoryDAO;
import com.web_hotel_reservations.model.dao.RoomDAO;
import com.web_hotel_reservations.model.factory.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THALIA
 */
public class RoomController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    RoomDAO roomDAO;

    public RoomController() throws SQLException, ClassNotFoundException {
        this.roomDAO = new RoomDAO(ConnectionManager.getConnection());
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
                    listRoom(request, response);
                    break;

                case "create":
                    createRoom(request, response);
                    break;

                case "show":
                    getSingleRoom(request, response);
                    break;

                case "edit":
                    editRoom(request, response);
                    break;

                case "delete":
                    deleteRoom(request, response);
                    break;

                default:
                    listRoom(request, response);
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
            request.getParameter("roomNumber"),
            request.getParameter("floor"),
            request.getParameter("categoryId")
        };

        try {
            RoomCategoryDAO roomCategoryDAO = new RoomCategoryDAO(ConnectionManager.getConnection());
            List<RoomCategory> roomCategoryList = roomCategoryDAO.list();
            request.setAttribute("roomCategoryList", roomCategoryList);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/room/create.jsp").forward(request, response);
        }

        String roomId = request.getParameter("roomId");
        String roomNumber = request.getParameter("roomNumber");
        String floor = request.getParameter("floor");

        try {
            if (roomId == null || roomId.isEmpty()) {
                boolean isUniqueNumber = roomDAO.checkUniqueNumber(Integer.parseInt(roomNumber), Integer.parseInt(floor), "register", 1);
                if (isUniqueNumber == false) {
                    System.out.println("Cuarto ya registrado");
                    message = "Cuarto ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/room/create.jsp").forward(request, response);
                } else {
                    registerRoom(
                            request,
                            response
                    );
                }
            } else {
                if (roomDAO.checkUniqueNumber(Integer.parseInt(roomNumber), Integer.parseInt(floor), "edit", Integer.parseInt(roomId)) == false) {
                    System.out.println("Cuarto ya registrado");
                    message = "Cuarto ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/room/edit.jsp").forward(request, response);
                } else {
                    updateRoom(
                            request,
                            response
                    );
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/room/create.jsp").forward(request, response);
        }
    }

    private void registerRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        boolean available = true;
        int floor = Integer.parseInt(request.getParameter("floor"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Room room = new Room(
                roomNumber,
                available,
                floor,
                categoryId
        );

        roomDAO.create(room);

        String message = "Cuarto registrado";
        request.setAttribute("message", message);
        listRoom(request, response);
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        boolean available = false;
        if (request.getParameter("available") != null ) {
            available = true;
        }
        int floor = Integer.parseInt(request.getParameter("floor"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Room room = new Room(
                roomNumber,
                available,
                floor,
                categoryId
        );
        room.setRoomId(roomId);

        roomDAO.update(room);

        String message = "Cuarto actualizado";
        request.setAttribute("message", message);
        listRoom(request, response);
    }

    private void listRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        List<Room> roomList = roomDAO.list();

        for (Room room : roomList) {
            room.setCategoryName(roomDAO.getCategoryName(room.getCategoryId()));
        }

        request.setAttribute("roomList", roomList);

        dispatcher = request.getRequestDispatcher("/admin/room/index.jsp");

        dispatcher.forward(request, response);

    }

    private void createRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        RoomCategoryDAO roomCategoryDAO = new RoomCategoryDAO(ConnectionManager.getConnection());

        List<RoomCategory> roomCategoryList = roomCategoryDAO.list();

        if (roomCategoryList.isEmpty()) {

            request.setAttribute("errorMessage", "No existen categorías, cree una");

            dispatcher = request.getRequestDispatcher("/admin/room-category/create.jsp");

            dispatcher.forward(request, response);
        } else {

            request.setAttribute("roomCategoryList", roomCategoryList);

            dispatcher = request.getRequestDispatcher("/admin/room/create.jsp");

            dispatcher.forward(request, response);
        }

    }

    private void getSingleRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int roomId = Integer.parseInt(request.getParameter("roomId"));

        Room room = roomDAO.read(roomId);
        
        room.setCategoryName(roomDAO.getCategoryName(room.getCategoryId()));

        request.setAttribute("room", room);

        dispatcher = request.getRequestDispatcher("/admin/room/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        
        Room room = roomDAO.read(roomId);
        
        RoomCategoryDAO roomCategoryDAO = new RoomCategoryDAO(ConnectionManager.getConnection());

        List<RoomCategory> roomCategoryList = roomCategoryDAO.list();
        
        request.setAttribute("room", room);
        
        request.setAttribute("roomCategoryList", roomCategoryList);

        dispatcher = request.getRequestDispatcher("/admin/room/edit.jsp");
        
        dispatcher.forward(request, response);
    }

    private void deleteRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {
        String roomId = request.getParameter("roomId");

        roomDAO.delete(Integer.parseInt(roomId));

        request.setAttribute("message", "Categoría de Cuarto eliminada");

        listRoom(request, response);

    }
}
