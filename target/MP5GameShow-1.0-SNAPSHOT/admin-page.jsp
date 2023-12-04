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
            if (session.getAttribute("username") == null || !((String)session.getAttribute("username")).equals("JohanLibertad")) {
                response.sendRedirect(request.getContextPath() + "/login_page.jsp");
                return;
            }
            Player[] ranking = (Player[])sesh.getAttribute("ranking");
        %>

        <main>
            <h1>Hello Johan!</h1>

            <h3>Add a player</h3>
            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <label>Enter username:</label><input type="text">
                <input type="submit" value="ADD">
            </form>

            <h3>Delete a player</h3>
            <form action="<%=request.getContextPath()%>/monster" method="POST">
                <label>Enter username: </label><input type="text"><br>
                <label>Enter score: </label><input type="number"><br>
                <input type="submit" value="DELETE">
            </form>
            <ol>
                <% if (ranking != null){
                    for (Player p : ranking) {%>
                        <li><p>p.name</p> <p>p.score</p></li>
                <%  } 
                 }%>
                
            </ol>
        </main>
    </body>
</html>
