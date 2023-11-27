/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
public class QuizServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("level") == null)
            request.getRequestDispatcher("login_page.jsp").forward(request,response);
        
        int level = Integer.parseInt((String)session.getAttribute("level"));
        
        // Different first-run
        if (level == 0) {
            session.setAttribute("question-queue", CreateQuestionQueue());
            session.setAttribute("score", "0");
        }
        else {
            String ans = request.getParameter("answer");
            if(!checkAnswer(session, ans)) // check if the player answered right
                request.getRequestDispatcher("loss-page.jsp").forward(request,response);
        }
        
        // before exit we update everything
        
        // Fix the questions
        setupPageQuestions(session);
        
        // Fix the current level
        level = (level < 10) ? level+1 : 99;
        session.setAttribute("level", level+"");
        
        if (level == 99) {
            request.getRequestDispatcher("victory_page.jsp").forward(request,response);
        }
        
        // Set the timer
        setTimer(session);
        
        response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
    }
    
    
    // Based my timing code on Stefan Evans' answer in
    // this chatroom
    // https://coderanch.com/t/282680/java/create-timer-jsp
    private void setTimer(HttpSession session) {
        long now = System.currentTimeMillis();
        long allotedTime = now + 120 * 1000;
        session.setAttribute("start-time", now+""); // casting to a string, since it's easier to work with it
        session.setAttribute("end-by-time", allotedTime+"");
    }
    
    private Deque<String[][]> CreateQuestionQueue() {
        Deque<String[][]> outQueue = new ArrayDeque<>();
        
        // Easy Questions
        selectRandomQuestions(0,10, outQueue);
        
        // Medium Questions
        selectRandomQuestions(10,10, outQueue);
        
        //Hard Questions
        selectRandomQuestions(20,9, outQueue);
        
        // Ultra Hard / Big Money Question
        outQueue.addLast(QuestionBank[29]);
        
        return outQueue;
    }
    
    private void selectRandomQuestions(int floor, int end, Deque<String[][]> queue) {
        
        // We generate three questions per difficulty
        for (int i=0; i < 3; i++) {
            int rand = (int)(Math.random() * end) + floor;
            queue.addLast(QuestionBank[rand]);
            
            // swap with last element to guarantee no repeat
            int lastEle = floor + end - 1;
            String[][] temp = QuestionBank[lastEle]; 
            QuestionBank[lastEle] = QuestionBank[rand];
            QuestionBank[rand] = temp;
            
            // adjust range so that we don't get duplicate questions
            end--;
        }
    }
    
    private boolean checkAnswer(HttpSession session, String ans) {
        // Notes: 
        // question[0][1] = correct answer
        // question[0][2] = difficulty
        
        String[][] question = (String[][])session.getAttribute("current-question");
        long time_end = System.currentTimeMillis();
        computeScore(session, question[0][2], time_end);
        
        // check if correct answer
        return ans.equals(question[0][1]);
        
    }
    
    // Points without multipliers
    enum BASE_SCORE {
        EASY(1000), 
        MEDIUM(3000), 
        HARD(5000), 
        ULTRA_HARD(5001);
        private final int score;
        
        private BASE_SCORE(int score) {
            this.score = score;
        }
        
        int get(){
            return score;
        }
    }
    private void computeScore(HttpSession session, String difficulty, long time_end) {
        long deadline = Long.parseLong((String)session.getAttribute("end-by-time"));
        double score = 0;
        
        switch(difficulty) {
            case "Ez":
                score = BASE_SCORE.EASY.get();
                break;
            case "Med":
                score = BASE_SCORE.MEDIUM.get();
                break;
            case "Hard":
                score = BASE_SCORE.HARD.get();
                break;
            case "Bonus":
                score = BASE_SCORE.ULTRA_HARD.get();
                break; // not needed just adding this for consistency
        }
        
        double mult = 1;
        double time_diff = (deadline - time_end);
        // test variables
        if (time_diff > 0) {
            mult += (time_diff / 120_000);
        }
        
        score *= mult;
        
        double new_score = Double.parseDouble((String)session.getAttribute("score")) + score;
        
        // truncates the score to 2 decimal places for readability
        session.setAttribute("score", String.format("%.2f",new_score));
        
        
    }
    
    private void setupPageQuestions(HttpSession session) {
        String[][] question = ((Deque<String[][]>)session.getAttribute("question-queue")).poll();
        session.setAttribute("current-question", question);
    }
    
    String[][][] QuestionBank = {
        
        // Easy Questions
        {{"0Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"9Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Ez"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Medium Questions
        
        {{"med0Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med9Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Med"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Hard Questions
        {{"hard0Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Hard"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Ultra Hard Question
        {{"uhardAnong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B", "Bonus"}, 
         {"Blue", "White", "Red", "Orange"}}
    };
    
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
