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
        <script src="https://cdn.tailwindcss.com"></script>
        <title>Lista de Categorias de Cuartos</title>
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Sistema de Reservación del Hotel El Cisne: Lista de Categorías de Cuartos</h1>
        </div>
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
                <form class="w-full"
                      action = "${pageContext.request.contextPath}/room-category"
                      method="GET">
                    <input type="hidden" name="action" value="search">
                    <input type="text" name="nameSearch" class="px-4 py-2 w-3/4 rounded bg-white border border-blue-500" placeholder="Buscar categoría por nombre">
                    <button type="submit" class="ml-5 bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
                        Buscar
                    </button>
                </form>
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
                            <th class="px-9 py-2"></th>
                            <th class="px-9 py-2" colspan="2">
                                <a href = "${pageContext.request.contextPath}/room-category?action=create"
                                   class="bg-indigo-500 hover:bg-indigo-700 text-white py-2 h-1/4 px-4 rounded-lg"
                                   >Registrar Categoría</a> 
                            </th>
                        </tr>

                    </thead>
                    <tbody class=" divide-y divide-gray-300">
                        <c:forEach items="${roomCategoryList}" var="roomCategory">
                            <tr>
                                <td class="px-9 py-5">${roomCategory.categoryName}</td>
                                <td class="px-9 py-5">${roomCategory.categoryDescription}</td>
                                <td class="px-9 py-5">${roomCategory.capacity}</td>
                                <td class="px-9 py-5">${roomCategory.beds}</td>
                                <td class="px-9 py-5">${roomCategory.price}</td>
                                <td class="px-9 py-5">
                                    <a href = "${pageContext.request.contextPath}/room-category?action=show&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-indigo-500 hover:bg-indigo-700 text-white py-4 px-4 rounded-lg"
                                       >Ver</a> 
                                </td>
                                <td class="px-9 py-5">
                                    <a href = "${pageContext.request.contextPath}/room-category?action=edit&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-yellow-500 hover:bg-yellow-700 text-white py-4 px-4 rounded-lg"
                                       >Editar</a> 
                                </td>
                                <td class="px-9 py-5">
                                    <a href = "${pageContext.request.contextPath}/room-category?action=delete&roomCategoryId=${roomCategory.categoryId}"
                                       class="bg-red-500 hover:bg-red-700 text-white py-4 px-4 rounded-lg"
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
    </body>
</html>

