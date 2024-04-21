package uta.cse3310;

import java.util.*;
public class Player{
    String playerName;
    int playerScore;
    int playerID;
    ArrayList<Player> playerList;
    PlayerType type;

    public Player(String playerName){
        if(verifyUsername(playerName)){
            this.playerName = playerName;
            this.playerID = playerList.size() + 1; // Example ID assignment
            playerList.add(this);
            System.out.println("Username is unique");
        } else {
            this.playerName = null; // Indicates username was not unique
            System.out.println("Username is not unique");
        }
    }

    public boolean verifyUsername(String playerName){

        for (Player player: playerList) {//checks if the username is unused 
            if(playerName == player.getPlayerName()){
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
}

