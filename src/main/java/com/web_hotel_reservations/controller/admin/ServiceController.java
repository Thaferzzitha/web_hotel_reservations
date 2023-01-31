/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.Service;
import com.web_hotel_reservations.model.dao.PackServiceDAO;
import com.web_hotel_reservations.model.dao.ServiceDAO;
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
public class ServiceController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    ServiceDAO serviceDAO;

    public ServiceController() throws SQLException, ClassNotFoundException {
        this.serviceDAO = new ServiceDAO(ConnectionManager.getConnection());
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
                    listService(request, response);
                    break;

                case "create":
                    dispatcher = request.getRequestDispatcher("/admin/service/create.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "show":
                    getSingleService(request, response);
                    break;

                case "edit":
                    editService(request, response);
                    break;

                case "delete":
                    deleteService(request, response);
                    break;

                default:
                    listService(request, response);
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
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
            request.getParameter("serviceName"),
            request.getParameter("servicePrice")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/service/create.jsp").forward(request, response);
        }

        String serviceName = request.getParameter("serviceName");
        String serviceId = request.getParameter("serviceId");

        try {

            if (serviceId == null || serviceId.isEmpty()) {
                if (serviceDAO.checkUniqueName(serviceName, "register", 1) == false) {
                    System.out.println("Servicio ya registrado");
                    message = "Servicio ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/service/create.jsp").forward(request, response);
                } else {
                    registerService(
                            request,
                            response
                    );
                }

            } else {
                if (serviceDAO.checkUniqueName(serviceName, "edit", Integer.parseInt(serviceId)) == false) {
                    System.out.println("Servicio ya registrado");
                    message = "Servicio ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/service/edit.jsp").forward(request, response);
                } else {
                    updateService(
                            request,
                            response
                    );
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/service/create.jsp").forward(request, response);
        }
    }

    private void registerService(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String serviceName = request.getParameter("serviceName");
        float servicePrice = Float.parseFloat(request.getParameter("servicePrice"));
        boolean payByPerson = false;
        boolean payByDay = false;
        if (request.getParameter("payByPerson") != null) {
            payByPerson = true;
        }
        if (request.getParameter("payByDay") != null) {
            payByDay = true;
        }

        Service service = new Service(
                serviceName.toUpperCase(),
                servicePrice,
                payByPerson,
                payByDay
        );

        serviceDAO.create(service);

        String message = "Servicio registrado";
        request.setAttribute("message", message);
        listService(request, response);
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String serviceName = request.getParameter("serviceName");
        float servicePrice = Float.parseFloat(request.getParameter("servicePrice"));
        boolean payByPerson = false;
        boolean payByDay = false;
        if (request.getParameter("payByPerson") != null) {
            payByPerson = true;
        }
        if (request.getParameter("payByDay") != null) {
            payByDay = true;
        }

        Service service = new Service(
                serviceName.toUpperCase(),
                servicePrice,
                payByPerson,
                payByDay
        );
        service.setServiceId(serviceId);

        serviceDAO.update(service);

        String message = "Servicio actualizado";
        request.setAttribute("message", message);
        listService(request, response);
    }

    private void listService(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        List<Service> serviceList = serviceDAO.list();

        System.out.println(serviceList);

        request.setAttribute("serviceList", serviceList);

        dispatcher = request.getRequestDispatcher("/admin/service/index.jsp");

        dispatcher.forward(request, response);

    }

    private void getSingleService(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        Service service = serviceDAO.read(serviceId);
        
        PackServiceDAO packServiceDAO = new PackServiceDAO(ConnectionManager.getConnection());
        
        List<Pack> packServiceList = packServiceDAO.listPackagesByService(serviceId);
        
        List<Pack> notInList = packServiceDAO.listNotInPackagesByService(serviceId);

        request.setAttribute("packServiceList", packServiceList);
        
        request.setAttribute("notInList", notInList);

        request.setAttribute("service", service);

        dispatcher = request.getRequestDispatcher("/admin/service/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editService(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        Service service = serviceDAO.read(serviceId);

        request.setAttribute("service", service);

        dispatcher = request.getRequestDispatcher("/admin/service/edit.jsp");
        
        dispatcher.forward(request, response);
    }

    private void deleteService(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {
        String serviceId = request.getParameter("serviceId");

        serviceDAO.delete(Integer.parseInt(serviceId));

        request.setAttribute("message", "Servicio Eliminado");

        listService(request, response);

    }    
}
