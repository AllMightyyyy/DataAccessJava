import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Write a program to reverse the lines of a text file.
        Read the entire file into memory and reverse the order of lines.
        Save the reversed content to a new file.
        Add an option to reverse the content of each line as well (i.e., reverse characters within each line).
        */

        Scanner sc = new Scanner(System.in);

        // Prompt user for the file path
        System.out.println("Enter the path of the file to read:");
        String filePath = sc.nextLine();
        File fileToRead = new File(filePath);

        // Check if the file exists
        if (!fileToRead.exists() || !fileToRead.isFile()) {
            System.out.println("The specified file does not exist or is not a valid file.");
            sc.close();
            return;
        }

        // Get user options for reversing
        System.out.println("Do you want to reverse the order of lines? (Y/N)");
        boolean reverseLines = sc.nextLine().trim().equalsIgnoreCase("Y");

        System.out.println("Do you want to reverse the content of each line? (Y/N)");
        boolean reverseContent = sc.nextLine().trim().equalsIgnoreCase("Y");

        System.out.println("Enter the path of the output file:");
        String outputFilePath = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            // Read all lines from the file
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Reverse the order of lines if requested
            if (reverseLines) {
                Collections.reverse(lines);
            }

            // Reverse the content of each line if requested
            if (reverseContent) {
                for (int i = 0; i < lines.size(); i++) {
                    StringBuilder sb = new StringBuilder(lines.get(i));
                    lines.set(i, sb.reverse().toString());
                }
            }

            // Write the modified lines to the new file
            for (String modifiedLine : lines) {
                writer.write(modifiedLine);
                writer.newLine(); // Add a new line after each line
            }

            System.out.println("The modified content has been saved to " + outputFilePath);

        } catch (IOException e) {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
