package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserEventTest extends TestCase{
    public UserEventTest(String testname){
        super(testname);
      }
      
      public static Test suite()
      {
        return new TestSuite( AppTest.class );
      }

      public void getGameIdTest(){
        int gameID = 1;
        PlayerType playertype = PlayerType.Blue;
        int button = 3;
        UserEvent event = new UserEvent(gameID, playertype, button);
        assertTrue(event.getGameIdx() == gameID);

      }

      public void getButtonTest(){
        int gameID = 1;
        PlayerType playertype = PlayerType.Blue;
        int button = 3;
        UserEvent event = new UserEvent(gameID, playertype, button);
        assertTrue(event.getButton() == button);

      }

      public void getPlayerTypeTest(){
        int gameID = 1;
        PlayerType playertype = PlayerType.Blue;
        int button = 3;
        UserEvent event = new UserEvent(gameID, playertype, button);
        assertTrue(event.getPlayerType() == playertype);
      }
}
