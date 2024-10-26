import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Create a program to read and display specific sections of a file using random access.
        Write a method to jump to a specific byte in the file and read a defined number of bytes.
        Print the extracted content as text.
        Prompt the user for start and end positions in the file.
         */
        Scanner sc = new Scanner(System.in);
        String filePath = "FileToRead.txt";
        File fileToRead = new File(filePath);

        try (RandomAccessFile raf = new RandomAccessFile(fileToRead, "r")) {

            System.out.println("Enter byte start position to read from:");
            int byteStart = Integer.parseInt(sc.nextLine());

            System.out.println("Enter byte end position to read to:");
            int byteEnd = Integer.parseInt(sc.nextLine());

            if (byteStart < 0 || byteEnd <= byteStart || byteEnd > raf.length()) {
                System.out.println("Invalid byte range specified.");
                return;
            }

            int byteAmount = byteEnd - byteStart;

            byte[] data = new byte[byteAmount];
            raf.seek(byteStart);
            int bytesRead = raf.read(data, 0, byteAmount);

            if (bytesRead == -1) {
                System.out.println("Could not read any data from the file.");
            } else {
                String content = new String(data);
                System.out.println("Extracted content: " + content);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}