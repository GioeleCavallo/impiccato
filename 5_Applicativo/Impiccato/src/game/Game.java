package game;

/**
 *
 * @author gioele.cavallo
 * @version 30.09.2021
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private String token;
    private List<Player> players;
    private List<String> words;
    private int time = 30;
    private int rounds = 5;
    private boolean started;
    private long startTime;
    private int maxErrors;
    private int currentRound;

    public Game() {
        this(30, 5);
    }

    public Game(int time) {
        this(time, 5);
    }

    public Game(int time, int rounds) {
        if (time >= 30 && rounds > 0) {
            this.words = generateWords(rounds);
            this.token = generateToken();
            this.players = new ArrayList<Player>();
            this.time = time;
            this.rounds = rounds;
            this.started = false;
            this.maxErrors = 10;
            this.currentRound = 0;
        } else {
            throw new IllegalArgumentException("insert a valid argument");
        }
    }
    
    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean getStarted() {
        return this.started;
    }

    public void addPlayer(Player plr) {
        plr.setToken(this.token);
        this.players.add(plr);
    }

    public void removePlayer(Player plr) {
        if (this.players.indexOf(plr) != -1) {
            this.players.remove(plr);
            plr.setToken("");
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public String getToken() {
        return this.token;
    }

    public int getMaxErrors() {
        return this.maxErrors;
    }

    public void setMaxErrors(int errors) {
        this.maxErrors = errors;
    }

    public List<String> getWords() {
        return this.words;
    }

    public void setTime(int time) {
        if (time >= 30) {
            this.time = time;
        } else {
            this.time = 30;
        }
    }

    public int getTime() {
        return this.time;
    }

    public void setRounds(int rounds) {
        if (rounds > 0) {
            this.rounds = rounds;
        } else {
            this.rounds = 5;
        }
        this.words = generateWords(this.rounds);
    }

    public int getRounds() {
        return this.rounds;
    }

    public void startGame() {
        this.startTime = System.currentTimeMillis();
        for (Player plr : this.players){
            plr.setIsOnGameStarted(true);
        }
    }

    /* public void endGame(){
        this.currentRound
    }*/
    
    public void endRound() {
        this.startTime = System.currentTimeMillis();
        this.currentRound++;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public static List<String> generateWords(int numberWords) {
        System.out.println("begin");
        List<String> words = new ArrayList<String>();
        Path path = Paths.get(".", "Files", "words.txt");
        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < numberWords; i++) {
                boolean utile = true;
                String parola;
                do {
                    utile = true;

                    parola = lines.get((int) (Math.random() * lines.size()));
                    for (int j = 0; j < parola.length(); j++) {
                        utile = (parola.charAt(j) >= 'a' && parola.charAt(j) <= 'z' 
                                || parola.charAt(j) >= 'A' && parola.charAt(j) <= 'Z') && utile && parola.length() >= 4;
                    }
                } while (!utile);
                words.add(parola);
            }
        } catch (IOException e) {
            System.out.println("Impossible to read \"" + path + "\"");
        }
        return words;
    }

    public static String generateToken() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        String token = "";
        for (int i = 0; i < 6; i++) {
            int index = (int) (AlphaNumericString.length()
                    * Math.random());
            token += AlphaNumericString.charAt(index);
        }
        return token;

    }

    public static void main(String[] args) {
        Game game = new Game(30,5);
        System.out.println(game.getWords());
        System.out.println("end");
        
    }
}
