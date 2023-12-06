<%-- 
    Document   : login_page
    Created on : 11 23, 23, 11:08:18 AM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Who Wants to?</title>
    </head>
    <body>
        <%
            // the checked attribute only exists in the created session
            // so this is always true for "bare" sessions
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            if (session.getAttribute("username") != null && session.getAttribute("level") != null) {
                response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
            }   
        %>
        <main class="flex-centered">
            <h1>Who wants to?</h1>
            <form action="/MP5GameShow/LoginServlet" method="post">
                <label for="username">Enter a Username: </label><input type="text" name="username" placeholder="username" required/>
                <input class="blue-hover" type="submit" name="submit">
            </form>
        </main>
        
    </body>
</html>
