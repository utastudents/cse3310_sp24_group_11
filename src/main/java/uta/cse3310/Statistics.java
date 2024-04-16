package uta.cse3310;

public class Statistics {
    private int totalGames;
    private int totalPlayers;
    private int gamesInProgress;
    
    public int getGamesInProgress(){
        // Returns the number of games in progress
        return gamesInProgress;
    }
    public int getTotalGames(){
        // Returns the total number of games played
        return totalGames;
    }
    public int getTotalPlayers(){
        // Returns the total number of players
        return totalPlayers;
    }
    public void increaseGamesInProgress(){
        // Increases the number of games in progress
        gamesInProgress++;
    }    
    public void increaseTotalGames(){
        // Increases the total number of games played
        totalGames++;
    }
    public void increaseTotalPlayers(){
        // Increases the total number of players
        totalPlayers++;
    }
}
