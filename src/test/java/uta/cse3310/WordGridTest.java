package uta.cse3310;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;


public class WordGridTest extends TestCase {
    private WordGrid.Grid grid;

    public WordGridTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(WordGridTest.class);
    }

    public void setUp() {
        // Initialize grid before each test
        List<String> words = WordGrid.verifyWord("words.txt");
        grid = WordGrid.createWords(words);
    }

    public void testWordVerification() {
        assertNotNull("Word verification failed", grid);
        assertNotNull("Grid is null", grid.wordGrid);
        assertFalse("Word bank is empty", grid.wordBank.isEmpty());
        assertTrue("Number of attempts is less than 1", grid.numAttempts >= 1);
        System.out.println("Word verification test completed");
    }

    public void testDisplayWords() {
        assertNotNull("Word grid is null", grid.wordGrid);
        WordGrid.displayWords(grid); // Just for visual inspection, no assertions
        System.out.println("Display words test completed");
    }

    public void testPrintDisplayOfWords() {
        assertNotNull("Word bank is null", grid.wordBank);
        WordGrid.printDisplayOfWords(grid); // Just for visual inspection, no assertions
        System.out.println("Print display of words test completed");
    }

    // public void testWordPlacement() {
    //     assertTrue("Word placement test failed", checkWordPlacement(grid));
    //     System.out.println("Word placement test completed");
    // }

}

