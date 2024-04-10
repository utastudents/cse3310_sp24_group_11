package uta.cse3310;

public class Player{
    String playerName;
    int playerScore;
    int playerID;

    public boolean verifyUsername(String playerName, ArrayList<Player> playerList){

        for (Player player: playerList) {//loops through every player in the player array and checks if any have the same username
            if(playerName == player.getUsername()){
                return false;
            }
        }
        return true;
    }

    public String getUsername() {
        return playerName;
    }

    public void setUsername(String username) {
        playerName = username;
    } 
}

