import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Implement a real-time directory monitoring program using `WatchService` in NIO.
        Monitor a directory for changes (file creation, modification, deletion).
        Display notifications in the console for each detected event.
        Allow the user to specify multiple directories to watch simultaneously.
         */
        Scanner scanner = new Scanner(System.in);
        List<Path> directoriesToWatch = new ArrayList<>();

        // Allow the user to specify multiple directories to watch
        System.out.println("Enter directories to watch (comma-separated):");
        String input = scanner.nextLine();
        String[] paths = input.split(",");

        // Add each specified directory to the list
        for (String pathStr : paths) {
            Path path = Paths.get(pathStr.trim());
            if (Files.isDirectory(path)) {
                directoriesToWatch.add(path);
            } else {
                System.out.println("Invalid directory: " + pathStr.trim());
            }
        }

        if (directoriesToWatch.isEmpty()) {
            System.out.println("No valid directories specified to watch.");
            return;
        }

        // Start monitoring the directories
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // Register each directory with the WatchService
            for (Path dir : directoriesToWatch) {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE);
                System.out.println("Watching directory: " + dir);
            }

            // Monitor the WatchService for events
            System.out.println("Monitoring directories for changes...");
            while (true) {
                WatchKey key;
                try {
                    // Wait for a watch key to be signaled
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    System.out.println("Directory monitoring interrupted.");
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // Context for the event is the file name (relative to the watched directory)
                    Path fileName = (Path) event.context();
                    Path directory = (Path) key.watchable();

                    // Display event type and file name
                    System.out.println("Event detected: " + kind.name() + " - " + directory.resolve(fileName));
                }

                // Reset the key (important for further event processing)
                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("WatchKey no longer valid. Stopping monitoring for a directory.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while setting up the WatchService: " + e.getMessage());
        }
    }
}