<%-- 
    Document   : victory_page
    Created on : 11 23, 23, 11:09:08 AM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="res.Player"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Who Wants to?</title>
    </head>
    <body>
        <% 
            HttpSession sesh = request.getSession();
            if (sesh.getAttribute("username") == null || sesh.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            int level = Integer.parseInt((String)sesh.getAttribute("level"));
            if (level != 99) {
                response.sendRedirect(request.getContextPath() + "/redirectpage.jsp");
                return;
            }
            
            Player[] ranking = (Player[])sesh.getAttribute("ranking");
        %>
        <main>
            <h1>Congratulations <%= sesh.getAttribute("username") %>!</h1>
            <h3>Your Final Score is: $<%= sesh.getAttribute("score")%></h3>
            <!-- Redirect Here -->
            <a href="login_page.jsp">Try Again?</a>
        </main>
            
            <ol type="1">
                <%for(int i=0; i<ranking.length; i++){ 
                    Player p = ranking[i];
                %>
                    <li><p><%=p.name%></p> <p>$<%=p.score%></p></li>
                <% }%>    
            </ol>
        <!-- fixed scrolling leaderboard here-->
        <%
            sesh.removeAttribute("username");
            sesh.removeAttribute("score");
            sesh.removeAttribute("level");
            sesh.removeAttribute("checked");
        %>
    </body>
</html>
