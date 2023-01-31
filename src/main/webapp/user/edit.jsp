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
        <title>Editar Perfil</title>
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
        <div class="bg-gray-200 flex justify-center items-center h-screen my-3">
            <form class="w-3/4 border border-blue-200 rounded bg-white p-5"
                  action = "${pageContext.request.contextPath}/user"
                  method="POST">
                <input type = "hidden" name = "userId" value = "${user.userId}"/>
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
                        Correo Electrónico *
                    </label>
                    <input
                        class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                        type="email"
                        id="email"
                        name="email"
                        value="${user.email}"
                        >
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 font-medium mb-2" for="password">
                        Contraseña
                    </label>
                    <div class="relative">
                        <input
                            class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                            type="password"
                            id="password"
                            name="password"
                            >
                        <span class="absolute right-0 top-0 m-2 cursor-pointer text-gray-500">
                            <i class="fas fa-eye" id="eye"></i>
                        </span>
                    </div>
                </div>
                <button class="bg-indigo-500 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-full">
                    Actualizar
                </button>

            </form>
        </div>   
        <script>
            const eye = document.getElementById("eye");
            const password = document.getElementById("password");
            eye.addEventListener("click", function () {
                if (password.type === "password") {
                    password.type = "text";
                    eye.classList.remove("fa-eye");
                    eye.classList.add("fa-eye-slash");
                } else {
                    password.type = "password";
                    eye.classList.add("fa-eye");
                    eye.classList.remove("fa-eye-slash");
                }
            });
        </script>
    </body>
</html>
