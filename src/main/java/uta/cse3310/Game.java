package uta.cse3310;

import java.util.ArrayList;

public class Game {
    public int GameId;
    ArrayList<Player> playerList = new ArrayList<Player>();
    WordGrid wordGrid;
    public PlayerType currentTurn;// there are no turns?
    public PlayerType[] button = new PlayerType[2500];
    ArrayList<UserEvent> events = new ArrayList<UserEvent>();
    public String[] msg;
    public Statistics Stats; 

    
    public Game(){

    }
    public Game(ArrayList<Player> playerList, int gameID){//playerList only contains players that are in this game
        GameId = gameID;
        this.playerList = playerList;
        startGame();
    }

    public void update(UserEvent U){
        // Processes user actions and updated game state

        if(button[U.getButton()] == PlayerType.NoPlayer){
            button[U.getButton()] = U.getPlayerType();
            events.add(U);
        }
        checkSelectedWords();
        


    }
    

    public void startGame() {
        for(Player player: playerList){
            player.playerScore = 0;
        }
        wordGrid = new WordGrid();

    }

    public boolean checkWinner() {
        // Checks for winner at the end of the game once all words are found
        return true;  // Placeholder return value
    }

    public void uniquePlayerColor() {
        // Assigns unique player colors to each player
    }

    public void checkSelectedWords(){
       //wordGrid.getWord(); maybe?
        
    }
    public String getWordString(int firstButton, int secondButton){


        return "";
    }

    public boolean checkValidWord(String word) {
        // Checks if highlighted word by player is a valid word to be scored from the word bank
        // for(String wordFromBank: wordGrid.getWordBank()){
        //     if(wordFromBank == word) return true;
        // }
        return false; 
    }

    public void displayUsersAndData() {
        // Displays player usernames/handles and their scores
    }

    public Statistics getStatistics(){
        // Returns the statistics of the game
        return Stats;
    }
    public void addPlayer(PlayerType player){
        // Adds a player to the game
        
    }
}

