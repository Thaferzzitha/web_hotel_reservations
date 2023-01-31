 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.model.PackService;
import com.web_hotel_reservations.model.dao.PackServiceDAO;
import com.web_hotel_reservations.model.factory.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
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
 * @author nesch
 */
public class PackServiceController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    PackServiceDAO packServiceDAO;

    public PackServiceController() throws SQLException, ClassNotFoundException {
        this.packServiceDAO = new PackServiceDAO(ConnectionManager.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            deletePackService(request, response);
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
            registerPackService(
                    request,
                    response
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/pack/show.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PackServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerPackService(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int packId = Integer.parseInt(request.getParameter("packId"));

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        String goTo = request.getParameter("goTo");

        PackService packService = new PackService();

        packService.setPackId(packId);

        packService.setServiceId(serviceId);

        packServiceDAO.create(packService);

        String message = "Servicio registrado en el paquete";

        request.setAttribute("message", message);
        request.setAttribute("goTo", goTo);

        if (goTo.equals("pack")) {
            PackController packController = new PackController();
            packController.doGet(request, response);
        } else {
            ServiceController serviceController = new ServiceController();
            serviceController.doGet(request, response);
        }

    }

    private void deletePackService(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        int packId = Integer.parseInt(request.getParameter("packId"));

        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        
        String goTo = request.getParameter("goTo");

        packServiceDAO.delete(packId, serviceId);

        String message = "Servicio eliminado del paquete";

        request.setAttribute("message", message);
        request.setAttribute("goTo", goTo);

        if (goTo.equals("pack")) {
            PackController packController = new PackController();
            packController.doGet(request, response);
        } else {
            ServiceController serviceController = new ServiceController();
            serviceController.doGet(request, response);
        }

    }
}
