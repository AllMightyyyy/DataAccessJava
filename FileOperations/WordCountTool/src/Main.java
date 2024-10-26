import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    /*
        Implement a word count tool that displays the number of words, lines, and characters in a file.
        Use `BufferedReader` to count lines and words.
        Ignore whitespace and punctuation when counting words.
        Print the results in a structured output.
        Add a feature to find and display the most frequently occurring word
     */
    public static void main(String[] args) {
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;
        Map<String, Integer> wordFrequency = new HashMap<String, Integer>();

        // Define a pattern to match words (ignoring punctuation)
        Pattern wordPattern = Pattern.compile("\\b[a-zA-Z]+\\b");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("FileToRead"))));
            String line;

            while ((line = br.readLine()) != null) {
                lineCount++;
                charCount += line.length();

                String[] words = line.split("\\W+");
                for(String word : words) {
                    if (!word.isEmpty()) {
                        wordCount++;
                        String wordLowerCase = word.toLowerCase();
                        wordFrequency.put(wordLowerCase, wordFrequency.getOrDefault(wordLowerCase, 0) + 1);
                    }
                }
            }

            String wordMostFrequent = null;
            int maxFrequency = 0;
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                if (entry.getValue() > maxFrequency) {
                    maxFrequency = entry.getValue();
                    wordMostFrequent = entry.getKey();
                }
            }

            System.out.println("File Analysis Results:");
            System.out.println("----------------------");
            System.out.println("Total Lines: " + lineCount);
            System.out.println("Total Words: " + wordCount);
            System.out.println("Total Characters: " + charCount);
            if (wordMostFrequent != null) {
                System.out.println("Most Frequent Word: " + wordMostFrequent + " (occurs " + maxFrequency + " times)");
            } else {
                System.out.println("No words found in the file.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}