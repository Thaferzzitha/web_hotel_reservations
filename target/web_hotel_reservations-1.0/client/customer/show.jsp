<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cliente</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@1.8.10/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md grid grid-cols-12">
            <h1 class="text-2xl font-medium col-span-11">Sistema de Reservación del Hotel El Cisne</h1>
            <a href="#" class="text-gray-600 hover:cursor-pointer hover:text-gray-800">
                <svg class="fill-current w-6 h-6" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <path d="M5 5a5 5 0 0 1 10 0v2A5 5 0 0 1 5 7V5zM0 16.68A19.9 19.9 0 0 1 10 14c3.64 0 7.06.97 10 2.68V20H0v-3.32z"/>
                </svg>
            </a>            
        </div>
        <header>
            <nav class="flex items-center justify-between flex-wrap bg-gray-900 p-6">
                <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
                    <div class="text-sm lg:flex-grow">
                        <a href="${pageContext.request.contextPath}/reservation" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-calendar-alt mr-2"></i>
                            Mis Reservas
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
                    <div class="text-gray-900 font-bold text-xl mb-2">${customer.firstName} ${customer.lastName}</div>
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-info"></i>
                        Cédula
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${customer.identificationNumber}"/>
                    </p>

                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-envelope"></i>
                        Correo Electrónico
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${customer.email}"/>
                    </p>
                    
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-phone"></i>
                        Teléfono
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${customer.phone}"/>
                    </p>
                    
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-map"></i>
                        Dirección
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${customer.address}"/>
                    </p>
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
