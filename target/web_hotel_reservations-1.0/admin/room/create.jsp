<%-- 
    Document   : register
    Created on : 21 ene 2023, 21:59:17
    Author     : nesch
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
        <title>Crear Cuarto</title>
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md grid grid-cols-12">
            <h1 class="text-2xl font-medium col-span-11">Sistema de Reservaci�n del Hotel El Cisne: Crear Cuarto</h1>
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
                            Categor�as
                        </a>
                        <a href="${pageContext.request.contextPath}/room" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-hotel mr-2"></i>
                            Cuartos
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/payment-method" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-money-bill mr-2"></i>
                            M�todos de Pago
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
        <div class="bg-gray-200 flex justify-center items-center my-5">
            <form class="w-3/4 border border-blue-200 rounded bg-white p-5"
                  action = "${pageContext.request.contextPath}/room"
                  method="POST">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null && errorMessage.length() != 0) {
                %>
                <div class="bg-red-500 text-white py-2 px-4 m-2 rounded">
                    <%= errorMessage %>
                </div>
                <%
                    }
                %>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="email">
                        N�mero de Cuarto *
                    </label>
                    <select
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        id="roomNumber"
                        name="roomNumber"
                        >
                        <option value="1">
                            1   
                        </option>
                        <option value="2">
                            2   
                        </option>
                        <option value="3">
                            3   
                        </option>
                        <option value="4">
                            4   
                        </option>
                        <option value="5">
                            5   
                        </option>
                        <option value="6">
                            6   
                        </option>
                        <option value="7">
                            7   
                        </option>
                        <option value="8">
                            8   
                        </option>
                    </select>
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="email">
                        N�mero de Piso *
                    </label>
                    <select
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        id="floor"
                        name="floor"
                        >
                        <option value="1">
                            1   
                        </option>
                        <option value="2">
                            2   
                        </option>
                        <option value="3">
                            3   
                        </option>
                        <option value="4">
                            4   
                        </option>
                        <option value="5">
                            5   
                        </option>
                        <option value="6">
                            6   
                        </option>
                        <option value="7">
                            7   
                        </option>
                    </select>
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="email">
                        Categor�a *
                    </label>
                    <select
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        id="categoryId"
                        name="categoryId"
                        >
                        <c:forEach items="${roomCategoryList}" var="category">
                            <option value="${category.categoryId}">
                                ${category.categoryName}    
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <button class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-full">
                    Registrar
                </button>
            </form>
        </div>   
    </body>
</html>
