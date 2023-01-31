<%-- 
    Document   : register
    Created on : 21 ene 2023, 21:59:17
    Author     : nesch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
        <script src="https://cdn.tailwindcss.com"></script>
        <title>Crear Categoría</title>
    </head>
    <body class="bg-gray-200">
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Sistema de Reservación del Hotel El Cisne: Registrar Nueva Categoría</h1>
        </div>
        <div class="bg-gray-200 flex justify-center items-center h-screen my-3">
            <form class="w-3/4 border border-blue-200 rounded bg-white p-5"
                  action = "${pageContext.request.contextPath}/room-category"
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
                        Nombre *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="categoryName"
                        id="categoryName"
                        name="categoryName"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="categoryDescription">
                        Descripción *
                    </label>
                    <div class="relative">
                        <textarea class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                            type="text"
                            id="categoryDescription"
                            name="categoryDescription"
                            rows="4" 
                            cols="50"></textarea>          
                    </div>
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="capacity">
                        Capacidad (Personas) *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="number"
                        id="capacity"
                        name="capacity"
                        min="0" 
                        max="50"
                        placeholder="Ej: 10"
                        >
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="beds">
                        Número de Camas *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="number"
                        id="beds"
                        name="beds"
                        min="0" 
                        max="10"
                        placeholder="Ej: 10"
                        >
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="price">
                        Precio *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="number"
                        id="price"
                        name="price"
                        min="0" 
                        max="10000.00"
                        step="0.01" 
                        placeholder="Ej: 19.99"
                        >
                </div>
                <button class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-full">
                    Registrar
                </button>

            </form>
        </div>   
    </body>
</html>
