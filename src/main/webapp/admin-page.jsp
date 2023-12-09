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
        <link rel="stylesheet" href="css/admin-page.css">
        <title>Kinderheim</title>
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

            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <h1>Add / Delete a player</h1>
                <label>Username</label><input type="text" name="username" required>
                <label>Score </label><input type="number" name="score"><br>
                <span>
                    <input class="soft-button green-button" type="submit" name="add_player" value="ADD">
                    <input class="soft-button red-button" type="submit" name="delete_player" value="DELETE">
                </span>
            </form>

            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <input class="hard-reset" type="submit" name="explode_session" value="HARD RESET">
            </form>
            <% if (session.getAttribute("player-add-check") != null) {%>
            <p class="error-message red-txt"><%= session.getAttribute("player-add-check") %></p>
            <% }%>
            <h1 class="red-txt">Hello Johan!</h1>
            <h1>The List</h1>
            <ol>
                <% if (ranking != null){
                    for (int i=0; i<ranking.length; i++) {
                        Player p = ranking[i];%>
                        <div class="line-1px"></div>
                        <li><b><%=i+1%>. <%=p.name%></b> <p>$<%=p.score%></p></li>
                <%  } 
                 }%>
            </ol>
            <% %>
        </main>
    </body>
</html>
