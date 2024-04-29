package uta.cse3310;

import junit.framework.TestCase;


public class GameTest extends TestCase{
    public void testTwoInput(){//R041 R042 R037
        Game g = new Game();
        g.addPlayer(new Player("player1"));
        g.addPlayer(new Player("player2"));
        g.startGame(null);

        //creates a new word to test the word checker and playertype array

        String testword = "testing";
        
        g.wordGrid.locations.add(new WordPosition(testword, 0, 0, 0, testword.length()-1, "Horizontal Right"));
        g.grid.wordsBank.add("testing");
        
        g.update(new UserEvent(0, PlayerType.Blue, 0));
        
        g.update(new UserEvent(0, PlayerType.Blue, testword.length()-1));
        
        
        for(int i = 0; i < testword.length(); i++){//checks that all buttons are correctly set
            assertTrue(g.button[i] == PlayerType.Blue);
        }
        

    }

    public void testInvalidWord(){//R030
        Game g = new Game();
        g.addPlayer(new Player("player1"));
        g.addPlayer(new Player("player2"));
        g.startGame(null);

        g.update(new UserEvent(0, PlayerType.Blue, 0));
        
        g.update(new UserEvent(0, PlayerType.Blue, 1));

        assertEquals(g.button[0], null);
        assertEquals(g.button[1], null);

        
    }

    public void testOneInput(){//test requirement R008
        Game g = new Game();
        g.addPlayer(new Player("Player 1"));
        g.addPlayer(new Player("Player 2"));
        g.startGame(null);

        int buttonInput = 5;

        g.update(new UserEvent(0, PlayerType.Blue, buttonInput));

        assertEquals(g.button[buttonInput], PlayerType.Blue);
    }


    public void testPointsAdded(){//R009 an R010
        Game g = new Game();
        g.addPlayer(new Player("player1"));
        g.addPlayer(new Player("player2"));
        g.startGame(1);

        String testword = "testing";
        
        g.wordGrid.locations.add(new WordPosition(testword, 0, 0, 0, testword.length()-1, "Horizontal Right"));
        g.grid.wordsBank.add("testing");
        
        g.update(new UserEvent(0, PlayerType.Blue, 0));
        
        g.update(new UserEvent(0, PlayerType.Blue, testword.length()-1));
       
        assertTrue(g.playerList.get(0).playerScore == testword.length());
        

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
