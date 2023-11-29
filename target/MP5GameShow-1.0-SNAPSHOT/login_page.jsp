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
        <title>Who Wants to Be?</title>
    </head>
    <body>
        <main>
            <h1>Who wants to be?</h1>
            <form action="/MP5GameShow/LoginServlet" method="post">
                <label for="username">Enter a Username: </label><input type="text" name="username" placeholder="username" required/>
                <input type="submit">
            </form>
        </main>
        <%
            HttpSession sesh = request.getSession(false);
            if(sesh != null) {
                Object uncasted_uname = session.getAttribute("username");
                if (uncasted_uname != null) {
                    response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
                    // Existing Session found restoring progress
                }
            }  
        %>
    </body>
</html>
