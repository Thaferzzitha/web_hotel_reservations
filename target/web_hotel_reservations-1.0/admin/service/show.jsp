<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Servicio</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@1.8.10/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md grid grid-cols-12">
            <h1 class="text-2xl font-medium col-span-11">Sistema de Reservación del Hotel El Cisne: Información de Servicio</h1>
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
            <div class="w-3/4 h-3/4 rounded-lg shadow-md flex  bg-white">
                <div class="w-full p-4">
                    <!-- Información del card -->
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-info"></i>
                        Nombre
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${service.serviceName}"/>
                    </p>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-dollar-sign"></i>
                        Precio
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${service.servicePrice}"/>
                    </p>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-money-bill"></i>
                        Pago por Persona
                    </p>

                    <p class="text-gray-700 text-base mb-5">
                        ${service.payByPerson == true ? "Si" : "No"}
                    </p>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-money-bill"></i>
                        Pago por Día
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        ${service.payByDay == true ? "Si" : "No"}
                    </p>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-plus"></i>
                        Agregar a Paquete
                    </p>

                    <form class="w-full flex border border-blue-200 rounded bg-white p-5 mb-5"
                          action = "${pageContext.request.contextPath}/admin/pack-service"
                          method="POST">
                        <button type="submit" class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 mr-2 rounded">
                            Agregar
                        </button>
                        <input type="hidden" id="serviceId" name="serviceId" value="${service.serviceId}">
                        <input type="hidden" id="goTo" name="goTo" value="service">
                        <select
                            class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                            id="packId"
                            name="packId"
                            >
                            <c:forEach items="${notInList}" var="pack">
                                <option value="${pack.packId}">
                                    ${pack.name}    
                                </option>
                            </c:forEach>
                        </select>
                    </form>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-minus"></i>
                        Quitar de Paquete
                    </p>

                    <form class="w-full flex border border-blue-200 rounded bg-white p-5 mb-5"
                          action = "${pageContext.request.contextPath}/admin/pack-service"
                          method="GET">
                        <button type="submit" class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 mr-2 rounded">
                            Quitar
                        </button>
                        <input type="hidden" id="serviceId" name="serviceId" value="${service.serviceId}">
                        <input type="hidden" id="goTo" name="goTo" value="service">
                        <select
                            class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                            id="packId"
                            name="packId"
                            >
                            <c:forEach items="${packServiceList}" var="pack">
                                <option value="${pack.packId}">
                                    ${pack.name}    
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
            </div>
        </div>


        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js"></script>
        <script>
            setTimeout(function () {
                document.getElementById("message").style.display = "none";
            }, 5000);
        </script>
    </body>
</html>
