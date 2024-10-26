import java.io.*;
import java.util.Scanner;

public class Main {
    /*
        Write a program that splits a large text file into multiple smaller files and then merges them back.
        Prompt the user for the size of each chunk in lines or bytes.
        Write each chunk to a new file (`file_part1.txt`, `file_part2.txt`, etc.).
        Implement a method to merge the files back in the correct order.
        Add an option to split and merge binary files.
         */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get user choice for splitting
        System.out.println("Choose an option:\n1. Split a text file\n2. Split a binary file");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline

        // Get the file path
        System.out.println("Enter the path of the file to split:");
        String filePath = sc.nextLine();

        // Get the chunk size
        System.out.println("Enter the size of each chunk (in lines or bytes):");
        int chunkSize = sc.nextInt();
        sc.nextLine(); // Consume the newline

        // Split the file
        try {
            if (choice == 1) {
                splitTextFile(filePath, chunkSize);
            } else if (choice == 2) {
                splitBinaryFile(filePath, chunkSize);
            } else {
                System.out.println("Invalid choice.");
            }

            // Get user choice for merging
            System.out.println("\nDo you want to merge the split files back? (Y/N)");
            String mergeChoice = sc.nextLine().trim().toLowerCase();
            if (mergeChoice.equals("y")) {
                System.out.println("Enter the output file path for merging:");
                String outputFilePath = sc.nextLine();

                if (choice == 1) {
                    mergeTextFiles(outputFilePath);
                } else {
                    mergeBinaryFiles(outputFilePath);
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        sc.close();
    }

    // Method to split a text file by lines
    public static void splitTextFile(String filePath, int linesPerChunk) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int filePart = 1;
        int lineCount = 0;

        PrintWriter writer = new PrintWriter(new FileWriter("file_part" + filePart + ".txt"));

        while ((line = reader.readLine()) != null) {
            writer.println(line);
            lineCount++;

            if (lineCount >= linesPerChunk) {
                writer.close();
                filePart++;
                lineCount = 0;
                writer = new PrintWriter(new FileWriter("file_part" + filePart + ".txt"));
            }
        }

        reader.close();
        writer.close();

        System.out.println("Text file split into " + filePart + " parts.");
    }

    // Method to split a binary file by bytes
    public static void splitBinaryFile(String filePath, int bytesPerChunk) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buffer = new byte[bytesPerChunk];
        int bytesRead;
        int filePart = 1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            FileOutputStream outputStream = new FileOutputStream("file_part" + filePart + ".bin");
            outputStream.write(buffer, 0, bytesRead);
            outputStream.close();
            filePart++;
        }

        inputStream.close();
        System.out.println("Binary file split into " + (filePart - 1) + " parts.");
    }

    // Method to merge text files back
    public static void mergeTextFiles(String outputFilePath) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath));
        int filePart = 1;
        File partFile = new File("file_part" + filePart + ".txt");

        while (partFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(partFile));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
            reader.close();
            filePart++;
            partFile = new File("file_part" + filePart + ".txt");
        }

        writer.close();
        System.out.println("Text files merged into " + outputFilePath);
    }

    // Method to merge binary files back
    public static void mergeBinaryFiles(String outputFilePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(outputFilePath);
        int filePart = 1;
        File partFile = new File("file_part" + filePart + ".bin");

        while (partFile.exists()) {
            FileInputStream inputStream = new FileInputStream(partFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            filePart++;
            partFile = new File("file_part" + filePart + ".bin");
        }

        outputStream.close();
        System.out.println("Binary files merged into " + outputFilePath);
    }
}
