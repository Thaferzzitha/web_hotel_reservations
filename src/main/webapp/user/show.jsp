<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Perfil</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@1.8.10/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Sistema de Reservación del Hotel El Cisne</h1>
        </div>
        <div class="w-full h-full flex items-center justify-center mt-5">
            <div class="w-3/4 h-3/4 rounded-lg shadow-md flex">
                <div class="w-full p-4">
                    <!-- Información del card -->
                    <p class="text-m text-gray-600 flex items-center">
                        <i class="fill-current text-gray-500 w-5 h-5 mr-2 fas fa-envelope"></i>
                        Correo Electrónico
                    </p>
                    <p class="text-gray-700 text-base mb-5">
                        <c:out value="${user.email}"/>
                    </p>

                    <a  class="bg-yellow-500 hover:bg-yellow-700 text-white py-2 px-4 mx-5 rounded-lg"
                        href="${pageContext.request.contextPath}/user/edit.jsp?action=edit&userId=${user.userId}">
                        <i class="fas fa-pen"></i> Editar
                    </a>
                        
                    <a  class="bg-red-500 hover:bg-red-700 text-white py-2 px-4 rounded-lg"
                        href="${pageContext.request.contextPath}/auth/logout.jsp">
                        <i class="fas fa-circle-xmark"></i> Cerrar Sesión
                    </a>
                </div>
            </div>
        </div>
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js"></script>
    </body>
</html>
