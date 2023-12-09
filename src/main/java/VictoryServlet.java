/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import res.*;

/**
 *
 * @author Vlad
 */
@WebServlet(urlPatterns = "/VictoryServlet", name = "VictoryServlet")
public class VictoryServlet extends HttpServlet {
    private final String ADMIN = "JohanLibertad";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("level") == null) {
            response.sendRedirect(request.getContextPath() + "/seshed");
            return;
        }

        // then we work out the leaderboard
        Leaderboard leaderboard;
        if (session.getAttribute("leaderboard") == null) {
            leaderboard = new Leaderboard();
            session.setAttribute("leaderboard", leaderboard);
        } else {
            leaderboard = (Leaderboard) session.getAttribute("leaderboard");
            // leaderboard = new Leaderboard();
        }

        String username = (String) session.getAttribute("username");
        double score = Double.parseDouble((String) session.getAttribute("score"));

        Player player = new Player(username, score);

        leaderboard.checkInsert(player);

        session.setAttribute("ranking", leaderboard.toArray());
        int level = Integer.parseInt((String) session.getAttribute("level"));

        if (level == 99) {
            response.sendRedirect(request.getContextPath() + "/victory_page.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/loss-page.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
