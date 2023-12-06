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
        <link rel="stylesheet" href="css/globals/main.css">
        <title>Who Wants to?</title>
    </head>
    <body>
        <% 
            HttpSession sesh = request.getSession(false);
            if (session == null || session.getAttribute("checked") == null) {
                response.sendRedirect(request.getContextPath() + "/seshed");
                return;
            }
            
            if (sesh.getAttribute("username") == null || sesh.getAttribute("level") == null) {
                response.sendRedirect(request.getContextPath() + "/login_page.jsp");
                return;
            }
            
            String level = (String)sesh.getAttribute("level");
            if (Double.parseDouble(level) > 10) {
                response.sendRedirect(request.getContextPath() + "/victory_page.jsp");
                return;
            }
            String[][] question = (String[][])sesh.getAttribute("current-question");
            String username = (String)sesh.getAttribute("username");
            String score = (String)sesh.getAttribute("score");
        %>
        <% if (sesh != null) {%>
        <header>
            <h4>Who Wants to?</h4>
            <a href="loss-page.jsp" id="give-up-button">Give Up</a>
        </header>
        <main>
            <section id="question-section"> 
                <h1>Question #<%= level%></h1>
                <h3><%= username%>, <%= question[0][0]%></h3>
                <span class="score-text"><h3>Score: </h3><h3 class="green-txt">$<%= score%></h3></span>
            </section>

            <form class="quiz-form" action="/MP5GameShow/QuizServlet" method="post">
                    <input type="radio" id="radioA" name="answer" value="A" required>
                    <label for="radioA"><%= question[1][0] %></label>

                    <input type="radio" id="radioB" name="answer" value="B">
                    <label for="radioB"><%= question[1][1] %></label>

                    <input type="radio" id="radioC" name="answer" value="C">
                    <label for="radioC"><%= question[1][2] %></label>

                    <input type="radio" id="radioD" name="answer" value="D">
                    <label for="radioD"><%= question[1][3] %></label>
            <input class="blue-hover" type="submit" value="Final Answer">
        </form>
        </main>
        <%} %>
        
    </body>
</html>
