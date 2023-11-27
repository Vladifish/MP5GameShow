<%-- 
    Document   : victory_page
    Created on : 11 23, 23, 11:09:08 AM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Who Wants to Be?</title>
    </head>
    <body>
        <% 
            HttpSession sesh = request.getSession(false);
            if (sesh == null || sesh.getAttribute("username") == null || 
                sesh.getAttribute("level") == null || 
                !sesh.getAttribute("level").equals((Object)"99")) {
               response.sendRedirect("/MP5GameShow/redirectpage.jsp");
            }
            
        %>
        <h1>Congratulations <%= sesh.getAttribute("username") %>!</h1>
        <!-- fixed scrolling leaderboard here-->
        
        <!-- Redirect Here -->
        <a href="login_page.jsp">Try Again?</a>
        <%
            sesh.invalidate();
        %>
    </body>
</html>
