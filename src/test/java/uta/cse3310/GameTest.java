package uta.cse3310;

import junit.framework.TestCase;


public class GameTest extends TestCase{
    public void testPlayerTypeButtonArray(){
        Game g = new Game();
        g.startGame();

        //creates a new word to test the word checker and playertype array

        String testword = "testing";
        g.wordGrid.locations.add(new WordPosition(testword, 0, 0, 0, testword.length()-1, "Horizontal Right"));
        g.grid.wordsBank.add("testing");
        
        g.update(new UserEvent(0, PlayerType.Blue, 0));
        System.out.println("button after 1input");
        
        //printButton(0, 40, g.button);
        
        g.update(new UserEvent(0, PlayerType.Blue, testword.length()-1));
        System.out.println("button after 2 input ");
        
        //printButton(0, 40, g.button);
        
        // for(int i = 0; i < testword.length(); i++){//checks that all buttons are correctly set
        //     assertTrue(g.button[i] == PlayerType.Blue);
        // }
        

    }
    public void printButton(int start, int end, PlayerType[] array){//method for debugging/testing
        for(int i = start; i <= end; i++){
            if(i%20==0){
                System.out.println("");
            }
            System.out.print("["+i+"]"+"|"+array[i]+"|");
            
        }
        System.out.println("");
    }
    
}
