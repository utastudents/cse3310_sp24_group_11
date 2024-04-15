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
        public static class Grid{
            int numAttempts;
            char [][] wordGrid = new char[nRows][nCols];
            List<String> wordBank = new ArrayList<>();
        }

        //3directions to generate words on the grid
    public static final int[][]DIRS = {
        {1,0},{0,1},{1,1}
    };

    //nb rows and cols for the grids
    public static final int nRows = 50, nCols = 50;
    public static final int gridSize = nRows*nCols;

    //min number of words to place on the grid generate
    public static final int minWords = 330;
    public static final Random RANDOM = new Random();

    public static List<String>verifyWord(String filename){
        int maxLength = Math.max(nRows,nCols);
        List<String> words = new ArrayList<>();
        try(Scanner sc = new Scanner(new FileReader(filename))){
            while(sc.hasNext()){
                String s = sc.next().trim().toLowerCase();

                //we pick words with length between 4 and maxlength and with a-z inside
                if (s.matches("^[a-z]{4,"+ maxLength + "}$")){
                    words.add(s.toUpperCase());
                }
            }
        }catch(FileNotFoundException e){
            //manage error!
        }
        return words;
    }

    public static Grid createWords(List<String>words){
        // Create words for the word grid from word.txt file
        Grid grid = null;
        int numAttempts = 0;

        //we make 100 attempts to generate a grid
        while(++numAttempts < 100){
            Collections.shuffle(words);//we shuffle words
            grid = new Grid();
            int messageLength = placeMessage(grid, "WORD GRID SEARCH GAME");
            int target = gridSize- messageLength;
            int wordFilled = 0;

            for (String word: words){
                wordFilled +=tryPlaceWord(grid, word);

                if (wordFilled == target){
                    if (grid.wordBank.size()>= minWords){
                        grid.numAttempts = numAttempts;
                        return grid;

                    }else break;//we fulfill he grid but we have not enough words, we start over!
                }
            }
        }
        return grid;

    }
    public static void displayWords(Grid grid){
        // Displays word density onto word grid
        int size = grid.wordBank.size();
        System.out.println("Number of Words: " + size);

        for (int r = 0; r<nRows; r++){
            System.out.printf("%n%d ", r);

            for (int c=0;c<nCols;c++){
                System.out.printf(" %c ",grid.wordGrid[r][c]);
            }
        }
        System.out.println("\n");


    }
    
    public static void printDisplayOfWords(Grid grid){
        // Prints the count of each orientation of words, filler characters, and density to fit the requirements
        if(grid == null || grid.numAttempts == 0){
            System.out.println("No grid to display");
            return;
        }
        int size = grid.wordBank.size();

        System.out.println("Number of Attempts :" + grid.numAttempts);

        //display word to place
        for (int i=0; i<size-1; i +=1){
            System.out.printf("%s \n",grid.wordBank.get(i));
        }
    }

    

    public static int placeMessage(Grid grid, String msg){
        msg = msg.toUpperCase().replaceAll("[^A-Z]","");
        int messageLength = msg.length();

        if (messageLength >0 &&messageLength<gridSize){
            int gapSize = gridSize / messageLength;
        
        for (int i=0; i<messageLength;i++){
            int pos = i*gapSize + RANDOM.nextInt(gapSize);
            grid.wordGrid[pos/ nCols][pos % nCols] = msg.charAt(i);
        }
        return messageLength;
        }
        return 0;
    }
    public static int tryPlaceWord(Grid grid, String word){
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
    public static int tryLocation(Grid grid, String word, int dir, int pos){
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

        //we check wordgrid
        for (i=0, rr=r, cc=c;i<length;i++){
            if (grid.wordGrid[rr][cc]!=0 && grid.wordGrid[rr][cc]!= word.charAt(i))
            return 0;

            cc +=DIRS[dir][0];
            rr += DIRS[dir][1];
        }

        //we place word
        for (i=0,rr=r,cc=c; i<length;i++){
            if (grid.wordGrid[rr][cc]==word.charAt(i))
            overlaps++;
            else grid.wordGrid[rr][cc] = word.charAt(i);

            if (i<length-1){
                cc += DIRS[dir][0];
                rr += DIRS[dir][1];
            }
        }

        int lettersPlaced = length - overlaps;
        if (lettersPlaced>0)
        grid.wordBank.add(String.format("%-10s(%d,%d)(%d,%d)",word,c,r,cc,rr));

        return lettersPlaced;
    }

    // public static void main(String[] args){
    //     //we meed to create these methods to look
    //     displayWords(createWords(verifyWord("words.txt")));
    //     printDisplayOfWords(createWords(verifyWord("words.txt")));

    //}
}