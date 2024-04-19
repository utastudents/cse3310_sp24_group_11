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
    }


    // Arraylist that will store word locations
    List<WordPosition> locations = new ArrayList<>();
   
    //4directions to generate words on the grid
    public final int[][]DIRS = {
    {1, 0},   // Vertical (Downward)
    {0, 1},   // Horizontal (Rightward)
    // {1, 1},   // Diagonal (Downward-Right)
    // {1, -1}   // Diagonal (Downward-Left)
    };


   
   
    //nb rows and cols for the grids
    public int nRows = 50, nCols = 50;
    public int gridSize = nRows*nCols;
    //min number of words to place on the grid generate
    public int minWords = 330;
    public Random RANDOM = new Random();






    // Method to read real words from a file
    public List<String>realWords(String filename){
        int maxLength = Math.max(nRows,nCols);
        List<String> words = new ArrayList<>();
        try(Scanner sc = new Scanner(new FileReader(filename))){
            while(sc.hasNext()){
                String s = sc.next().trim().toLowerCase();


                //we pick words with length between 4 and maxLength and with a-z inside
                if (s.matches("^[a-z]{8,11}$")){
                    words.add(s.toUpperCase());
                }
            }
        }catch(FileNotFoundException e){
            //manage error!
        }
        return words;
    }


    // Method to create a word search grid
    public Grid createWordSearch(List<String>words){
        Grid grid = null;
        int numAttempts = 0;


        //we make 100 attempts to generate a grid
        while(++numAttempts < 2){
            //System.out.println("Attempt: " + numAttempts);
            Collections.shuffle(words);//we shuffle words
            grid = new Grid();
            int messageLength = placeMessage(grid, "Word Search Game");
            int target = gridSize- messageLength;
            int cellsFilled = 0;


            for (String word: words){
                cellsFilled +=tryPlaceWord(grid, word);


                if (cellsFilled == target){
                    if (grid.wordsBank.size()>= minWords){
                        grid.numAttempts = numAttempts;
                        return grid;
                    }else break;//we fulfill he grid but we have not enough words, we start over!
                }
            }
            grid.numAttempts = numAttempts;
            //replace every non-word cell with a '-' character
            int count = 0;
            for (int r=0;r<nRows;r++){
                for (int c=0;c<nCols;c++){
                    if (grid.wordsGrid[r][c]==0){
                        grid.wordsGrid[r][c] = '-';
                        count++;
                    }
                        
                }
            }
            System.out.println("dashes count: " + count);
        }
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


    // Method to try placing a word at a location on the grid
    public int tryLocation(Grid grid, String word, int dir, int pos){
        int r=pos/nCols;
        int c = pos%nCols;
        int length = word.length();


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
        if (lettersPlaced>0)
        grid.wordsBank.add(String.format("%-10s(%d,%d)(%d,%d)",word,r,c,rr,cc));
        locations.add(new WordPosition(word, r, c, rr, cc));
        return lettersPlaced;




    }


    public void printResult(Grid grid){
        if(grid == null){// || grid.numAttempts == 0){
            System.out.println("No grid to display");
            return;
        }
        int size = grid.wordsBank.size();


        System.out.println("Number of Attempts :" + grid.numAttempts);
        System.out.println("Number of Words: " + size);
        System.out.println("\n      ");


        System.out.println();
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




   
}
   






   






