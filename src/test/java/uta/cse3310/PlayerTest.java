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
    Player player = new Player();
    String username = "username";
    player.setUsername(username);
    assertEquals(username, player.getUsername());
  }
  public void testNameCheck(){//tests if the 
    Player player = new Player();
    player.setUsername("username");
    ArrayList<Player> players = new ArrayList<Player>();
  }
}
