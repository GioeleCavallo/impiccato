package game;

/**
 *
 * @author Gioele Cavallo
 * @version 30.09.2021
 */
import helper.Helper;
import java.lang.*;
import java.util.ArrayList;

public class Player {

    private String name;
    private String token;
    private int points;
    private boolean isAdmin;
    private int errors;
    private boolean isStarted;
    private boolean finished;
    private ArrayList<String> guessedChars;

    public Player(String name) {
        this(name, " ", 0);
    }

    public Player(String name, String token) {
        this(name, token, 0);
    }

    public Player(String name, String token, int points) {
        if (Helper.isValid(name)) {
            this.name = name;
            this.token = token;
            this.points = points;
            this.isAdmin = false;
            this.errors = 0;
            this.isStarted = false;
            this.guessedChars = new ArrayList<>();
            this.finished = false;
        } else {
            throw new IllegalArgumentException("The name is not valid");
        }
    }

    public void setFinished(boolean finish) {
        this.finished = finish;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public ArrayList<String> getGuessedChars() {
        return this.guessedChars;
    }

    public void addGuessedChar(String character) {
        this.guessedChars.add(character);
    }
    
    public void deleteGuessedChar() {
        this.guessedChars.removeAll(this.guessedChars);
    }

    public int getErrors() {
        return this.errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void addErrors(int errors) {
        this.errors += errors;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (Helper.isValid(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("The name is not valid");
        }
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public boolean getAdmin() {
        return this.isAdmin;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        //this.isStarted = !token.trim().equals("");
        this.token = token;
    }

    public void setIsOnGameStarted(boolean bool) {
        this.isStarted = bool;
    }

    public boolean isOnGameStarted() {
        return this.isStarted;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        if (points >= 0) {
            this.points = points;
        }
    }

    public void addPoints(int points) {
        if (points >= 0) {
            this.points += points;
        }
    }

    public String toString() {
        return this.name + "(" + this.token + ")" + " - " + this.points;
    }

    public boolean equals(Player plr) {
        if (this.name.equals(plr.name)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Player plr = new Player("Gioele", "abc");
        System.out.println(plr.name);
        System.out.println(plr.token);
        System.out.println(plr.points);
    }
}
