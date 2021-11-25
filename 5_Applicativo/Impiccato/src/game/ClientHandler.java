package game;

/**
 * Questa classe gestisce le richieste dei Client e la partita.
 *
 * @author gioelecavallo
 * @version 07.10.2021
 */
import exceptions.InvalidNameException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.Helper;

public class ClientHandler implements Runnable {

    private Player player;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public Player getPlayer() {
        return this.player;
    }

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    public String getRandomName() {
        return DateServer.getRandomName();
    }
    
    /*public void addPlayer(Player plr) throws InvalidNameException {
        DateServer.addPlayer(plr);
        ArrayList<Player> players = DateServer.getPlayers();
        for(Player plr2 : players){
            System.out.println(plr2);
        }
        player = plr;
    }*/

    //@Override
    public void run() throws IllegalArgumentException{
        try {
            while (true) {
                System.out.println("Attending reply");
                String request = in.readLine();
                System.out.println(request);
                String playerIdentifier = "";
                if (request != null) {
                    playerIdentifier = request.substring(request.indexOf("%") + 1, request.indexOf("%", request.indexOf("%") + 1));
                } else {
                    return;
                }

                String[] playerArray = playerIdentifier.split(",");
                //out.printf("[%s] [%s] [%s]",playerArray[0],playerArray[1],playerArray[2]);

                Player player = new Player(playerArray[0], playerArray[1], Integer.parseInt(playerArray[2]));
                boolean exist = false;
                ArrayList<Player> players = DateServer.getPlayers();
                int counter = 0;
                for (Player plr : players) {
                    if (plr.getName().equals(player.getName())) {
                        counter++;
                        out.println("count: "+counter);
                    }
                }
                request = request.substring(request.indexOf("%", request.indexOf("%") + 1) + 1, request.length());

                boolean canRun = true;
                if (counter == 0) {
                    DateServer.addPlayer(player);
                } else /*if (counter > 1 && !request.startsWith("changed name,"))*/ {
                    canRun = false;
                    //throw new IllegalArgumentException("Name already used");
                }
                player = DateServer.getPlayer(player);
                request = request.substring(request.indexOf("%", request.indexOf("%") + 1) + 1, request.length());
                this.player = player;
                if(request.equals("nothing")){
                    
                    return;
                }
                if (canRun && !player.isOnGameStarted()) {

                    if (request.equals("name")) {
                        out.println(DateServer.getRandomName());
                    } /*else if (request.toLowerCase().startsWith("check ")) {
                        String namePlr = request.substring(request.indexOf(" ") + 1, pack.length());
                        ArrayList<Player> arr = DateServer.getPlayers();
                        for (int i = 0; i < arr.size(); i++) {
                            System.out.println(arr.get(i));
                            if (arr.get(i).getName().equals(namePlr)) {
                                //throw InvalidNameException();
                            }
                        }
                        //System.out.println(player.getName());

                    } */ else if (request.startsWith("say")) {
                        int firstSpace = request.indexOf(" ");
                        if (firstSpace != -1) {
                            outToAll(request.substring(firstSpace + 1), player);
                        }
                    } else if (request.equals("create game")) {
                        if (Helper.isOnAGame(player, DateServer.getGameHandler())) {
                            out.print("you are already on a game");
                        } else if (Helper.nameTaked(player)) {
                            out.println("Your name is already taken, please change it to create a new game");
                        } else {
                            Game gm = new Game();
                            player.setToken(gm.getToken());
                            gm.addPlayer(player);
                            DateServer.setAdmin(player.getName(), true);
                            DateServer.addGame(gm);
                            out.printf("Game created. Token: %s", gm.getToken() + "\n");
                        }
                    } else if (request.startsWith("join ")) {
                        if (!Helper.isOnAGame(player, DateServer.getGameHandler())) {
                            int firstSpace = request.indexOf(" ");
                            if (firstSpace != -1) {
                                String tokenGame = request.substring(firstSpace + 1);
                                String tokenPlayer = player.getToken();
                                LinkedList<Game> games = DateServer.getGameHandler().getGames();
                                boolean isPlaying = false;
                                for (Game gm : games) {
                                    if (gm.getToken().equals(tokenPlayer)) {
                                        isPlaying = true;
                                    }
                                }
                                if (isPlaying) {
                                    out.println("You are already playing");
                                } else {
                                    DateServer.addPlayerOnGame(player, tokenGame);
                                    out.println("You joined: " + tokenGame);
                                }
                            }
                        } else {
                            out.println("You are already on a game");
                        }
                    } else if (request.equals("get token")) {
                        if (Helper.isOnAGame(player, DateServer.getGameHandler())) {
                            out.printf("Token: %s%n", player.getToken());
                        } else if (Helper.nameTaked(player)) {
                            out.println("Your name is already taken, please change it to create a new game");
                        } else {
                            out.println("you aren't plaing yet");
                        }
                    }
                    if (request.equals("start game")) {
                        if (Helper.isOnAGame(player, DateServer.getGameHandler())) {
                            Game gm = DateServer.getGameHandler().getGameFromToken(player.getToken());
                            if (player.getAdmin() && !gm.getStarted()) {
                                gm.setStarted(true);
                                gm.startGame();
                                //out.println("the game started!");
                                request = "";
                            } else {
                                out.println("you are not the admin of this game or the game is already started!");
                            }
                        } else {
                            out.println("you are not on a game");
                        }
                    } else if (request.startsWith("set time ")) {
                        Game gm = DateServer.getGameHandler().getGameFromToken(player.getToken());
                        if (gm == null) {
                            out.println("your game doesn't exist");
                            return;
                        }
                        if (gm.getStarted() || !player.getAdmin()) {
                            out.println("you can change the time cause the game is already started or you aren't the admin!");
                        } else {
                            String timeStr = request.substring(request.indexOf(" ", request.indexOf(" ") + 1) + 1, request.length()).trim();
                            try {
                                int time = Integer.parseInt(timeStr);

                                gm.setTime(time);
                                out.println("time changed to: " + DateServer.getGameHandler().getGameFromToken(player.getToken()).getTime());
                            } catch (NumberFormatException nfe) {
                                out.println("number format unaccepted");
                            }
                        }
                    } else if (request.startsWith("set rounds ")) {
                        Game gm = DateServer.getGameHandler().getGameFromToken(player.getToken());
                        if (gm == null) {
                            out.println("your game doesn't exist");
                            return;
                        }
                        if (gm.getStarted() || !player.getAdmin()) {
                            out.println("you can change the time cause the game is already started or you aren't the admin!");
                        } else {
                            String roundsStr = request.substring(request.indexOf(" ", request.indexOf(" ") + 1) + 1, request.length()).trim();
                            try {
                                int rounds = Integer.parseInt(roundsStr);

                                gm.setRounds(rounds);
                                out.println("rounds changed to: " + DateServer.getGameHandler().getGameFromToken(player.getToken()).getRounds());
                            } catch (NumberFormatException nfe) {
                                out.println("number format unaccepted");
                            }
                        }
                    } else if (request.equals("get players")) {
                        ArrayList<Player> arr = DateServer.getPlayers();
                        if (arr.isEmpty()) {
                            out.println("No players.");
                        } else {
                            out.println("count: " + arr.size() + "(" + DateServer.getClientNumber() + " connection)");
                            for (Player plr : arr) {
                                out.println(plr.toString());
                            }
                        }
                    } else if (request.equals("quit game")) {
                        if (Helper.isOnAGame(player, DateServer.getGameHandler())) {
                            DateServer.getGameHandler().getGameFromToken(player.getToken()).removePlayer(player);
                            out.println("eliminated payer.");
                            out.println("token: " + DateServer.getPlayer(player).getToken());
                        } else {
                            out.println("you aren't on a game");
                        }

                    } else if (request.equals("close")) {
                        DateServer.removePlayer(player);
                        DateServer.removeHandler(this);
                        System.out.println("removed player:");
                        System.out.printf("count of %s players (%s)%n", DateServer.getPlayers().size(), DateServer.getClientNumber());
                    }
                } else if (player.isOnGameStarted()) { // il giocatore é dentro una partita iniziata

                    Game gm = DateServer.getGameHandler().getGameFromToken(player.getToken());

                    /*if (gm.getRounds() <= gm.getCurrentRound()) {
                        out.println("finished the game !!!");
                        DateServer.removePlayerFromGame(player);
                        player.getGuessedChars().removeAll(player.getGuessedChars());
                        player.setErrors(0);
                        return;
                    }*/
                    if (!player.getFinished()) {
                        String word = gm.getWords().get(gm.getCurrentRound());
                        String censuredWord = "";

                        char letter = (request.length() > 0) ? request.charAt(0) : ' ';
                        outToGameChar(player, "[" + player.getName() + "] " + letter);
                        if (word.toLowerCase().contains(Character.toString(letter).toLowerCase())) {
                            player.addGuessedChar(Character.toString(letter).toLowerCase());
                        } else {
                            if (letter >= 'a' && letter <= 'z' || letter >= 'A' && letter <= 'Z') {
                                player.addErrors(1);
                            }
                        }
                        out.println("round : " + gm.getCurrentRound());
                        out.println("guessed chars -> " + player.getGuessedChars().toString());
                        for (int i = 0; i < word.length(); i++) {
                            censuredWord += (Helper.charsArrayContainsChar(player.getGuessedChars(), word.charAt(i))) ? word.charAt(i) : "*";
                        }
                        out.println(censuredWord + " -- " + word);
                        out.println("errors: " + player.getErrors() + " / 10");
                        if (player.getErrors() >= 10) {
                            out.println("FINISHED FOR ERRORS");
                            out.println("errors: " + player.getErrors() + "/" + gm.getMaxErrors());
                            out.println("points: " + player.getPoints());
                            //gm.endRound();

                            player.getGuessedChars().removeAll(player.getGuessedChars());
                            player.setErrors(0);
                            player.setFinished(true);
                            //return;
                        }
                        if (censuredWord.equals(word)) {
                            out.println("you win the round :)");
                            player.getGuessedChars().removeAll(player.getGuessedChars());
                            player.setErrors(0);
                            long time = System.currentTimeMillis();
                            out.printf("%s - %s%n", time / 1000, gm.getStartTime() / 1000);
                            int points = gm.getTime() - (int) ((time - gm.getStartTime()) / 1000);
                            out.println("points: " + points);
                            player.addPoints(points);
                            player.setFinished(true);
                            //gm.endRound();
                        }
                    } else {
                        out.println("wait until the round finish");
                    }

                }
            }
        } catch (IOException ioe) {
            /*System.err.println("IO Exception in client handler");
            System.err.println(ioe.getStackTrace());*/

        } catch (InvalidNameException ex) {
           // Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void outToAll(String message, Player from) {
        String fromName = from.getName();
        for (ClientHandler aClient : clients) {
            aClient.out.println(fromName + ": " + message);
        }
    }

    public void outToGame(Player plr, String message) {
        for (ClientHandler aClient : clients) {
            if (aClient.getPlayer().getToken().equals(plr.getToken())) {
                aClient.out.println(message);

            }
        }
    }

    public void outToGameChar(Player plr, String message) {
        for (ClientHandler aClient : clients) {
            if (aClient.getPlayer().getToken().equals(plr.getToken())
                    && !aClient.getPlayer().getName().equals(plr.getName())) {
                aClient.out.println(message);
            }
        }
    }
    /*public void endActions(Game gm, Player plr, PrintWriter out){
        gm.endRound();
        if(gm.getRounds() == gm.getCurrentRound()+1){
            out.println("finished the game !!!");
            DateServer.removePlayerFromGame(plr);
        }
        plr.getGuessedChars().removeAll(plr.getGuessedChars());
        plr.setErrors(0);
    }*/
}
