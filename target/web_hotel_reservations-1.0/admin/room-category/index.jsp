<%-- 
    Document   : register
    Created on : 21 ene 2023, 21:59:17
    Author     : THALIA
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.22/datatables.min.css"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.22/datatables.min.js"></script>
        <script src="https://cdn.tailwindcss.com"></script>
        <title>Lista de Categorias de Cuartos</title>
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md grid grid-cols-12">
            <h1 class="text-2xl font-medium col-span-11">Sistema de Reservación del Hotel El Cisne: Lista de Categorías de Cuarto</h1>
            <a href="${pageContext.request.contextPath}/user/show.jsp?action=show&userId=${userId}" 
               class="text-gray-600 hover:cursor-pointer hover:text-gray-800">
                <svg class="fill-current w-6 h-6" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <path d="M5 5a5 5 0 0 1 10 0v2A5 5 0 0 1 5 7V5zM0 16.68A19.9 19.9 0 0 1 10 14c3.64 0 7.06.97 10 2.68V20H0v-3.32z"/>
                </svg>
            </a>            
        </div>
        <header>
            <nav class="flex items-center justify-between flex-wrap bg-gray-900 p-6">
                <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
                    <div class="text-sm lg:flex-grow">
                        <a href="${pageContext.request.contextPath}/admin/pack" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-box-open mr-2"></i>
                            Paquetes
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/service" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-wrench mr-2"></i>
                            Servicios
                        </a>
                        <a href="${pageContext.request.contextPath}/room-categories" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-tags mr-2"></i>
                            Categorías
                        </a>
                        <a href="${pageContext.request.contextPath}/room" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-hotel mr-2"></i>
                            Cuartos
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/payment-method" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-money-bill mr-2"></i>
                            Métodos de Pago
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/pack" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-calendar-alt mr-2"></i>
                            Reservas
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/user" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-users mr-2"></i>
                            Usuarios
                        </a>
                    </div>
                </div>
            </nav>
        </header>
        <%
        String errorMessage = (String) request.getAttribute("message");
        if (errorMessage != null && errorMessage.length() != 0) {
        %>
        <div class="bg-green-500 text-white py-2 px-4 rounded w-3/4 my-5 mx-auto" id="message">
            ${message}
        </div>
        <%
            }
        %>

        <div class="w-full h-full flex items-center justify-center mt-5">
            <div class="px-4 py-2 w-full mx-10 rounded-lg shadow-md flex bg-white">
                <a href = "${pageContext.request.contextPath}/admin/room-category?action=create"
                   class="bg-indigo-500 hover:bg-indigo-700 text-white py-2 h-1/4 px-4 rounded-lg"
                   >Registrar Categoría</a> 
            </div>
        </div>

        <div class="w-full h-full flex items-center justify-center mt-5">

            <div class="px-4 py-2 w-full mx-10 rounded-lg shadow-md flex bg-white">
                <table class="table-fixed text-center mx-auto divide-y divide-gray-400">
                    <thead>
                        <tr>
                            <th class="px-9 py-2">Nombre de la categoría</th>
                            <th class="px-9 py-2">Descripción</th>
                            <th class="px-9 py-2">Capacidad</th>
                            <th class="px-9 py-2">N° Camas</th>
                            <th class="px-9 py-2">Precio</th>
                            <th class="px-9 py-2">Acciones</th>
                        </tr>

                    </thead>
                    <tbody class="divide-y divide-gray-300">
                        <c:forEach items="${roomCategoryList}" var="roomCategory">
                            <tr>
                                <td class="px-9 py-5">${roomCategory.categoryName}</td>
                                <td class="px-9 py-5">${roomCategory.categoryDescription}</td>
                                <td class="px-9 py-5">${roomCategory.capacity}</td>
                                <td class="px-9 py-5">${roomCategory.beds}</td>
                                <td class="px-9 py-5">${roomCategory.price}</td>
                                <td class="px-9 py-5">
                                    <a href = "${pageContext.request.contextPath}/admin/room-category?action=show&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-indigo-500 hover:bg-indigo-700 text-white py-1 px-2 mx-2 rounded-lg"
                                       >Ver</a> 
                                    <a href = "${pageContext.request.contextPath}/admin/room-category?action=edit&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-yellow-500 hover:bg-yellow-700 text-white py-1 px-2 mx-2 rounded-lg"
                                       >Editar</a> 
                                    <a href = "${pageContext.request.contextPath}/admin/room-category?action=delete&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-red-500 hover:bg-red-700 text-white py-1 px-2 mx-2 rounded-lg"
                                       >Eliminar</a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
            setTimeout(function () {
                document.getElementById("message").style.display = "none";
            }, 5000);
        </script>
        <script>
            $(document).ready(function () {
                $('#servicesTable').DataTable({
                    "language": {
                        "url": "https://cdn.datatables.net/plug-ins/1.13.1/i18n/es-ES.json"
                    }
                });
            });
        </script>
    </body>
</html>

