import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class extractWords {
    public static void main(String[] args) {
        String inputFile = "words.txt";
        String outputFile = "filteredWords.txt";

        try {
            extractWords(inputFile, outputFile);
            System.out.println("Worked!");
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }

    public static void extractWords(String inputFile, String outputFile) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\n");
                for (String word : words) {
                    if (word.length() > 3) {
                        writer.write(word + "\n");
                    }
                }
            }
        
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }
    }
}
