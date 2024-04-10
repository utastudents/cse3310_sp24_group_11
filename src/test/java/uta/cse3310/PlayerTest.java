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
  public void testNameCheck(){//tests if the 
    Player player = new Player();
    player.setName("username");
    ArrayList<Player> players = new ArrayList<Player>()
  }
}
