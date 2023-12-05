<%-- 
    Document   : loss-page
    Created on : 11 23, 23, 3:54:23 PM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Who Wants to?</title>
        
    </head>
    <body>
        <main>
            <h1>Uh Oh! You Lost</h1>
            <a href="login_page.jsp">Try Again?</a>
        </main>
        <%
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            HttpSession sesh = request.getSession(false);
            if (sesh != null) {
                if (sesh.getAttribute("username") != null) {
                    sesh.removeAttribute("username");
                    sesh.removeAttribute("score");
                    sesh.removeAttribute("level");
                }
            }
        %>
    </body>
</html>
