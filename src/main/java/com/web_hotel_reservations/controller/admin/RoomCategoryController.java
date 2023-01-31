/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.RoomCategory;
import com.web_hotel_reservations.model.dao.RoomCategoryDAO;
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
public class RoomCategoryController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    RoomCategoryDAO roomCategoryDAO;

    public RoomCategoryController() throws SQLException, ClassNotFoundException {
        this.roomCategoryDAO = new RoomCategoryDAO(ConnectionManager.getConnection());
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
                    listRoomCategory(request, response);
                    break;

                case "create":
                    dispatcher = request.getRequestDispatcher("/admin/room-category/create.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "show":
                    getSingleRoomCategory(request, response);
                    break;

                case "edit":
                    editRoomCategory(request, response);
                    break;

                case "delete":
                    deleteRoomCategory(request, response);
                    break;

                case "search":
                    searchRoomCategoryByName(request, response);
                    break;

                default:
                    listRoomCategory(request, response);
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
            request.getParameter("categoryName"),
            request.getParameter("categoryDescription"),
            request.getParameter("capacity"),
            request.getParameter("beds"),
            request.getParameter("price")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/room-category/create.jsp").forward(request, response);
        }

        String categoryName = request.getParameter("categoryName");
        String categoryId = request.getParameter("categoryId");

        try {

            if (categoryId == null || categoryId.isEmpty()) {
                if (roomCategoryDAO.checkUniqueName(categoryName, "register", 1) == false) {
                    System.out.println("Categoría ya registrada");
                    message = "Categoría ya registrada";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/room-category/create.jsp").forward(request, response);
                } else {
                    registerRoomCategory(
                            request,
                            response
                    );
                }

            } else {
                if (roomCategoryDAO.checkUniqueName(categoryName, "edit", Integer.parseInt(categoryId)) == false) {
                    System.out.println("Categoría ya registrada");
                    message = "Categoría ya registrada";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/room-category/edit.jsp").forward(request, response);
                } else {
                    updateRoomCategory(
                            request,
                            response
                    );
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/room-category/create.jsp").forward(request, response);
        }
    }

    private void registerRoomCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int beds = Integer.parseInt(request.getParameter("beds"));
        float price = Float.parseFloat(request.getParameter("price"));

        RoomCategory roomCategory = new RoomCategory(
                categoryName.toUpperCase(),
                categoryDescription,
                capacity,
                beds,
                price
        );

        roomCategoryDAO.create(roomCategory);

        String message = "Categoría registrada";
        request.setAttribute("message", message);
        listRoomCategory(request, response);
    }

    private void updateRoomCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int beds = Integer.parseInt(request.getParameter("beds"));
        float price = Float.parseFloat(request.getParameter("price"));

        RoomCategory roomCategory = new RoomCategory(
                categoryName.toUpperCase(),
                categoryDescription,
                capacity,
                beds,
                price
        );
        roomCategory.setCategoryId(categoryId);

        roomCategoryDAO.update(roomCategory);

        String message = "Categoría actualizada";
        request.setAttribute("message", message);
        listRoomCategory(request, response);
    }

    private void listRoomCategory(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        List<RoomCategory> roomCategoryList = roomCategoryDAO.list();

        System.out.println(roomCategoryList);

        request.setAttribute("roomCategoryList", roomCategoryList);

        dispatcher = request.getRequestDispatcher("/admin/room-category/index.jsp");

        dispatcher.forward(request, response);

    }

    private void getSingleRoomCategory(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("roomCategoryId"));

        RoomCategory roomCategory = roomCategoryDAO.read(categoryId);

        request.setAttribute("roomCategory", roomCategory);

        dispatcher = request.getRequestDispatcher("/admin/room-category/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editRoomCategory(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("roomCategoryId"));

        RoomCategory roomCategory = roomCategoryDAO.read(categoryId);

        request.setAttribute("roomCategory", roomCategory);

        dispatcher = request.getRequestDispatcher("/admin/room-category/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteRoomCategory(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {
        String categoryId = request.getParameter("roomCategoryId");

        roomCategoryDAO.delete(Integer.parseInt(categoryId));

        request.setAttribute("message", "Categoría de Cuarto eliminada");

        listRoomCategory(request, response);

    }

    private void searchRoomCategoryByName(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        String categoryName = request.getParameter("nameSearch");

        List<RoomCategory> roomCategoryList = roomCategoryDAO.listByName(categoryName);

        if (roomCategoryList.isEmpty()) {

            request.setAttribute("message", "No se encontraron resultados");

            listRoomCategory(request, response);
        }

        request.setAttribute("roomCategoryList", roomCategoryList);

        dispatcher = request.getRequestDispatcher("/admin/room-category/index.jsp");

        dispatcher.forward(request, response);
    }
}
