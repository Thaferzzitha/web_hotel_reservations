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
        <title>Registro</title>
    </head>
    <body>
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-medium">Bienvenido al sistema de reservación del Hotel El Cisne</h1>
            <div class="flex justify-end">
                <a href="${pageContext.request.contextPath}/auth/login.jsp" class="bg-indigo-500 text-white p-2 rounded-lg mr-2">Iniciar Sesión</a>
            </div>
        </div>
        <div class="bg-gray-100 flex justify-center items-center">
            <form class="w-1/3 border border-blue-200 rounded bg-white p-5"
                  action = "${pageContext.request.contextPath}/auth/register"
                  method="POST">
                <h2 class="text-center text-lg font-medium mb-4">Registro</h2>
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
                  Correo electrónico*
                </label>
                <input
                  class="bg-gray-200 border border-gray-400 rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:bg-white"
                  type="email"
                  id="email"
                  name="email"
                >
              </div>

              <div class="mb-4">
                <label class="block text-gray-700 font-medium mb-2" for="password">
                  Contraseña*
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
                Registrar
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
