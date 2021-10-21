/**
 *
 * @author Gioele Cavallo
 * @version 30.09.2021
 */

import java.lang.*;
public class Player {
    public static String[] invalidNames = 
    {"dio","gesù","madonna","maometto",
        "buddha","gay","omosessuale"};
    private String name;
    private String token;
    private int points;
    
    public Player(String name){
        this(name, " ",0);
    }
    
    public Player(String name, String token) {
        this(name,token,0);
    }
    
    public Player(String name, String token, int points) {
        if(isValid(name)){
            this.name = name;
            this.token = token;
            this.points = points;
        }else{
            throw new IllegalArgumentException("The name is not valid");
        }
    }

    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        if(isValid(name)){
            this.name = name;
        }else{
            throw new IllegalArgumentException("The name is not valid");
        }
    }    
    
    public String getToken(){
        return this.token;
    }
    
    public void setToken(String token){
        this.token = token;
    }

    public int getPoints(){
        return this.points;
    }
    
    public void setPoints(int points){
        if(points >= 0){
            this.points = points;
        }
    }
    
    private boolean isValid(String name) {
        if(name.length() > 20 || isVulgar(name)){
            return false;
        }
        return true;
    }
    
    public static boolean isVulgar(String word){
        String wrd = word.toLowerCase();
        for (int i = 0; i < invalidNames.length; i++) {
            StringBuilder sb = new StringBuilder(invalidNames[i]);  
            if(wrd.contains(sb) || 
                wrd.contains(sb.reverse())){
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Player plr = new Player("Gioele","abc");
        System.out.println(plr.name);
        System.out.println(plr.token);
        System.out.println(plr.points);
    }
}
