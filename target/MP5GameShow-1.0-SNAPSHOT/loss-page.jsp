<%-- 
    Document   : loss-page
    Created on : 11 23, 23, 3:54:23 PM
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
        <title>Who Wants to?</title>
        
    </head>
    <%
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            
            HttpSession sesh = session;
            
            if (sesh.getAttribute("username") == null || sesh.getAttribute("checked") == null ||
                sesh.getAttribute("level") == null) {
                response.sendRedirect(request.getContextPath() + "/login_page.jsp");
                return;
            }
            int level = Integer.parseInt((String)sesh.getAttribute("level"));
            if (level == 0) {
                response.sendRedirect(request.getContextPath() + "/login_page.jsp");
                return;
            }
            
            Player[] ranking = (Player[])sesh.getAttribute("ranking");
        %>
    <body>
        <header>
            <h4>Who Wants to?</h4>
        </header>
        <main>
            <h1>Uh Oh! You Lost</h1>
            <h3>Score: $<%=session.getAttribute("score")%></h1>
            <a href="login_page.jsp">Try Again?</a>
            <h1>The List</h1>
            <ol type="1">
                <% if (ranking != null){
                    for (int i=0; i<ranking.length; i++) {
                        Player p = ranking[i];%>
                        <div class="line-1px"></div>
                        <li><b><%=i+1%>. <%=p.name%></b> <p>$<%=p.score%></p></li>
                <%  } 
                 }%>
            </ol>
        </main>
        <% 
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
