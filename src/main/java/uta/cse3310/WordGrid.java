package uta.cse3310;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

public class WordGrid{
    //Designing the grid
    public class Grid{
        int numAttempts;
        char [][] wordsGrid = new char[nRows][nCols];
        List<String>wordsBank = new ArrayList<>();
        int verticalDownCount = 0;
        int verticalUpCount = 0;
        int horizontalRightCount = 0;
        int diagonalDownCount = 0;
        int diagonalUpCount = 0;
    }

    // Arraylist that will store word locations 
    List<WordPosition> locations = new ArrayList<>();
    
    //4directions to generate words on the grid
    public final int[][] DIRS = {
        {1, 0},   // Vertical Down
        {0, 1},   // Horizontal Right
        {1, 1},   // Diagonal Down(Down-Right)
        {0, -1},  // Vertical Up
        {-1, 1}   // Diagonal Up(Up-Right)
    };

   
    
    //nb rows and cols for the grids
    public int nRows = 20, nCols = 20;
    public int gridSize = nRows*nCols;
    //min number of words to place on the grid generate
    public int minWords = 50;
    public double density = 0;;
    //public int totalLengthOfWords = 0;
    public Random RANDOM = new Random();
    public int numOfLetters = 0;



    // Method to read real words from a file
    public List<String>realWords(String filename){
        int maxLength = Math.max(nRows,nCols);
        List<String> words = new ArrayList<>();
        int totalLengthOfWords = 0;
        try(Scanner sc = new Scanner(new FileReader(filename))){
            while(sc.hasNext()){
                String s = sc.next().trim().toLowerCase();
                //we pick words with length between 4 and maxLength and with a-z inside
                if (s.matches("^[a-z]{4,10}$")){ //4,"+ maxLength + "
                    words.add(s.toUpperCase());
                    totalLengthOfWords += s.length();
                }
            }
        }catch(FileNotFoundException e){
            //manage error!
        }
        return words;
    }

