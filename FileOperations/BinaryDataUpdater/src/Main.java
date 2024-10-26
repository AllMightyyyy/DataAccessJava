import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Implement a program to modify specific bytes in a binary file using `RandomAccessFile`.
        Write data at specified byte positions without altering the rest of the file.
        For example, modify the header or footer of an image file.
        Add functionality to restore original data if necessary.
         */
        String filePath = "image.png";
        try (RandomAccessFile raf = new RandomAccessFile(new File(filePath), "rw")) {
            Scanner sc = new Scanner(System.in);

            // Read original data to allow for restoration later
            byte[] originalData = new byte[(int) raf.length()];
            raf.readFully(originalData); // Reads entire file content into the array

            // Prompt user for the byte position to modify
            System.out.println("Enter the byte position to modify:");
            int position = Integer.parseInt(sc.nextLine());

            // Validate the position
            if (position < 0 || position >= raf.length()) {
                System.out.println("Invalid byte position specified.");
                return;
            }

            // Prompt user for the new data (in bytes) to write at the specified position
            System.out.println("Enter the byte value (0-255) to insert at the specified position:");
            int newByteValue = Integer.parseInt(sc.nextLine());
            if (newByteValue < 0 || newByteValue > 255) {
                System.out.println("Invalid byte value. Must be between 0 and 255.");
                return;
            }

            // Move the file pointer to the specified position and write the new byte value
            raf.seek(position);
            raf.write(newByteValue);
            System.out.println("Byte modified successfully.");

            // Ask if the user wants to restore the original content
            System.out.println("Do you want to restore the original content? (Y/N)");
            boolean restore = sc.nextLine().trim().equalsIgnoreCase("Y");
            if (restore) {
                // Truncate the file to the original length before restoring
                raf.setLength(originalData.length);
                raf.seek(0);
                raf.write(originalData);
                System.out.println("Original content restored.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred while modifying the file: " + e.getMessage());
        }
    }
}