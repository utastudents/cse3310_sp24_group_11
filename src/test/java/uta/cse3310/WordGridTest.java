// package uta.cse3310;
import java.util.List;

public class WordGridTest {
    
    public static void testCreateWordSearch() {
        // Test case: Ensure the method returns a non-null grid with at least one word placed
        List<String> words = List.of("JAVA", "PYTHON","CPP", "HTML", "CSS");
        WordGrid.Grid grid = WordGrid.createWords(words);
        assert grid != null : "Grid should not be null";
        assert grid.wordBank.size() > 0 : "At least one word should be placed on the grid";
        
        // Test case: Ensure all words are placed on the grid
        for (String word : words) {
            boolean found = false;
            for (String solution : grid.wordBank) {
                if (solution.contains(word)) {
                    found = true;
                    break;
                }
            }
            assert found : "Word " + word + " should be placed on the grid";
        }
        
        // Test case: Ensure the number of attempts is less than or equal to 100
        assert grid.numAttempts <= 100 : "Number of attempts should be less than or equal to 100";
    }
}
