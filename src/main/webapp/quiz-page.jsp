<%-- 
    Document   : quiz-page
    Created on : 11 23, 23, 11:08:47 AM
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/quiz_page.css">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            HttpSession sesh = request.getSession(false);
            if (sesh == null || sesh.getAttribute("username") == null || 
                sesh.getAttribute("level") == null) {
               response.sendRedirect("/MP5GameShow/redirectpage.jsp");
            }
            
            String level = (String)sesh.getAttribute("level");
            
            if (level.equals("99")) {
                response.sendRedirect("/MP5GameShow/victory_page.jsp");
            }
            
            String[][] question = (String[][])sesh.getAttribute("current-question");
        %>
        <a href="loss-page.jsp">Give Up</a>
        <section id="question-section"> 
            <h1>Question #<%= level%></h1>
            <h3><%= question[0][0]%></h3>
        </section>
        
        <section id="answer-section">
            <form class="quiz-form" action="/MP5GameShow/QuizServlet" method="post">
            <input type="radio" id="radioA" name="answer" value="A" required>
            <label for="radioA"><%= question[1][0] %></label>
            
            <input type="radio" id="radioB" name="answer" value="B">
            <label for="radioB"><%= question[1][1] %></label>
            
            <input type="radio" id="radioC" name="answer" value="C">
            <label for="radioC"><%= question[1][2] %></label>
            
            <input type="radio" id="radioD" name="answer" value="D">
            <label for="radioD"><%= question[1][3] %></label>
            
            <input type="submit">
        </form>
        </section>
        
        
    </body>
</html>
