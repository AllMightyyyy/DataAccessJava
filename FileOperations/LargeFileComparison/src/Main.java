import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final int BUFFER_SIZE = 8192; // 8 KB buffer size

    public static void main(String[] args) {
        /*
        This program compares two files byte-by-byte using a buffer for large files.
        It reads both files in chunks and compares each chunk to avoid memory issues.
        */

        String filePath1 = "File1.txt";
        String filePath2 = "File2.txt";

        try {
            boolean areIdentical = compareFiles(Path.of(filePath1), Path.of(filePath2));
            if (areIdentical) {
                System.out.println("Files are identical.");
            } else {
                System.out.println("Files are different.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while comparing the files: " + e.getMessage());
        }
    }

    private static boolean compareFiles(Path file1, Path file2) throws IOException {
        // Compare file sizes first
        if (Files.size(file1) != Files.size(file2)) {
            return false; // Different sizes, so files are not identical
        }

        // Use try-with-resources to ensure streams are closed
        try (InputStream is1 = Files.newInputStream(file1);
             InputStream is2 = Files.newInputStream(file2)) {

            byte[] buffer1 = new byte[BUFFER_SIZE];
            byte[] buffer2 = new byte[BUFFER_SIZE];

            int bytesRead1;
            int bytesRead2;

            // Read both files in chunks and compare each chunk
            while ((bytesRead1 = is1.read(buffer1)) != -1 &&
                    (bytesRead2 = is2.read(buffer2)) != -1) {
                if (bytesRead1 != bytesRead2 || !compareBuffers(buffer1, buffer2, bytesRead1)) {
                    return false; // Files are different
                }
            }
        }

        return true; // Files are identical
    }

    // Helper method to compare buffers
    private static boolean compareBuffers(byte[] buffer1, byte[] buffer2, int length) {
        for (int i = 0; i < length; i++) {
            if (buffer1[i] != buffer2[i]) {
                return false;
            }
        }
        return true;
    }
}
