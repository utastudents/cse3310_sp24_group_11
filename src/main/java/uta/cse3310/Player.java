package uta.cse3310;

import java.util.*;
public class Player{
    String playerName;
    int playerScore;
    int playerID;
    ArrayList<Player> playerList;

    public Player(String playerName, ArrayList<Player> playerList){
        this.playerList = playerList;
        setPlayerName(playerName, playerList);
        playerID = playerList.size()+1;
    }

    public boolean verifyUsername(String playerName, ArrayList<Player> playerList){

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

    public void setPlayerName(String playerName, ArrayList<Player> playerList) {
        if(verifyUsername(playerName,playerList)){
            this.playerName = playerName;
        }
    } 
}

