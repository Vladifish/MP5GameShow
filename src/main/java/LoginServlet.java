/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.ServletContext;
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
public class LoginServlet extends HttpServlet {
    private final String ADMIN = "JohanLibertad";

    private boolean checkEmpty(String param) {
        return param == null || param.trim().equals("");
    }

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
        String username = request.getParameter("username");

        if (checkEmpty(username)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST); // this should redirect back to the log-in page
            return;
        }

        HttpSession userSession = request.getSession(false);
        if (userSession == null) {
            // we just restart the servlet to get the cookie again
            response.sendRedirect(request.getContextPath() + "/seshed");
            return;
        }

        // admin bypass
        if (username.equals(ADMIN)) {
            userSession.setAttribute("username", username);

            // we'd want to redirect since we're handling cookies
            response.sendRedirect(request.getContextPath() + "/admin-page.jsp");
            return;
        }

        if (username.length() > 10) {
            request.getRequestDispatcher("/login_pageE.jsp").forward(request, response);
        }

        // ---------

        userSession.setAttribute("username", username);
        userSession.setAttribute("level", "0");

        response.sendRedirect(request.getContextPath() + "/QuizServlet");
        // request.getRequestDispatcher(redirectURL).forward(request, response);
    }

    private HttpSession retainSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        Cookie old_session = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("session"))
                    old_session = c;
            }
        }

        if (old_session != null) {
            if (!session.getId().equals(old_session.getValue())) {
                response.addCookie(new Cookie("JSESSIONID", old_session.getValue()));
            }
        } else {
            old_session = new Cookie("session", session.getId());
            int LARGEST_INT = 2147483647;
            old_session.setMaxAge(LARGEST_INT); // largest int value
            response.addCookie(old_session);
        }

        return session;
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
