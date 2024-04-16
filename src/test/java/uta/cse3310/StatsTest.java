package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StatsTest extends TestCase{
    
    public StatsTest(String testname){
        super(testname);
    }
    
    public static Test suite(){
        return new TestSuite( StatsTest.class );
    }

    public void testGamesInProgress(){
        Statistics stats = new Statistics();
        assertTrue(stats.getGamesInProgress() == 0);
        stats.increaseGamesInProgress();
        assertTrue(stats.getGamesInProgress() == 1);
    }

    public void testTotalGames(){
        Statistics stats = new Statistics();
        assertTrue(stats.getTotalGames() == 0);
        stats.increaseTotalGames();
        assertTrue(stats.getTotalGames() == 1);
    }

    public void testTotalPlayers(){
        Statistics stats = new Statistics();
        assertTrue(stats.getTotalPlayers() == 0);
        stats.increaseTotalPlayers();
        assertTrue(stats.getTotalPlayers() == 1);
    }
}