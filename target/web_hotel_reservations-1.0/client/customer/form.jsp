<%-- 
    Document   : register
    Created on : 21 ene 2023, 21:59:17
    Author     : THALIA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
        <script src="https://cdn.tailwindcss.com"></script>
        <title>Registrar Cliente</title>
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
                        <a href="${pageContext.request.contextPath}/client/reservation" class="block mt-4 lg:inline-block lg:mt-0 text-gray-300 hover:text-white mr-4">
                            <i class="fas fa-calendar-alt mr-2"></i>
                            Reservas
                        </a>
                    </div>
                </div>
            </nav>
        </header>
        <div class="bg-gray-200 flex justify-center items-center  my-5">
            <form class="w-3/4 border border-blue-200 rounded bg-white p-5"
                  action = "${pageContext.request.contextPath}/customer"
                  method="POST">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    
                    if( session.getAttribute("userId") == null){
                        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                    } else {
                        int userId = (int)session.getAttribute("userId");
                    }
                    if (errorMessage != null && errorMessage.length() != 0) {
                %>
                <div class="bg-red-500 text-white py-2 px-4 m-2 rounded">
                    <%= errorMessage %>
                </div>
                <%
                    }
                %>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="firstName">
                        Nombre *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="text"
                        id="firstName"
                        name="firstName"
                        value="${customer.firstName}"
                        >
                </div>
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="lastName">
                        Apellido *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="text"
                        id="lastName"
                        name="lastName"
                        value="${customer.lastName}"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="identificationNumber">
                        Cédula *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="text"
                        id="identificationNumber"
                        name="identificationNumber"
                        value="${customer.identificationNumber}"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="email">
                        Correo Electónico *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="email"
                        id="email"
                        name="email"
                        value="${customer.email}"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="phone">
                        Teléfono *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="text"
                        id="phone"
                        name="phone"
                        max="10"
                        value="${customer.phone}"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="address">
                        Dirección
                    </label>
                    <div class="relative">
                        <textarea class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                            type="text"
                            id="address"
                            name="address"
                            rows="4" 
                            cols="50">${customer.address}</textarea>          
                    </div>
                </div>

                <button class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-full">
                    Registrar
                </button>
            </form>
        </div>   
    </body>
</html>
