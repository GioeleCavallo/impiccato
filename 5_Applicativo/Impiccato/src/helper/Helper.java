/**
 * Questa classe mette a disposizione dei metodi per il controllo
 * di vari parametri.
 *
 * @author gioele.cavallo
 * @versoin 14.10.2021
 */
package helper;

import game.DateServer;
import game.GameHandler;
import game.Game;
import game.Player;
import java.util.ArrayList;


public class Helper {

    public static String[] invalidNames
            = {"dio", "gesù", "madonna", "maometto",
                "buddha", "gay", "omosessuale"};

    public static boolean isOnAGame(Player player, GameHandler games) {

        for (Game gm : games.getGames()) {
            if (gm.getPlayers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nameTaked(Player player) {
        for (Player plr : DateServer.getGameHandler().getPlayers()) {
            if (plr.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValid(String name) {
        if (name.length() > 20 || isVulgar(name) || isBlank(name)) {
            return false;
        }
        return true;
    }

    public static boolean isVulgar(String word) {
        String wrd = word.toLowerCase();
        for (String invalidString : invalidNames) {
            StringBuilder sb = new StringBuilder(invalidString);
            if (wrd.contains(sb)
                    || wrd.contains(sb.reverse()) || percentageMatch(sb.toString(), invalidString) >= 50) {
                return true;
            }
        }
        return false;
    }

    public static int percentageMatch(String wrd1, String wrd2) {
        int length = Math.min(wrd1.length(), wrd2.length());
        int match = 0;
        wrd1 = wrd1.toLowerCase();
        wrd2 = wrd2.toLowerCase();
        if (wrd1.length() == wrd2.length()) {
            match++;
        }
        for (int i = 0; i < length; i++) {
            if (wrd1.charAt(i) == wrd2.charAt(i)) {
                match++;
            }
        }
        int percentage = (match / length) * 100;
        return percentage;
    }

    public static boolean isBlank(String text) {
        if (text == null || text.equals("") || text.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean charsArrayContainsChar(ArrayList<String> chars, char c) {
        for (int i = 0; i < chars.size(); i++) {
            if ((chars.get(i).charAt(0)) == c) {
                return true;
            }
        }
        return false;
    }

    /*public static void main(String[] args) {
        
    }*/
}
