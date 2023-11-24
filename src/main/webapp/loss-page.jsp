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
        <title>JSP Page</title>
        
    </head>
    <body>
        <h1>Uh Oh! You Lost</h1>
        <a href="login_page.jsp">Try Again?</a>
        <% 
            if (session != null) {
                session.invalidate();
            }
        %>
    </body>
</html>
