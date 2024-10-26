import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        List all files and directories inside a specified directory.
        Print the name of each file and directory along with its size (in KB).
        Implement filtering to show only files with specific extensions (e.g., `.txt`, `.java`).
        Implement recursive listing to show the entire directory tree.
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the directory to scan:");
        String dir = sc.nextLine();
        File file = new File(dir);

        if (!file.isDirectory()) {
            System.out.println("Not a valid directory.");
        } else {
            System.out.println("Enter file extensions to filter (comma-separated, e.g., .txt,.java), or leave empty to show all files:");
            String filter = sc.nextLine().trim();
            String[] extensions = filter.isEmpty() ? new String[0] : filter.split(",");
            System.out.println("\nScanned directory:");
            listAllSubDirectoriesAndFiles(file, extensions, 0);
        }

        sc.close();
    }

    static void listAllSubDirectoriesAndFiles(File file, String[] extensions, int level) {
        if (file.isDirectory()) {
            // Indent according to directory level
            String indent = "  ".repeat(level);
            System.out.println(indent + "[DIR] " + file.getName());

            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    listAllSubDirectoriesAndFiles(subFile, extensions, level + 1);
                }
            }
        } else {
            // Check if the file matches the specified extensions
            if (shouldDisplayFile(file, extensions)) {
                String indent = "  ".repeat(level);
                long fileSizeInKB = file.length() / 1024;
                System.out.println(indent + "[FILE] " + file.getName() + " (" + fileSizeInKB + " KB)");
            }
        }
    }

    static boolean shouldDisplayFile(File file, String[] extensions) {
        // If no extensions are specified, show all files
        if (extensions.length == 0) {
            return true;
        }

        // Check if the file ends with any of the specified extensions
        String fileName = file.getName().toLowerCase();
        for (String ext : extensions) {
            if (fileName.endsWith(ext.toLowerCase().trim())) {
                return true;
            }
        }
        return false;
    }
}
