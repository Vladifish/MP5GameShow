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
        <link rel="stylesheet" href="css/globals/leaderboard.css">
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
        <header>
            <h4>Who Wants to?</h4>
            <a href="redirectpage.jsp">Go back</a>
        </header>
        <main>
            <h1>Hello Johan!</h1>

            <h3>Add/Delete a player</h3>
            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <label>Enter username:</label><input type="text" name="username" required>
                <label>Enter score: </label><input type="number" name="score"><br>
                <input type="submit" name="add_player" value="ADD">
                <input type="submit" name="delete_player" value="DELETE">
            </form>

            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <input class="session-delete" type="submit" name="explode_session" value="HARD RESET">
            </form>
            <% if (session.getAttribute("player-add-check") != null) {%>
            <p class="error-message"><%= session.getAttribute("player-add-check") %></p>
            <% }%>
            <h3>Leaderboard</h3>
            <ol>
                <% if (ranking != null){
                    for (Player p : ranking) {%>
                        <div class="line-1px"></div>
                        <li><b><%=p.name%></b> <p>$<%=p.score%></p></li>
                <%  } 
                 }%>
            </ol>
            <% %>
        </main>
    </body>
</html>
