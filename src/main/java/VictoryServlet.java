/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vlad
 */
public class VictoryServlet extends HttpServlet {

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
        response.sendRedirect("victory_page.jsp");
    }

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

class Leaderboard {
    private double min=0;
    private int length = 0;
    private Map<String, Player> playerMap = new HashMap<>();
    private Player[] ranking = new Player[20];
    
    void checkInsert(Player p) {
        int startIndex = length-1;
        if (playerMap.containsKey(p.name)) {
            Player old_p = playerMap.get(p.name);
            
            if (old_p.score >= p.score)
                return; // old still beats
            
            old_p.score = p.score;
            startIndex = findIndex(p);
        } 
        else {
            if (p.score <= min) {
                return; // no point in editing
            } 
            
            // could either override or add new player to ranking
            if (length < 20)
                length++;
            else 
                playerMap.remove(ranking[length-1].name);

            ranking[length-1] = p;
            playerMap.put(p.name, p);
        }
        sortUp(startIndex);
        // since we sorted, the last element would 
        // always have the lowest score
        min = ranking[length-1].score;
    }
    
    // the insertion part in insertion sort
    // runs fairly fast, at worst 20 times
    // we just jog up the array finding things to move
    private void sortUp(int start) {
        int i = start;
        Player key = ranking[i];
        while (i > 0 && ranking[i-1].score < ranking[i].score) {
            ranking[i] = ranking[i-1];
            i--;
        }
        ranking[i] = key;
    }
    
    // binary search ;;;;
    private int findIndex(Player p) {
        int l = 0;
        int r = length-1;
        while (l <= r) {
            int m = l + (r - 1) / 2;
            
            if (ranking[m] == p)
                return m;
            
            if (ranking[m].score < p.score)
                l = m + 1;
            else 
                r = m - 1;
        }
        return -1; // should never be reached
    }
}