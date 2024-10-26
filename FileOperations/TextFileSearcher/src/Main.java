import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        Create a program to search for a specific word in a text file and display its line number and position.
        Prompt the user to enter a file path and the word(s) to search for.
        For each occurrence, print the line number and index within the line.
        Add support for case-insensitive search and allow the user to specify multiple words.
         */

        Scanner sc = new Scanner(System.in);

        // Prompt user to enter file path
        System.out.println("Enter the path of the file to search:");
        String filePath = sc.nextLine();

        // Prompt user to enter the word(s) to search for, separated by commas
        System.out.println("Enter the word(s) to search for (separated by commas):");
        String[] wordsToSearch = sc.nextLine().toLowerCase().split(",");

        // Trim each word to remove extra spaces
        for (int i = 0; i < wordsToSearch.length; i++) {
            wordsToSearch[i] = wordsToSearch[i].trim();
        }

        // Try reading the file and searching for the words
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            // Map to store each word's occurrences with line numbers and positions
            Map<String, List<String>> wordOccurrences = new HashMap<>();

            while ((line = br.readLine()) != null) {
                // Convert line to lowercase for case-insensitive search
                String lowerCaseLine = line.toLowerCase();

                // Check each word to see if it's in the line
                for (String word : wordsToSearch) {
                    int index = lowerCaseLine.indexOf(word);
                    while (index != -1) {
                        // Record the occurrence
                        String occurrence = "Line " + lineNumber + ", Index " + index;
                        wordOccurrences
                                .computeIfAbsent(word, k -> new ArrayList<>())
                                .add(occurrence);

                        // Find the next occurrence in the line
                        index = lowerCaseLine.indexOf(word, index + 1);
                    }
                }
                lineNumber++;
            }

            // Display the results
            for (String word : wordsToSearch) {
                List<String> occurrences = wordOccurrences.get(word);
                if (occurrences != null && !occurrences.isEmpty()) {
                    System.out.println("Occurrences of \"" + word + "\":");
                    for (String occurrence : occurrences) {
                        System.out.println("  - " + occurrence);
                    }
                } else {
                    System.out.println("No occurrences of \"" + word + "\" found.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        sc.close();
    }
}
