package uta.cse3310;

import java.util.ArrayList;

public class Game {
    public int GameId;
    ArrayList<Player> playerList = new ArrayList<Player>();
    WordGrid wordGrid;
    public PlayerType currentTurn;// there are no turns?
    public PlayerType[] button = new PlayerType[2500];
    ArrayList<UserEvent> redEvents,blueEvents,yellowEvents,greenEvents = new ArrayList<UserEvent>();
    public String[] msg;
    public Statistics Stats; 
    WordGrid.Grid grid;

    
    
    

    
    public Game(){
        
    }
    public Game(ArrayList<Player> playerList, int gameID){//playerList only contains players that are in this game
        GameId = gameID;
        this.playerList = playerList;
        startGame();
    }

    public void update(UserEvent U){
        // Processes user actions and updated game state

        if(button[U.getButton()] == PlayerType.NOPLAYER){
            button[U.getButton()] = U.getPlayerType();
            switch (U.getPlayerType()) {
                case Red:
                    redEvents.add(U);
                    break;
            
                case Blue:
                    blueEvents.add(U);
                    break;
                
                case Yellow:
                    yellowEvents.add(U);
                    break;

                case Green:
                    greenEvents.add(U);
                    break;

                default:
                    break;
            }
        }
        String foundWord = "";
        Player player = null;
        if(redEvents.size()==2){
            foundWord = wordFound(redEvents.get(0).getButton(),redEvents.get(1).getButton(),PlayerType.Red);
            playerList.get(0);
            player.playerScore += foundWord.length();
            playerList.set(0,player);
        }

        if(blueEvents.size()==2){
            foundWord = wordFound(blueEvents.get(0).getButton(),blueEvents.get(1).getButton(),PlayerType.Blue);
            playerList.get(1);
            player.playerScore += foundWord.length();
            playerList.set(1,player);
        }

        if(yellowEvents.size()==2){
            foundWord = wordFound(yellowEvents.get(0).getButton(),yellowEvents.get(1).getButton(),PlayerType.Yellow);
            playerList.get(2);
            player.playerScore += foundWord.length();
            playerList.set(2,player);
        }

        if(greenEvents.size()==2){
            foundWord = wordFound(redEvents.get(0).getButton(),redEvents.get(1).getButton(),PlayerType.Green);
            playerList.get(3);
            player.playerScore += foundWord.length();
            playerList.set(3,player);
        }
        
    }


    public void startGame() {
        for(Player player: playerList){
            player.playerScore = 0;
        }
        wordGrid = new WordGrid();
        
        grid = wordGrid.createWordSearch(wordGrid.realWords("filteredWords.txt"));
        wordGrid.printResult(grid);
        uniquePlayerColor();
        

    }

    public boolean checkWinner() {

        if(grid.wordsBank.size()<100){
            msg[0] = "Game Over";
            return true;
        }
        return false;  
    }

    public void uniquePlayerColor() {
        // Assigns unique player colors to each player
        int counter = 0;
        for(Player player: playerList){
            switch (counter) {
                case 0:
                    player.type = PlayerType.Blue;
                    counter++;
                    break;
                
                case 1:
                    player.type = PlayerType.Red;
                    counter++;
                    break;
            
                case 2:
                    player.type = PlayerType.Yellow;
                    counter++;
                    break;
                case 3:
                    player.type = PlayerType.Green;
                    counter++;
                    break;

                default:
                    break;
            }
            
            
        }
    }

    public String wordFound(int firstButton, int secondButton, PlayerType type){

        int[] first,second;
        first = convertTo2Dcoord(firstButton);
        second = convertTo2Dcoord(secondButton);
        for(WordPosition possibleWord: wordGrid.locations){
            if(possibleWord.startRow==first[0] && possibleWord.startCol == first[1] &&
                possibleWord.endRow == second[0] && possibleWord.endCol == second[1]){
                
                if(checkValidWord(possibleWord.getWord())){
                    grid.wordsBank.remove(possibleWord.getWord());  
                    highlightWord(possibleWord, type);
                    return possibleWord.getWord();
                }
                
            }
        }

        return "";
    }

    public boolean checkValidWord(String word) {
        for(String possibleWord: grid.wordsBank){
            if(word == possibleWord) return true;
        }
        return false; 
    }
    private void highlightWord(WordPosition word, PlayerType type){
        int rowIncrement = 0;
        int colIncrement = 0;
        if(word.startRow < word.endRow && word.startCol == word.endCol){// up to down
            colIncrement = 1;
        }
        if(word.startRow < word.endRow && word.startCol < word.endCol){//left to right up to down
            rowIncrement = 1;
            colIncrement = 1;
        }
        if(word.startRow < word.endRow && word.startCol < word.endCol){//right to left up to down
            rowIncrement = 1;
            colIncrement = -1;
        }
        if(word.startRow == word.endRow && word.startCol < word.endCol){//left to right 
            
            colIncrement = 1;
        }
        
        int length = word.getWord().length()-1;
        int row = word.getstartRow();
        int col = word.getstartCol();
        for(int i = 0; i < length; i++){
            button[convertTo1Dcoord(row, col)] = type;
            row += rowIncrement;
            col += colIncrement;
        }


    }

    public void displayUsersAndData() {
        // Displays player usernames/handles and their scores
    }

    public Statistics getStatistics(){
        return Stats;
    }
    public void addPlayer(Player player){
        if(playerList.size()<4){
            playerList.add(player);
        }
        
    }
    private int[] convertTo2Dcoord(int input){
        int row = input/50;
        int col = input%50;
        return new int[]{row,col};
    }
    private int convertTo1Dcoord(int row, int col){
        return row*50 + col;
    }
}

