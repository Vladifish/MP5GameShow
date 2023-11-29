<%-- 
    Document   : genericerror
    Created on : 11 23, 23, 9:54:05 PM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Oops Something Went Wrong!</h1>
        <a href="redirectpage.jsp">Go back</a>
        <% 
            HttpSession sesh = request.getSession(false);
            if (sesh != null)
                sesh.invalidate();
        %>
    </body>
</html>
