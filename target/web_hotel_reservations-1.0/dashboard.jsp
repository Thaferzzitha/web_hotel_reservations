<%-- 
    Document   : dashboard
    Created on : 24 ene 2023, 22:53:54
    Author     : nesch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.0.3/dist/tailwind.min.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        
        <div class="bg-white p-6 rounded-lg shadow-md grid grid-cols-12">
            <h1 class="text-2xl font-medium col-span-11">Sistema de Reservación del Hotel El Cisne</h1>
            <a href="${pageContext.request.contextPath}/user/show.jsp?action=show&userId=${userId}" 
               class="text-gray-600 hover:cursor-pointer hover:text-gray-800">
                <svg class="fill-current w-6 h-6" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <path d="M5 5a5 5 0 0 1 10 0v2A5 5 0 0 1 5 7V5zM0 16.68A19.9 19.9 0 0 1 10 14c3.64 0 7.06.97 10 2.68V20H0v-3.32z"/>
                </svg>
            </a>            
        </div>

        <div class="flex justify-center mt-6 grid grid-cols-4 gap-4 w-3/4 mx-auto">
            <a href="${pageContext.request.contextPath}/reservation"
                class="bg-white p-4 rounded-lg mb-4 shadow-md hover:cursor-hand">Mis Reservaciones</a>
            <a href="#"
                class="bg-white p-4 rounded-lg mb-4 shadow-md hover:cursor-hand">Cuartos</a>
            <a href="#"
                class="bg-white p-4 rounded-lg mb-4 shadow-md hover:cursor-hand">Categorías</a>
            <a href="#"
                class="bg-white p-4 rounded-lg mb-4 shadow-md hover:cursor-hand">Servicios</a>
            <a href="#"
                class="bg-white p-4 rounded-lg mb-4 shadow-md hover:cursor-hand">Paquetes</a>
        </div>
    </body>
</html>
