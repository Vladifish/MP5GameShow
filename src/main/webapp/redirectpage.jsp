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
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Redirecting..</title>
    </head>
    <body class="slight-padding">
        <h1>Player Not Logged In! Redirecting</h1>
        <% 
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis()- start < 1500 ) {
            
            }
            session = request.getSession(false);
            // extra cleaning up
            if (session != null || session.getAttribute("username") != null) {
                session.removeAttribute("username");
            }
            response.sendRedirect(request.getContextPath() + "/login_page.jsp");
        %>
    </body>
</html>
