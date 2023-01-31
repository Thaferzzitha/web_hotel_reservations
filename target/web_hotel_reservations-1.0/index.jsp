<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.0.3/dist/tailwind.min.css" rel="stylesheet">
        <title>Hotel El Cisne - Reservaciones</title>
    </head>
    <body class="bg-gray-100">

        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Bienvenido al sistema de reservación del Hotel El Cisne</h1>
            <div class="flex justify-end">
                <a href="${pageContext.request.contextPath}/auth/login.jsp" class="bg-indigo-500 text-white p-2 rounded-lg mr-2">Iniciar Sesión</a>
                <a href="${pageContext.request.contextPath}/auth/register.jsp" class="bg-indigo-500 text-white p-2 rounded-lg">Registrarse</a>
            </div>
        </div>

    </body>
</html>