    // Method to create a word search grid
    public Grid createWordSearch(List<String> words) {
        Grid grid = null;
        int numAttempts = 0;
        boolean minWordsConditionMet = false;
        final int MIN_WORDS = 65; // Minimum number of words required
    
        // We make 100 attempts to generate a grid
        while (++numAttempts < 10) {
            Collections.shuffle(words); // Shuffle words
            grid = new Grid();
            int messageLength = placeMessage(grid, "Word Search Game");
            int target = gridSize - messageLength;
            int cellsFilled = 0;
    
            for (String word : words) {
                cellsFilled += tryPlaceWord(grid, word);
    
                if (cellsFilled == target) {
                    break; // We fulfill the grid, but we may not have enough words
                }
            }
    
            // Check if the minimum word count is met for each direction
            boolean verticalDownConditionMet = (grid.verticalDownCount >= 15);
            boolean verticalUpConditionMet = (grid.verticalUpCount >= 15);
            boolean horizontalRightConditionMet = (grid.horizontalRightCount >= 15);
            boolean diagonalConditionMet = ((grid.diagonalDownCount + grid.diagonalUpCount) >= 15);
    
            // Try to add more words for directions that don't meet the minimum word count
            if (!verticalDownConditionMet || !verticalUpConditionMet) {
                addMoreWords(grid, words, 1, 3, verticalDownConditionMet, verticalUpConditionMet);
            }
            if (!horizontalRightConditionMet) {
                addMoreWords(grid, words, 0, -1, horizontalRightConditionMet, false);
            }
            if (!diagonalConditionMet) {
                addMoreWords(grid, words, 2, -1, diagonalConditionMet, false);
            }
    
            int totalWordsPlaced = grid.wordsBank.size();
    
            minWordsConditionMet = (verticalDownConditionMet && verticalUpConditionMet && horizontalRightConditionMet && diagonalConditionMet && totalWordsPlaced >= MIN_WORDS);
    
            if (minWordsConditionMet) {
                grid.numAttempts = numAttempts;
                break;
            }
        }
    
        grid.numAttempts = numAttempts;
        // Add random letters to non-filled cells
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (grid.wordsGrid[r][c] == 0) {
                    grid.wordsGrid[r][c] = (char) ('A' + RANDOM.nextInt(26));
                } else {
                    numOfLetters++;
                }
            }
        }
        density = (double) numOfLetters / gridSize;
        return grid;
    }

    // Method to place a message on the grid
    public int placeMessage(Grid grid, String msg){
        msg = msg.toUpperCase().replaceAll("[^A-Z]","");
        int messageLength = msg.length();

        if (messageLength >0 &&messageLength<gridSize){
            int gapSize = gridSize / messageLength;
        
            for (int i=0; i<messageLength;i++){
                int pos = i*gapSize + RANDOM.nextInt(gapSize);
                grid.wordsGrid[pos/ nCols][pos % nCols] = msg.charAt(i);
            }
            return messageLength;
        }
        return 0;
    }

    // Method to try placing a word on the grid
    public int tryPlaceWord(Grid grid, String word){
        int randDir = RANDOM.nextInt(DIRS.length);
        int randPos = RANDOM.nextInt(gridSize);

        for (int dir = 0; dir<DIRS.length;dir++){
            dir = (dir + randDir)% DIRS.length;
        

            for (int pos = 0; pos<gridSize;pos++){
                pos = (pos+randPos)%gridSize;

                int lettersPlaced = tryLocation(grid, word,dir,pos);

                if (lettersPlaced>0)
                    return lettersPlaced;
            }
        }
        return 0;
    }

    //To add more words in the grid of the condition is not met
    public void addMoreWords(Grid grid, List<String> words, int dir1, int dir2, boolean condition1, boolean condition2) {
        int minWordCount = 60; // Minimum word count for each direction
    
        for (String word : words) {
            // Vertical Down
            if (!condition1 && grid.verticalDownCount < minWordCount) {
                int lettersPlaced = tryPlaceWord(grid, word);
                if (lettersPlaced > 0) {
                    tryLocation(grid, word, dir1, -1);
                }
            }
    
            // Vertical Up
            if (!condition1 && grid.verticalUpCount < minWordCount) {
                int lettersPlaced = tryPlaceWord(grid, word);
                if (lettersPlaced > 0) {
                    tryLocation(grid, word, 3, -1);
                }
            }
    
            // Horizontal Right
            if (dir2 != -1 && !condition2 && grid.horizontalRightCount < minWordCount) {
                int lettersPlaced = tryPlaceWord(grid, word);
                if (lettersPlaced > 0) {
                    tryLocation(grid, word, dir2, -1);
                }
            }
    
            // Diagonal Down
            if (dir2 != -1 && !condition2 && (grid.diagonalDownCount + grid.diagonalUpCount) < minWordCount) {
                int lettersPlaced = tryPlaceWord(grid, word);
                if (lettersPlaced > 0) {
                    tryLocation(grid, word, 2, -1);
                }
            }
    
            // Diagonal Up
            if (dir2 != -1 && !condition2 && (grid.diagonalDownCount + grid.diagonalUpCount) < minWordCount) {
                int lettersPlaced = tryPlaceWord(grid, word);
                if (lettersPlaced > 0) {
                    tryLocation(grid, word, 4, -1);
                }
            }
    
            // Check if all minimum requirements are met
            if (grid.verticalDownCount >= minWordCount &&
                grid.verticalUpCount >= minWordCount &&
                grid.horizontalRightCount >= minWordCount &&
                (grid.diagonalDownCount + grid.diagonalUpCount) >= minWordCount) {
                break;
            }
        }
    }
    

    // Method to try placing a word at a location on the grid
    public int tryLocation(Grid grid, String word, int dir, int pos){
        int r, c, length = word.length();
        if (pos == -1) {
        // Find a suitable position to place the word
        for (int i = 0; i < gridSize; i++) {
            r = i / nCols;
            c = i % nCols;

            // Check bounds and availability
            if ((DIRS[dir][0] == 1 && (length + c) > nCols)
                    || (DIRS[dir][0] == -1 && (length - 1) > c)
                    || (DIRS[dir][1] == 1 && (length + r) > nRows)
                    || (DIRS[dir][1] == -1 && (length - 1) > r))
                continue;

            int rr = r, cc = c, overlaps = 0;
            for (int j = 0; j < length; j++) {
                if (grid.wordsGrid[rr][cc] != 0 && grid.wordsGrid[rr][cc] != word.charAt(j))
                    break;

                cc += DIRS[dir][0];
                rr += DIRS[dir][1];
                if (j == length - 1) {
                    // Found a suitable position
                    pos = i;
                    break;
                }
            }
            if (pos != -1)
                break;
        }
        if (pos == -1)
            return 0; // No suitable position found
    } else {
        r = pos / nCols;
        c = pos % nCols;
    }
    r = pos / nCols;
    c = pos % nCols;

        //we check bounds
        if ((DIRS[dir][0]==1&&(length+c)>nCols)
        ||(DIRS[dir][0]==-1&&(length-1)>c)
        ||(DIRS[dir][1]==1&&(length+r)>nRows)
        ||(DIRS[dir][1]==-1&&(length-1)>r))
        return 0;

        int i,rr,cc,overlaps = 0;

        //we check cells
        for (i=0, rr=r, cc=c;i<length;i++){
            if (grid.wordsGrid[rr][cc]!=0 && grid.wordsGrid[rr][cc]!= word.charAt(i))
            return 0;

            cc +=DIRS[dir][0];
            rr += DIRS[dir][1];
        }

        //we place word
        for (i=0,rr=r,cc=c; i<length;i++){
            if (grid.wordsGrid[rr][cc]==word.charAt(i))
            overlaps++;
            else grid.wordsGrid[rr][cc] = word.charAt(i);

            if (i<length-1){
                cc += DIRS[dir][0];
                rr += DIRS[dir][1];
            }
        }

        int lettersPlaced = length - overlaps;
        if (lettersPlaced > 0) {
            grid.wordsBank.add(String.format("%-10s(%d,%d)(%d,%d)", word, r, c, rr, cc));
            //locations.add(new WordPosition(word, r, c, rr, cc));
            switch (dir) {
                case 0: // Vertical Down
                    grid.horizontalRightCount++;
                    locations.add(new WordPosition(word, r, c, rr, cc, "Horizontal Right"));
                    // grid.verticalDownCount++;
                    // locations.add(new WordPosition(word, r, c, rr, cc, "Vertical Down"));
                    break;
                case 1: // Horizontal Right
                    grid.verticalDownCount++;
                    locations.add(new WordPosition(word, r, c, rr, cc, "Vertical Down"));
                    //grid.horizontalRightCount++;
                    //locations.add(new WordPosition(word, r, c, rr, cc, "Horizontal Right"));
                    break;
                case 2: // Diagonal Down
                    grid.diagonalDownCount++;
                    locations.add(new WordPosition(word, r, c, rr, cc, "Diagonal Down"));
                    break;
                case 3: // Vertical Up
                    grid.verticalUpCount++;
                    locations.add(new WordPosition(word, r, c, rr, cc, "Vertical Up"));
                    break;
                case 4: // Diagonal Up
                    grid.diagonalUpCount++;
                    locations.add(new WordPosition(word, r, c, rr, cc, "Diagonal Up"));
                    break;
            }
        }
        return lettersPlaced;
    }

    public void printResult(Grid grid){
        if(grid == null || grid.numAttempts == 0){
            System.out.println("No grid to display");
            return;
        }
        int size = grid.wordsBank.size();

        System.out.println("Number of Attempts :" + grid.numAttempts);
        System.out.println("Number of Words: " + size);
        System.out.println("\n      ");

        System.out.println();

        System.out.println("Vertical Down: " + grid.verticalDownCount);
        System.out.println("Vertical Up: " + grid.verticalUpCount);
        System.out.println("Horizontal Right: " + grid.horizontalRightCount);
        System.out.println("Diagonal Down: " + grid.diagonalDownCount);
        System.out.println("Diagonal Up: " + grid.diagonalUpCount);
        // for (int r = 0; r<nRows; r++){
        //     System.out.printf("%n%d ", r);

        //     for (int c=0;c<nCols;c++){
        //         System.out.printf(" %c ",grid.wordsGrid[r][c]);
        //     }
        // }
        // System.out.println("\n");
        for(int i = 0; i < nRows; i++){
            for(int j = 0; j < nCols; j++){
                System.out.print(grid.wordsGrid[i][j] + " ");
            }
            System.out.println();
        }

        //display word to place
        for (int i=0; i<size-1; i +=2){
            System.out.printf("%s %s%n",grid.wordsBank.get(i),grid.wordsBank.get(i+1));
        }

        if (size %2 ==1){
            System.out.println(grid.wordsBank.get(size-1));
        }
        // for (WordPosition location : locations) {
        // System.out.printf("Word: %s, Start: (%d,%d), End: (%d,%d)%n", location.word, location.startRow, location.startCol, location.endRow, location.endCol);
        // }
    }
    /*
     * int verticalDownCount = 0;
        int verticalUpCount = 0;
        int horizontalRightCount = 0;
        int diagonalDownCount = 0;
        int diagonalUpCount = 0;
     */

    public List<Object> getGridStatistics(Grid grid) {
        List<Object> stats = new ArrayList<>();
        int totalWordsPlaced = grid.wordsBank.size();
        stats.add(density);
        stats.add(gridSize-numOfLetters);
        stats.add(grid.verticalDownCount + "(" + (grid.verticalDownCount * 100 / totalWordsPlaced) + "%)");
        stats.add(grid.verticalUpCount+ "(" + (grid.verticalUpCount * 100 / totalWordsPlaced) + "%)");
        stats.add(grid.horizontalRightCount + "(" + (grid.horizontalRightCount * 100 / totalWordsPlaced) + "%)");
        stats.add(grid.diagonalDownCount + "(" + (grid.diagonalDownCount * 100 / totalWordsPlaced) + "%)");
        stats.add(grid.diagonalUpCount + "(" + (grid.diagonalUpCount * 100 / totalWordsPlaced) + "%)");
        stats.add(grid.diagonalDownCount + grid.diagonalUpCount + "(" + ((grid.diagonalDownCount + grid.diagonalUpCount) * 100 / totalWordsPlaced) + "%)");
        return stats;
    }
    
    // Method to get a preset grid based on an index
    public Grid getPresetGrid(int index) {
        Grid grid = new Grid();
        switch (index) {
            case 1:
                grid.wordsGrid[0][0] = 'H';
                grid.wordsGrid[0][1] = 'E';
                grid.wordsGrid[0][2] = 'L';
                grid.wordsGrid[0][3] = 'L';
                grid.wordsGrid[0][4] = 'O';
                grid.wordsBank.add("HELLO(0,0)(0,4)");

                grid.wordsGrid[1][0] = 'W';
                grid.wordsGrid[2][0] = 'O';
                grid.wordsGrid[3][0] = 'R';
                grid.wordsGrid[4][0] = 'L';
                grid.wordsGrid[5][0] = 'D';
                grid.wordsBank.add("WORLD(1,0)(5,0)");

                grid.wordsGrid[6][6] = 'J';
                grid.wordsGrid[7][7] = 'A';
                grid.wordsGrid[8][8] = 'V';
                grid.wordsGrid[9][9] = 'A';
                grid.wordsBank.add("JAVA(6,6)(9,9)");

                grid.verticalDownCount = 1; // "WORLD"
                grid.horizontalRightCount = 1; // "HELLO"
                grid.diagonalDownCount = 1; // "JAVA"

                break;
            case 2:
                grid.wordsGrid[0][0] = 'D';
                grid.wordsGrid[0][1] = 'A';
                grid.wordsGrid[0][2] = 'T';
                grid.wordsGrid[0][3] = 'A';
                grid.wordsBank.add("DATA(0,0)(0,3)");

                grid.wordsGrid[1][0] = 'S';
                grid.wordsGrid[2][0] = 'C';
                grid.wordsGrid[3][0] = 'I';
                grid.wordsGrid[4][0] = 'E';
                grid.wordsGrid[5][0] = 'N';
                grid.wordsGrid[6][0] = 'C';
                grid.wordsGrid[7][0] = 'E';
                grid.wordsBank.add("SCIENCE(1,0)(7,0)");

                grid.horizontalRightCount = 1; // "DATA"
                grid.verticalDownCount = 1; // "SCIENCE"
                break;
            case 3:
                grid.wordsGrid[0][0] = 'C';
                grid.wordsGrid[1][1] = 'O';
                grid.wordsGrid[2][2] = 'D';
                grid.wordsGrid[3][3] = 'E';
                grid.wordsBank.add("CODE(0,0)(3,3)");

                grid.wordsGrid[5][0] = 'J';
                grid.wordsGrid[5][1] = 'A';
                grid.wordsGrid[5][2] = 'V';
                grid.wordsGrid[5][3] = 'A';
                grid.wordsBank.add("JAVA(5,0)(5,3)");

                grid.diagonalDownCount = 1; // "CODE"
                grid.horizontalRightCount = 1; // "JAVA"
                break;
            case 4:
                grid.wordsGrid[0][0] = 'N';
                grid.wordsGrid[1][0] = 'O';
                grid.wordsGrid[2][0] = 'D';
                grid.wordsGrid[3][0] = 'E';
                grid.wordsBank.add("NODE(0,0)(3,0)");

                grid.wordsGrid[0][0] = 'R';
                grid.wordsGrid[0][1] = 'E';
                grid.wordsGrid[0][2] = 'A';
                grid.wordsGrid[0][3] = 'C';
                grid.wordsGrid[0][4] = 'T';
                grid.wordsBank.add("REACT(0,0)(0,4)");

                grid.verticalDownCount = 1; // "NODE"
                grid.horizontalRightCount = 1; // "REACT"
                break;
            case 5:
                grid.wordsGrid[0][0] = 'G';
                grid.wordsGrid[1][0] = 'R';
                grid.wordsGrid[2][0] = 'I';
                grid.wordsGrid[3][0] = 'D';
                grid.wordsBank.add("GRID(0,0)(3,0)");

                grid.wordsGrid[0][0] = 'T';
                grid.wordsGrid[0][1] = 'E';
                grid.wordsGrid[0][2] = 'S';
                grid.wordsGrid[0][3] = 'T';
                grid.wordsBank.add("TEST(0,0)(0,3)");

                grid.verticalDownCount = 1; // "GRID"
                grid.horizontalRightCount = 1; // "TEST"
                break;
            default:
                return createWordSearch(realWords("filteredWords.txt")); // Default to generating a grid
        }
        fillEmptyCells(grid);
        return grid;
    }


    private void fillEmptyCells(Grid grid) {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (grid.wordsGrid[i][j] == 0) { // Check if the cell is empty
                    grid.wordsGrid[i][j] = (char) ('A' + RANDOM.nextInt(26));
                }
            }
        }
    }
}
