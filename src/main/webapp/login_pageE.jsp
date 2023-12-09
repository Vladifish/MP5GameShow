<%-- 
    Document   : login_pageE
    Created on : 11 30, 23, 12:31:35 PM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <link rel="stylesheet" href="css/planet-animation.css">
        <title>Who Wants to Be?</title>
    </head>
    <body>
        <%
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            if (session.getAttribute("username") != null && session.getAttribute("level") != null) {
                response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
            }   
        %>
        <main class="flex-centered">
            <h1>WHO Wants.. to?</h1>
            <form action="/MP5GameShow/LoginServlet" method="post">
                <label for="username">Enter a Username: </label><input type="text" name="username" placeholder="username" required/>
                <input  class="blue-hover" type="submit" name="submit" value="ENTER">
            </form>
            <p class="username-error red-txt">Username must have at most 10 characters</p>
            <div class="planet">
                <img src="https://64.media.tumblr.com/059efe36a192f0753efc940016a1fb79/tumblr_mwmyr9yp6j1sk6m5ho1_500.png">
            </div>
        </main>
    </body>
</html>
