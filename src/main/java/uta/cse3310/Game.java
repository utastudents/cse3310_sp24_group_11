package uta.cse3310;

public class Game {
    public int GameId;
    private PlayerType players;
    public PlayerType currentTurn;
    public  PlayerType[] button;
    public String[] msg;
    public Statistics Stats;

    public void update(UserEvent U){
        // Processes user actions and updated game state
    }

    public void startGame() {
        // Starts the game with player 1
    }

    public boolean checkWinner() {
        // Checks for winner at the end of the game once all words are found
        return true;  // Placeholder return value
    }

    public void uniquePlayerColor() {
        // Assigns unique player colors to each player
    }

    public boolean checkValidWord(String word) {
        // Checks if highlighted word by player is a valid word to be scored from the word bank
        return true; // Placeholder return value
    }

    public void displayUsersAndData() {
        // Displays player usernames/handles and their scores
    }

    public Statistics getStatistics(){
        // Returns the statistics of the game
        return Stats;
    }
}

