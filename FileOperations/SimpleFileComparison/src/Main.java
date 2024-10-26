import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        /*
        This program compares two files byte-by-byte using `Files.readAllBytes`.
        It prints whether the files are identical or different.
        */

        String filePath1 = "File1.txt";
        String filePath2 = "File2.txt";

        try {
            // Read all bytes from both files
            byte[] file1Bytes = Files.readAllBytes(Path.of(filePath1));
            byte[] file2Bytes = Files.readAllBytes(Path.of(filePath2));

            // Compare the byte arrays
            if (file1Bytes.length != file2Bytes.length) {
                System.out.println("Files are different (size mismatch).");
            } else {
                boolean areIdentical = true;
                for (int i = 0; i < file1Bytes.length; i++) {
                    if (file1Bytes[i] != file2Bytes[i]) {
                        areIdentical = false;
                        break;
                    }
                }

                if (areIdentical) {
                    System.out.println("Files are identical.");
                } else {
                    System.out.println("Files are different.");
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the files: " + e.getMessage());
        }
    }
}
