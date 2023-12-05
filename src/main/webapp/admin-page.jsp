<%-- 
    Document   : admin-page
    Created on : 12 4, 23, 8:43:15 PM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="res.Player"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            if (session.getAttribute("username") == null || !((String)session.getAttribute("username")).equals("JohanLibertad")) {
                response.sendRedirect(request.getContextPath() + "/login_page.jsp");
                return;
            }
            Player[] ranking = (Player[])session.getAttribute("ranking");
        %>

        <main>
            <a href="redirectpage.jsp">Go back</a>
            <h1>Hello Johan!</h1>

            <h3>Add/Delete a player</h3>
            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <label>Enter username:</label><input type="text" name="username" required>
                <label>Enter score: </label><input type="number" name="score"><br>
                <input type="submit" name="add_player" value="ADD">
                <input type="submit" name="delete_player" value="DELETE">
            </form>
                
            <h3>Leaderboard</h3>
            <ol>
                <% if (ranking != null){
                    for (Player p : ranking) {%>
                        <li><p><%=p.name%></p> <p><%=p.score%></p></li>
                <%  } 
                 }%>
            </ol>
            <% %>
        </main>
    </body>
</html>
