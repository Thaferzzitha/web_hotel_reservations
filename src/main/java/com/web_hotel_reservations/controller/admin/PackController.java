/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.Service;
import com.web_hotel_reservations.model.dao.PackDAO;
import com.web_hotel_reservations.model.dao.PackServiceDAO;
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
 * @author nesch
 */
public class PackController extends HttpServlet {
    RequestDispatcher dispatcher = null;
    PackDAO packDAO;
    
    public PackController() throws SQLException, ClassNotFoundException {
        this.packDAO = new PackDAO(ConnectionManager.getConnection());
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
        
        String goTo = (String)request.getAttribute("goTo");
        
        if (goTo != null) {
            action = "show";
        }

        if (action == null) {
            action = "list";
        }

        try {

            switch (action) {

                case "list":
                    listPack(request, response);
                    break;

                case "create":
                    dispatcher = request.getRequestDispatcher("/admin/pack/create.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "show":
                    getSinglePack(request, response);
                    break;

                case "edit":
                    editPack(request, response);
                    break;

                case "delete":
                    deletePack(request, response);
                    break;

                default:
                    listPack(request, response);
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PackController.class.getName()).log(Level.SEVERE, null, ex);
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
            request.getParameter("name")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/pack/create.jsp").forward(request, response);
        }

        String name = request.getParameter("name");
        String packId = request.getParameter("packId");

        try {

            if (packId == null || packId.isEmpty()) {
                if (packDAO.checkUniqueName(name, "register", 1) == false) {
                    System.out.println("Paquete ya registrado");
                    message = "Paquete ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/pack/create.jsp").forward(request, response);
                } else {
                    registerPack(
                            request,
                            response
                    );
                }

            } else {
                if (packDAO.checkUniqueName(name, "edit", Integer.parseInt(packId)) == false) {
                    System.out.println("Paquete ya registrado");
                    message = "Paquete ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/pack/edit.jsp").forward(request, response);
                } else {
                    updatePack(
                            request,
                            response
                    );
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/pack/create.jsp").forward(request, response);
        }
    }

    private void registerPack(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String name = request.getParameter("name");
        
        int discount = 0;
        
        if (request.getParameter("discount") != null) {
            discount = Integer.parseInt(request.getParameter("discount"));
        }
        
        Pack pack = new Pack(
                name.toUpperCase(),
                discount
        );

        packDAO.create(pack);

        String message = "Paquete registrado";
        request.setAttribute("message", message);
        listPack(request, response);
    }

    private void updatePack(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        int packId = Integer.parseInt(request.getParameter("packId"));
        
        String name = request.getParameter("name");
        
        int discount = 0;
        
        if (request.getParameter("discount") != null) {
            discount = Integer.parseInt(request.getParameter("discount"));
        }
        
        Pack pack = new Pack(
                name.toUpperCase(),
                discount
        );
        pack.setPackId(packId);

        packDAO.update(pack);

        String message = "Paquete actualizado";
        request.setAttribute("message", message);
        listPack(request, response);
    }

    private void listPack(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        List<Pack> packList = packDAO.list();

        request.setAttribute("packList", packList);

        dispatcher = request.getRequestDispatcher("/admin/pack/index.jsp");

        dispatcher.forward(request, response);

    }

    private void getSinglePack(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int packId = Integer.parseInt(request.getParameter("packId"));

        Pack pack = packDAO.read(packId);
        
        PackServiceDAO packServiceDAO = new PackServiceDAO(ConnectionManager.getConnection());
        
        List<Service> servicePackageList = packServiceDAO.listServicesByPackage(packId);
        
        List<Service> notInList = packServiceDAO.listNotInServicesByPackage(packId);

        request.setAttribute("servicePackageList", servicePackageList);
        
        request.setAttribute("notInList", notInList);

        request.setAttribute("pack", pack);

        dispatcher = request.getRequestDispatcher("/admin/pack/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editPack(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int packId = Integer.parseInt(request.getParameter("packId"));

        Pack pack = packDAO.read(packId);

        request.setAttribute("pack", pack);

        dispatcher = request.getRequestDispatcher("/admin/pack/edit.jsp");
        
        dispatcher.forward(request, response);
    }

    private void deletePack(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {
        String packId = request.getParameter("packId");

        packDAO.delete(Integer.parseInt(packId));

        request.setAttribute("message", "Paquete Eliminado");

        listPack(request, response);

    } 
}
