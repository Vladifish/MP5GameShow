<%-- 
    Document   : error_page
    Created on : 11 23, 23, 11:10:54 AM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Error with login</title>
    </head>
    <body>
        <h1>Error, no username found</h1>
        <a href="login_page.jsp">Go back</a>
        <% 
            HttpSession sesh = request.getSession(false);
            if (sesh != null) {
                sesh.invalidate();
            }
        %>
    </body>
</html>
