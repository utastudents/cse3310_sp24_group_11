package uta.cse3310;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlayerTest extends TestCase{
  
  public PlayerTest(String testname){
    super(testname);
  }
  
  public static Test suite()
  {
    return new TestSuite( AppTest.class );
  }

  public void testNameSetandGet(){
    
    String username = "username";
    ArrayList<Player> playerList = new ArrayList<Player>();
    Player player = new Player(username,playerList);
    
    assertEquals(username, player.getPlayerName());
  }

  
  public void testNameCheck(){//tests the unique username check
    
    String username = "username";
    ArrayList<Player> playerList = new ArrayList<Player>();
    Player player = new Player(username,playerList);
    playerList.add(player);

    String username2 = "username";
    Player player2 = new Player(username2,playerList);
    playerList.add(player2);

    Player player3 = new Player(username, playerList);
    assertFalse(player.getPlayerName() == player3.getPlayerName());
  }
}
