import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        This program recursively copies all files and subdirectories from a source directory to a destination.
        It uses `Files.walkFileTree` for traversal and `Files.copy` for copying files.
        The original directory structure is maintained in the destination.
        */

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the source directory: ");
        String sourceDir = scanner.nextLine().trim();
        System.out.print("Enter the destination directory: ");
        String destinationDir = scanner.nextLine().trim();

        Path sourcePath = Paths.get(sourceDir);
        Path destinationPath = Paths.get(destinationDir);

        // Validate source directory
        if (!Files.isDirectory(sourcePath)) {
            System.out.println("Source is not a valid directory.");
            return;
        }

        try {
            // Use a custom FileVisitor to recursively copy files
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Calculate the destination path for the current directory
                    Path targetDir = destinationPath.resolve(sourcePath.relativize(dir));
                    if (!Files.exists(targetDir)) {
                        // Create the directory if it does not exist in the destination
                        Files.createDirectories(targetDir);
                        System.out.println("Created directory: " + targetDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Calculate the destination path for the current file
                    Path targetFile = destinationPath.resolve(sourcePath.relativize(file));

                    // Check if the file already exists in the destination
                    if (Files.exists(targetFile)) {
                        // Prompt user for action if file already exists
                        System.out.print("File already exists: " + targetFile + ". Overwrite (O), Skip (S), Rename (R)? ");
                        String choice = scanner.nextLine().trim().toUpperCase();

                        switch (choice) {
                            case "O": // Overwrite
                                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Overwritten file: " + targetFile);
                                break;
                            case "S": // Skip
                                System.out.println("Skipped file: " + targetFile);
                                break;
                            case "R": // Rename
                                Path renamedFile = getUniqueFileName(targetFile);
                                Files.copy(file, renamedFile);
                                System.out.println("Renamed and copied file: " + renamedFile);
                                break;
                            default:
                                System.out.println("Invalid choice. Skipping file: " + targetFile);
                                break;
                        }
                    } else {
                        // If file does not exist, simply copy it
                        Files.copy(file, targetFile);
                        System.out.println("Copied file: " + targetFile);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("File copy completed.");
        } catch (IOException e) {
            System.err.println("An error occurred during the file copy process: " + e.getMessage());
        }
    }

    // Helper method to generate a unique file name if the file already exists
    private static Path getUniqueFileName(Path file) {
        int count = 1;
        String fileName = file.getFileName().toString();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');

        // Separate the base name and extension if the file has an extension
        if (dotIndex != -1) {
            fileExtension = fileName.substring(dotIndex);
            fileName = fileName.substring(0, dotIndex);
        }

        // Keep generating new file names until a unique one is found
        Path newFileName = file.resolveSibling(fileName + "_" + count + fileExtension);
        while (Files.exists(newFileName)) {
            count++;
            newFileName = file.resolveSibling(fileName + "_" + count + fileExtension);
        }
        return newFileName;
    }
}
