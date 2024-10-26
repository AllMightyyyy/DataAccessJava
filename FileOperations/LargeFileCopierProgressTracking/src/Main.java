import java.io.*;

public class Main {
    public static void main(String[] args) {
        /*
        Create a program to copy a large text or binary file and track progress.
        Use `BufferedInputStream` and `BufferedOutputStream` for efficient copying.
        Track and display progress in percentage (e.g., “50% complete”).
        Display estimated time remaining based on copying speed.
         */

        String fileSource = "FileSource";
        String fileDestination = "FileDestination";

        copyFileWithProgress(fileSource, fileDestination);
    }

    private static void copyFileWithProgress(String fileSource, String fileDestination) {
        File sourceFile = new File(fileSource);
        File destinationFile = new File(fileDestination);

        // Check if source file exists
        if (!sourceFile.exists()) {
            System.out.println("Source file does not exist");
            return;
        }

        // Get total size of source file
        long totalBytes = sourceFile.length();
        long copiedBytes = 0;
        int bufferSize = 1024 * 8; // 8 kb buffer size

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinationFile))) {

            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            long startTime = System.nanoTime();
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                copiedBytes += bytesRead;

                // calculate the progress percentage
                int progress = (int) ((copiedBytes * 100) / totalBytes);

                // calculate elapsed time and estimate remaining time
                long elapsedTime = System.nanoTime() - startTime;
                double secondsElapsed = elapsedTime / 1_000_000_000.0;
                double speed = copiedBytes / totalBytes;
                double timeRemaining = (totalBytes - copiedBytes) / speed;

                // Display progress and estimated time remaining
                System.out.printf("\rProgress: %d%% - Time remaining: %.2f seconds", progress, timeRemaining);
            }

            // Ensure all data is written
            bos.flush();

            System.out.println("\nFile copy completed successfully!");
        } catch (IOException e) {
            System.err.println("Error during file copy: " + e.getMessage());
        }
    }
}