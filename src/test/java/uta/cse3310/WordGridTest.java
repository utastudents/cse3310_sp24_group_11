package uta.cse3310;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordGridTest extends TestCase {

    public void setUp() {
        // Initialize grid before each test
        List<String> words = new ArrayList<>();
        try {
            words = readWordsFromFile("filteredWords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        WordGrid wordGrid = new WordGrid();
        WordGrid.Grid grid = wordGrid.new Grid();
        grid.numAttempts = 1;
        grid.wordBank.add("WORD");
        System.out.println("Setup completed");
    }

    private List<String> readWordsFromFile(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim());
        }
        reader.close();
        return words;
    }

    public void testDisplayWords() {
        WordGrid wordGrid = new WordGrid();
        WordGrid.Grid grid = wordGrid.new Grid();
        
        assertNotNull("Word grid is null", grid);
        System.out.println("Display words test completed");
    }

    public void testPrintDisplayOfWords() {
        WordGrid wordGrid = new WordGrid();
        WordGrid.Grid grid = wordGrid.new Grid();
        
        assertNotNull("Word grid is null", grid);
        System.out.println("Print display of words test completed");
    }

    public void testCreateWords() {
        WordGrid wordGrid = new WordGrid();
        List<String> words = new ArrayList<>();
        words.add("TEST");
        WordGrid.Grid grid = wordGrid.new Grid();
        grid.numAttempts = 1;
        grid.wordBank.add("WORD");
        
        assertNotNull(words);
        assertFalse(words.isEmpty());
        assertNotNull(grid);
        assertTrue(grid.numAttempts > 0);
        assertFalse(grid.wordBank.isEmpty());
    }

    public void testTryPlaceWord() {
        WordGrid wordGrid = new WordGrid();
        WordGrid.Grid grid = wordGrid.new Grid();
        int lettersPlaced = wordGrid.tryPlaceWord(grid, "HELLO");
        
        assertTrue(lettersPlaced > 0);
        assertFalse(grid.wordBank.isEmpty());
    }

    public void testTryLocation() {
        WordGrid wordGrid = new WordGrid();
        WordGrid.Grid grid = wordGrid.new Grid();
        int lettersPlaced = wordGrid.tryLocation(grid, "WORLD", 0, 0);
        
        assertEquals(5, lettersPlaced);
        assertFalse(grid.wordBank.isEmpty());
    }
}
