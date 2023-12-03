/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
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
public class SessionHandlingServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        Cookie prevSession = null;

        // just gets a specific cookie
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("session")) {
                    prevSession = c;
                }
            }
        }

        if (prevSession != null) {
            if (!session.getId().equals(prevSession.getValue())) {
                response.addCookie(new Cookie("JSESSIONID", prevSession.getValue()));
            }
        } else {
            prevSession = new Cookie("session", session.getId());
            int LARGEST_INT = 2147483647;
            prevSession.setMaxAge(LARGEST_INT); // it would die in a few years
            response.addCookie(prevSession);
        }

        session.setAttribute("checked", "true");
        // check if user still has to login
        // shouldn't throw an error since we make a session eitherway
        if (session.getAttribute("username") == null || session.getAttribute("level") == null) {
            response.sendRedirect(request.getContextPath() + "/login_page.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/quiz-page.jsp");
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
