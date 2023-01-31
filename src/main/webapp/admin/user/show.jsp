<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Categorias de Cuarto</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@1.8.10/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Sistema de Reservación del Hotel El Cisne: Información de Categoría de Cuartos</h1>
        </div>
        <div class="w-full h-full flex items-center justify-center mt-5">
            <div class="w-3/4 h-3/4 rounded-lg shadow-md flex">
                <div class="w-1/3 p-4">
                    <!-- Información del card -->
                    <div class="text-gray-900 font-bold text-xl mb-2">Categoría: <span class="font-normal"><c:out value="${roomCategory.categoryName}"/></span> </div>
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-info-circle"></i>
                        Descripción
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${roomCategory.categoryDescription}"/>
                    </p>
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-users"></i>
                        Capacidad
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${roomCategory.capacity}"/>
                        Persona
                    </p>
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-bed"></i>
                        Número de Camas
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${roomCategory.beds}"/>
                    </p>
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-dollar-sign"></i>
                        Precio
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${roomCategory.price}"/>
                    </p>

                    <a  class="bg-indigo-500 hover:bg-indigo-700 text-white py-2 px-4 rounded-lg"
                        href="${pageContext.request.contextPath}/room?action=list&roomCategoryId=${roomCategory.categoryId}"
                        target="_blank">
                        <i class="fas fa-eye"></i> Ver Cuartos
                    </a>
                </div>
                <div class="w-2/3">
                    <img src="http://static1.eskypartners.com/travelguide/vancouver-hotels.jpg" alt="Tu imagen" class="w-full h-full rounded-lg">
                </div>
            </div>
        </div>


        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js"></script>
    </body>
</html>
