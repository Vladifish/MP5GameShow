<%-- 
    Document   : redirectpage
    Created on : 11 23, 23, 4:06:36 PM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Player Not Logged In! Redirecting</h1>
        <% 
            response.sendRedirect(request.getContextPath() + "/login_page.jsp");
        %>
    </body>
</html>
