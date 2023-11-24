/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
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
            response.sendRedirect(request.getContextPath() + "/redirectpage.jsp");
        
        int level = Integer.parseInt((String)session.getAttribute("level"));
        
        if (level == 0) {
            session.setAttribute("question-queue", CreateQuestionQueue());
        }
        else {
            if(!checkAnswers(session)) // check if the player answered right
                response.sendRedirect(request.getContextPath() + "/loss-page.jsp");
        }
        
        // before exit we update everything
        level = (level < 10) ? level+1 : 99;
        setupPageQuestions(session);
        session.setAttribute("level", level+"");
        
        String curr = ((String[][])session.getAttribute("current-question"))[0][0];
        response.addCookie(new Cookie("current-question", curr));
        
        response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
    }
    
    private Deque<String[][]> CreateQuestionQueue() {
        Deque<String[][]> outQueue = new ArrayDeque<>();
        
        // Easy Questions
        selectRandomQuestions(0,9, outQueue);
        
        // Medium Questions
        selectRandomQuestions(10,9, outQueue);
        
        //Hard Questions
        selectRandomQuestions(20,8, outQueue);
        
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
            String[][] temp = QuestionBank[end]; 
            QuestionBank[end] = QuestionBank[rand];
            QuestionBank[rand] = temp;
            
            // adjust range so that we don't get duplicate questions
            end--;
        }
    }
    
    // TODO: setup checking system
    private boolean checkAnswers(HttpSession session) {
        return true;
    }
    
    // TODO:
    // - Sets the question and the answers in the quiz page
    // - sets everything up using the session
    private void setupPageQuestions(HttpSession session) {
        String[][] question = ((Deque<String[][]>)session.getAttribute("question-queue")).poll();
        session.setAttribute("current-question", question);
    }
    
    String[][][] QuestionBank = {
        
        // Easy Questions
        {{"1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"9Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"10Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Medium Questions
        
        {{"med0Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med9Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Hard Questions
        {{"hard0Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard1Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"med2Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard3Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard4Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard5Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard6Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard7Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        {{"hard8Anong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
         {"Blue", "White", "Red", "Orange"}},
        
        // Ultra Hard Question
        {{"uhardAnong kulay ng t-shirt ng 2csa ng tinawag sila sa dean's office?", "B"}, 
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
