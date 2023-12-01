/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import res.Player;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vlad
 */
@WebServlet(urlPatterns = "/VictoryServlet", name = "VictoryServlet")
public class VictoryServlet extends HttpServlet {
    private final String ADMIN = "ad_min";
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

        if (session == null || session.getAttribute("username") == null) {
            request.getRequestDispatcher("login-page.jsp").forward(request, response);
        }

        // then we work out the leaderboard
        Leaderboard leaderboard;
        if (session.getAttribute("leaderboard") == null) {
            leaderboard = new Leaderboard();
            session.setAttribute("leaderboard", leaderboard);
        } else {
            leaderboard = (Leaderboard) session.getAttribute("leaderboard");
            //leaderboard = new Leaderboard();
        }

        String username = (String) session.getAttribute("username");
        double score = Double.parseDouble((String)session.getAttribute("score"));

        Player player = new Player(username, score);
        
        //if (!username.equals(ADMIN))
            leaderboard.checkInsert(player);

        session.setAttribute("ranking", leaderboard.toArray());
        response.sendRedirect("victory_page.jsp");
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

    class Leaderboard {
        private double min;
        private int length;
        private Map<String, Player> playerMap;
        private Player[] ranking;

        public Leaderboard() {
            min = 0;
            length = 0;
            playerMap = new HashMap<>();
            ranking = new Player[20];
        }

        void checkInsert(Player p) {
            int startIndex;
            if (playerMap.containsKey(p.name)) {
                Player old_p = playerMap.get(p.name);

                if (old_p.score >= p.score)
                    return; // old still beats

                old_p.score = p.score;
                startIndex = findIndex(p);
            } else {
                // check if new player fits in ranking
                if (length < 20) {
                    length++;
                    
                // not fit for leaderboard
                } else if (p.score <= min) {
                    return; // no point in editing
                
                // it's better than last player so we good
                } else {
                    playerMap.remove(ranking[length - 1].name);
                }
                    

                ranking[length - 1] = p;
                playerMap.put(p.name, p);
                startIndex = length - 1;
            }
            if (length != 1)
                sortUp(startIndex);
            // since we sorted, the last element would
            // always have the lowest score
            min = ranking[length - 1].score;
        }

        // the insertion part in insertion sort
        // runs fairly fast, at worst 20 times
        // we just jog up the array finding things to move
        private void sortUp(int start) {
            Player key = ranking[start];
            int i = start-1;
            while (i>=0 && ranking[i].score < key.score) {
                ranking[i+1] = ranking[i];
                i--;
            }
            ranking[i+1] = key;
        }

        // binary search ;;;;
        private int findIndex(Player p) {
            int l = 0;
            int r = length - 1;
            while (l <= r) {
                int m = l + (r - 1) / 2;

                if (ranking[m] == p)
                    return m;

                if (ranking[m].score < p.score)
                    l = m + 1;
                else
                    r = m - 1;
            }
            return 0; // would be zero if the item is yet to be added
        }

        public Player[] toArray() {
            Player[] out_list = new Player[length];
            for (int i = 0; i < length; i++) {
                out_list[i] = ranking[i];
            }
            return out_list;
        }
    }// end of leaderboard class
}
