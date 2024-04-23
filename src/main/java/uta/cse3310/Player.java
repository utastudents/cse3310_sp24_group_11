package uta.cse3310;

import java.util.*;
public class Player{
    String playerName;
    int playerScore;
    int playerID;
    static ArrayList<Player> playerList = new ArrayList<Player>();
    PlayerType type;

    public Player(String playerName){
        if(verifyUsername(playerName)){
            this.playerName = playerName;
            this.playerID = playerList.size() + 1; // Example ID assignment
            playerList.add(this);
        } else {
            this.playerName = null; // Indicates username was not unique
        }
    }

    public static boolean verifyUsername(String playerName){
    for (Player player: playerList) {
        if(playerName.equals(player.getPlayerName())){
            System.out.println("Player name is not unique");
            return false;
        }
    }
    return true;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        if(verifyUsername(playerName)){
            this.playerName = playerName;
        }
    } 

    public static void removePlayer(int playerID) {
        playerList.removeIf(player -> player.playerID == playerID);
    }
}
