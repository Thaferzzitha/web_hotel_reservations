<%
session.invalidate();
response.sendRedirect(pageContext.request.contextPath + "index.jsp");
%>