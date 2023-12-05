/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package res;

import java.util.*;

/**
 *
 * @author Vlad
 */
public class Leaderboard {
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

    public void checkInsert(Player p) {
        int startIndex;
        if (playerMap.containsKey(p.name)) {
            Player old_p = playerMap.get(p.name);

            if (old_p.score >= p.score)
                return; // old still beats

            old_p.score = p.score;
            startIndex = findIndex(p.name);
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

    public Player[] toArray() {
        Player[] out_list = new Player[length];
        for (int i = 0; i < length; i++) {
            out_list[i] = ranking[i];
        }
        return out_list;
    }

    // Does not really delete, mostly dereferences the player from the leaderboard
    // array
    public boolean deletePlayer(String name) {
        if (!playerMap.containsKey(name)) {
            return false;
        }

        // replaces the player using the items from the top
        int i = findIndex(name);

        while (i < length - 1) {
            ranking[i] = ranking[i + 1];
            i++;
        }

        // then we update the leaderboard to not include the dereferenced player
        length--;
        playerMap.remove(name);
        return true;
    }

    // the insertion part in insertion sort
    // runs fairly fast, at worst 20 times
    // we just jog up the array finding things to move
    private void sortUp(int start) {
        Player key = ranking[start];
        int i = start - 1;
        while (i >= 0 && ranking[i].score < key.score) {
            ranking[i + 1] = ranking[i];
            i--;
        }
        ranking[i + 1] = key;
    }

    // binary search ;;;;
    private int findIndex(String playerName) {
        int l = 0;
        int r = length - 1;
        Player p = playerMap.get(playerName);

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

}
