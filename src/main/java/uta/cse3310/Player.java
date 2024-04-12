package uta.cse3310;

import java.util.*;
public class Player{
    String playerName;
    int playerScore;
    int playerID;
    ArrayList<Player> playerList;

    public Player(ArrayList<Player> playerList){
        this.playerList = playerList;
    }

    public boolean verifyUsername(String playerName){

        for (Player player: playerList) {//loops through every player in the player array and checks if any have the same username
            if(playerName == player.getPlayerName()){
                return false;
            }
        }
        return true;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setUsername(String username) {
        if(verifyUsername(username)){
            playerName = username;
        }
    } 
}

