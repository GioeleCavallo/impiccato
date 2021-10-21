package game;

/**
 *
 * @author gioele.cavallo
 * @version 07.10.2021
 */

import java.util.LinkedList;
public class GameHandler {
    private LinkedList<Game> games;
    
    public GameHandler(){
        this.games = new LinkedList<Game>();
    }
    
    public LinkedList<Game> getGames(){
        return this.games;
    }
    
    public LinkedList<Player> getPlayers(){
        LinkedList<Player> plr = new LinkedList<Player>();
        for (int i = 0; i < this.games.size(); i++) {
            plr.addAll(this.games.get(i).getPlayers());
        }
        return plr;
    }
    
    public void addGame(Game game){
        this.games.add(game);
    }
    
    public void removeGame(Game game){
        this.games.remove(game);
    }
    
    public Game getGameFromToken(String token){
        for(Game gm : this.games){
            if(gm.getToken().equals(token)){
                return gm;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        
    }
}