/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web_hotel_reservations.controller.admin;

import com.web_hotel_reservations.controller.UserController;
import com.web_hotel_reservations.controller.handler.ErrorHandler;
import com.web_hotel_reservations.model.Pack;
import com.web_hotel_reservations.model.PaymentMethod;
import com.web_hotel_reservations.model.Service;
import com.web_hotel_reservations.model.dao.PackServiceDAO;
import com.web_hotel_reservations.model.dao.PaymentMethodDAO;
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
public class PaymentMethodController extends HttpServlet {
    RequestDispatcher dispatcher = null;
    
    PaymentMethodDAO paymentMethodDAO;
    
    public PaymentMethodController() throws SQLException, ClassNotFoundException {
        this.paymentMethodDAO = new PaymentMethodDAO(ConnectionManager.getConnection());
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
                    listPaymentMethod(request, response);
                    break;

                case "create":
                    dispatcher = request.getRequestDispatcher("/admin/payment-method/create.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "show":
                    getSinglePaymentMethod(request, response);
                    break;

                case "edit":
                    editPaymentMethod(request, response);
                    break;

                case "delete":
                    deletePaymentMethod(request, response);
                    break;

                default:
                    listPaymentMethod(request, response);
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
            request.getParameter("methodName")
        };
        if (error.isNotEmptyField(fields) == false) {
            System.out.println("Los campos con * son obligatorios");
            message = "Los campos con * son obligatorios";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/payment-method/create.jsp").forward(request, response);
        }

        String methodName = request.getParameter("methodName");
        String paymentMethodId = request.getParameter("paymentMethodId");

        try {

            if (paymentMethodId == null || paymentMethodId.isEmpty()) {
                if (paymentMethodDAO.checkUniqueName(methodName, "register", 1) == false) {
                    System.out.println("Método de Pago ya registrado");
                    message = "Método de Pago ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/payment-method/create.jsp").forward(request, response);
                } else {
                    registerPaymentMethod(
                            request,
                            response
                    );
                }

            } else {
                if (paymentMethodDAO.checkUniqueName(methodName, "edit", Integer.parseInt(paymentMethodId)) == false) {
                    System.out.println("Método de Pago ya registrado");
                    message = "Método de Pago ya registrado";
                    request.setAttribute("errorMessage", message);
                    request.getRequestDispatcher("/admin/payment-method/edit.jsp").forward(request, response);
                } else {
                    updatePaymentMethod(
                            request,
                            response
                    );
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            message = "ERROR";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/payment-method/create.jsp").forward(request, response);
        }
    }

    private void registerPaymentMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String methodName = request.getParameter("methodName");
        
        PaymentMethod paymentMethod = new PaymentMethod(
                methodName.toUpperCase()
        );

        paymentMethodDAO.create(paymentMethod);

        String message = "Método de Pago registrado";
        request.setAttribute("message", message);
        listPaymentMethod(request, response);
    }

    private void updatePaymentMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        String methodName = request.getParameter("methodName");
        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
        
        PaymentMethod paymentMethod = new PaymentMethod(
                methodName.toUpperCase()
        );
        
        paymentMethod.setPaymentMethodId(paymentMethodId);

        paymentMethodDAO.update(paymentMethod);

        String message = "Método de Pago actualizado";
        request.setAttribute("message", message);
        listPaymentMethod(request, response);
    }

    private void listPaymentMethod(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        List<PaymentMethod> paymentMethodList = paymentMethodDAO.list();

        request.setAttribute("paymentMethodList", paymentMethodList);

        dispatcher = request.getRequestDispatcher("/admin/payment-method/index.jsp");

        dispatcher.forward(request, response);

    }

    private void getSinglePaymentMethod(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {

        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));

        PaymentMethod paymentMethod = paymentMethodDAO.read(paymentMethodId);

        request.setAttribute("paymentMethod", paymentMethod);

        dispatcher = request.getRequestDispatcher("/admin/payment-method/show.jsp");

        dispatcher.forward(request, response);
    }

    private void editPaymentMethod(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {

        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));

        PaymentMethod paymentMethod = paymentMethodDAO.read(paymentMethodId);

        request.setAttribute("paymentMethod", paymentMethod);

        dispatcher = request.getRequestDispatcher("/admin/payment-method/edit.jsp");
        
        dispatcher.forward(request, response);
    }

    private void deletePaymentMethod(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, ServletException, IOException {
        
        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));

        paymentMethodDAO.delete(paymentMethodId);

        request.setAttribute("message", "Método de Pago Eliminado");

        listPaymentMethod(request, response);

    } 
    
}
