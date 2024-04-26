package uta.cse3310;


import java.util.ArrayList;
import com.google.gson.JsonObject;


public class Game {
    public int GameId;
    ArrayList<Player> playerList = new ArrayList<Player>();
    WordGrid wordGrid;
    public PlayerType currentTurn;// there are no turns?
    public PlayerType[] button = new PlayerType[400];
    ArrayList<UserEvent> redEvents = new ArrayList<>();
    ArrayList<UserEvent> blueEvents = new ArrayList<>();
    ArrayList<UserEvent> yellowEvents = new ArrayList<>();
    ArrayList<UserEvent> greenEvents = new ArrayList<>();
    public String[] msg;
    public Statistics Stats;
    WordGrid.Grid grid;
    public int wordBankSize;
    public boolean[] foundWords;
    public boolean gameStarted = false;
   
   


   
    public Game(){
       
    }
    public Game(ArrayList<Player> playerList, int gameID){//playerList only contains players that are in this game
        GameId = gameID;
        this.playerList = playerList;
        if (playerList.size() == 2) {
            startGame();
        }
    }


    public void update(UserEvent U){
        // Processes user actions and updated game state
        //System.out.println("Button: " + button[U.getButton()]);
        if(button[U.getButton()] == null){
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
                    System.out.println("Invalid Player Type");
                    break;
            }
        }
        String foundWord = "";
        System.out.println(U.playerType + " " + U.getButton());
        System.out.println("Red: " + redEvents.size() + " Blue: " + blueEvents.size() + " Yellow: " + yellowEvents.size() + " Green: " + greenEvents.size());
        //print out the first button and second button and check if they arent null for blue
        
        if(U.playerType == PlayerType.Blue && blueEvents.size()%2 == 0){
            if(blueEvents.size() > 1){
                foundWord = wordFound(blueEvents.get(0).getButton(), blueEvents.get(1).getButton(), PlayerType.Blue);
            
                if(foundWord == ""){
                    button[blueEvents.get(0).getButton()] = null;
                    button[blueEvents.get(1).getButton()] = null;
                } else {
                    updateScore(PlayerType.Blue, foundWord.length()); // Update score based on word length
                }
                blueEvents.clear(); // Clear events after processing
            }
        }

        if(U.playerType == PlayerType.Red && redEvents.size()%2 == 0){
            if(redEvents.size() > 1){
                foundWord = wordFound(redEvents.get(0).getButton(), redEvents.get(1).getButton(), PlayerType.Red);
            
                if(foundWord == ""){
                    button[redEvents.get(0).getButton()] = null;
                    button[redEvents.get(1).getButton()] = null;
                } else {
                    updateScore(PlayerType.Red, foundWord.length()); // Update score based on word length
                }
                redEvents.clear(); // Clear events after processing
            }
        }

        if(U.playerType == PlayerType.Yellow && yellowEvents.size()%2 == 0){
            if(yellowEvents.size() > 1){
                foundWord = wordFound(yellowEvents.get(0).getButton(), yellowEvents.get(1).getButton(), PlayerType.Yellow);
            
                if(foundWord == ""){
                    button[yellowEvents.get(0).getButton()] = null;
                    button[yellowEvents.get(1).getButton()] = null;
                } else {
                    updateScore(PlayerType.Yellow, foundWord.length()); // Update score based on word length
                }
                yellowEvents.clear(); // Clear events after processing
            }
        }

