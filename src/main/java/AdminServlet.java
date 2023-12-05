/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import res.*;

/**
 *
 * @author Vlad
 */
public class AdminServlet extends HttpServlet {

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

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/seshed");
            return;
        }

        if (session.getAttribute("username") == null
                || !((String) session.getAttribute("username")).equals("JohanLibertad")) {
            response.sendRedirect(request.getContextPath() + "/login_page.jsp");
            return;
        }

        if (request.getParameter("add_player") != null) {
            String username = request.getParameter("username");
            String score = request.getParameter("score");

            if (score != null) {
                double d_score = Double.parseDouble(score);
                double trimmedScore = Double.parseDouble(String.format("%.2f", d_score));
                addToLeaderboard(session, new Player(username, trimmedScore));
            }
        } else {
            String username = request.getParameter("username");
            deleteFromLeaderboard(session, username);
        }

        Leaderboard leaderboard = (Leaderboard) session.getAttribute("leaderboard");
        if (leaderboard != null)
            session.setAttribute("ranking", leaderboard.toArray());

        response.sendRedirect(request.getContextPath() + "/admin-page.jsp");
    }

    private static void addToLeaderboard(HttpSession session, Player p) {
        Leaderboard leaderboard = (Leaderboard) session.getAttribute("leaderboard");
        if (leaderboard == null) {
            leaderboard = new Leaderboard();
            session.setAttribute("leaderboard", leaderboard);
        }
        leaderboard.checkInsert(p);
    }

    private static void deleteFromLeaderboard(HttpSession session, String name) {
        Leaderboard leaderboard = (Leaderboard) session.getAttribute("leaderboard");
        if (leaderboard != null) {
            leaderboard.deletePlayer(name);
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