        if(U.playerType == PlayerType.Green && greenEvents.size()%2 == 0){
            if(greenEvents.size() > 1){
                foundWord = wordFound(greenEvents.get(0).getButton(), greenEvents.get(1).getButton(), PlayerType.Green);
            
                if(foundWord == ""){
                    button[greenEvents.get(0).getButton()] = null;
                    button[greenEvents.get(1).getButton()] = null;
                } else {
                    updateScore(PlayerType.Green, foundWord.length()); // Update score based on word length
                }
                greenEvents.clear(); // Clear events after processing
            }
        }
       
    }


    public PlayerType[] getButtonColorArray(){
        return button;
    }

    public void startGame() {
        if (playerList.size() == 2 && !gameStarted) { // Start the game only if exactly 2 players are present and game hasn't started
            gameStarted = true;
            for (Player player : playerList) { // Initialize player scores
                player.playerScore = 0;
            }
            wordGrid = new WordGrid();
            grid = wordGrid.createWordSearch(wordGrid.realWords("filteredWords.txt"));
            uniquePlayerColor();
            wordBankSize = grid.wordsBank.size();
            foundWords = new boolean[400];
        }
    }

    public PlayerType[] getTypeGrid(){
        return button;
    }

    public boolean checkWinner() {


        if(grid.wordsBank.size()<(wordBankSize/2)){
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
        System.out.println("First Button: " + firstButton + " Second Button: " + secondButton);
        int[] first,second;
        first = convertTo2Dcoord(firstButton);
        second = convertTo2Dcoord(secondButton);
        System.out.println("First Row: " + first[0] + " First Col: " + first[1]);
        System.out.println("Second Row: " + second[0] + " Second Col: " + second[1]);
        for(WordPosition possibleWord: wordGrid.locations){
            String formattedWord = String.format("%-10s(%d,%d)(%d,%d)", possibleWord.getWord(), possibleWord.startRow, possibleWord.startCol, possibleWord.endRow, possibleWord.endCol).trim();
            int wordIndex = grid.wordsBank.indexOf(formattedWord);
            if(possibleWord.startRow==first[0] && possibleWord.startCol == first[1] &&
                possibleWord.endRow == second[0] && possibleWord.endCol == second[1]){
                System.out.println("maybe word: " + possibleWord.getWord());
                if(checkValidWord(possibleWord.getWord())){
                    System.out.println("actual word verified: " + possibleWord.getWord());
                    if (wordIndex != -1) {
                        foundWords[wordIndex] = true;
                    }
                    highlightWord(possibleWord, type);
                    return possibleWord.getWord();
                }
               
            }
        }


        return "";
    }


    public boolean checkValidWord(String word) {
        System.out.println("In Check Valid Word");
        for(String possibleWord: grid.wordsBank){
            //System.out.println("Word: " + word + " Possible Word: " + possibleWord);
            possibleWord = possibleWord.replaceAll("\\s", "");//ignore whitespace in possibleWord
            possibleWord = possibleWord.replaceAll("\\(.*?\\)","");//remove coordinates from possibleWord
            //System.out.println("Word: " + word + " Possible Word: " + possibleWord);
            if(possibleWord.equals(word)){
                System.out.println("Word Found: " + word);
                return true;
            }
        }
        return false;
    }
    /*
     *  {1, 0},   // Vertical Down
        {0, 1},   // Horizontal Right
        {1, 1},   // Diagonal Down(Down-Right)
        {-1, 0},  // Vertical Up
        {-1, 1}   // Diagonal Up(Up-Right)
     */

    private void highlightWord(WordPosition word, PlayerType type){
        int rowIncrement = 0;
        int colIncrement = 0;
        switch (word.getDirection()) {
            //works
            case "Vertical Down":
                rowIncrement = 1;
                colIncrement = 0;
                System.out.println("Vertical Down");
                // rowIncrement = 0;
                // colIncrement = 1;
                // System.out.println("Horizontal Right");
                break;
            //works
            case "Horizontal Right":
                rowIncrement = 0;
                colIncrement = 1;
                System.out.println("Horizontal Right");
                // rowIncrement = 1;
                // colIncrement = 0;
                // System.out.println("Vertical Down");
                break;
            //works
            case "Diagonal Down":
                rowIncrement = 1;
                colIncrement = 1;
                System.out.println("Diagonal Down");
                break;
            //works 
            case "Vertical Up":
                rowIncrement = -1;
                colIncrement = 0;
                System.out.println("Vertical Up");
                break;
            //works (?)
            case "Diagonal Up":
                rowIncrement = 1;
                colIncrement = -1;
                System.out.println("Diagonal Up");
                break;
            default:
                break;
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

    public double getLetterDensity(){
        int realLetters = 0;
        for(String word: grid.wordsBank){
            realLetters += word.length();
        }
        return realLetters/400;//change 400 if we use a different grid size 
    }
    public int[] convertTo2Dcoord(int input){
        int row = input/20;
        int col = input%20;
        return new int[]{row,col};
    }
    public int convertTo1Dcoord(int row, int col){
        return row*20 + col;
    }

    public ArrayList<Boolean> getFoundWordsAsList() {
        ArrayList<Boolean> foundWordsList = new ArrayList<>();
        for (boolean wordFound : foundWords) {
            foundWordsList.add(wordFound);
        }
        return foundWordsList;
    }

    public ArrayList<JsonObject> getPlayerScores() {
        ArrayList<JsonObject> scores = new ArrayList<>();
        for (Player player : playerList) {
            JsonObject scoreDetail = new JsonObject();
            scoreDetail.addProperty("username", player.getPlayerName());
            scoreDetail.addProperty("points", player.playerScore);
            scores.add(scoreDetail);
        }
        return scores;
    }

    public void updateScore(PlayerType playerType, int scoreIncrement) {
    for (Player player : playerList) {
        if (player.type == playerType) {
            player.playerScore += scoreIncrement;
            break;
        }
    }
}
}