import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // List of essential directories
        List<String> essentialDirs = new ArrayList<>();
        essentialDirs.add("src");
        essentialDirs.add("bin");
        essentialDirs.add("resources");
        essentialDirs.add("docs");

        Scanner sc = new Scanner(System.in);

        // Asking for additional custom subdirectories
        System.out.println("Do you want to specify any additional subdirectories (in addition to src, bin, resources, and docs)?");
        System.out.println("Enter the names separated by commas, or leave empty if no additional directories are needed:");
        String response = sc.nextLine().trim();

        // Adding additional directories if specified
        if (!response.isEmpty()) {
            String[] additionalDirs = response.split(",");
            for (String dirToAdd : additionalDirs) {
                dirToAdd = dirToAdd.trim();
                if (!dirToAdd.isEmpty() && !essentialDirs.contains(dirToAdd)) {
                    essentialDirs.add(dirToAdd);
                }
            }
        }

        // Asking for the base directory path
        System.out.println("Enter the base directory path for your project (copy-paste the absolute path):");
        String basePath = sc.nextLine().trim();

        // Normalize the path
        Path normalizedPath = Paths.get(basePath).toAbsolutePath().normalize();
        File baseDir = normalizedPath.toFile();

        // Create the base directory if it doesn't exist
        if (!baseDir.exists()) {
            if (baseDir.mkdirs()) {
                System.out.println("Base directory created successfully: " + baseDir.getPath());
            } else {
                System.out.println("Failed to create base directory: " + baseDir.getPath());
                sc.close();
                return; // Exit the program if base directory creation fails
            }
        } else {
            System.out.println("Base directory already exists: " + baseDir.getPath());
        }

        // Creating subdirectories
        for (String dir : essentialDirs) {
            File subDir = new File(baseDir, dir);
            if (!subDir.exists()) {
                if (subDir.mkdirs()) {
                    System.out.println("Directory created successfully: " + subDir.getPath());
                } else {
                    System.out.println("Failed to create directory: " + subDir.getPath());
                }
            } else {
                System.out.println("Directory already exists: " + subDir.getPath());
            }
        }

        sc.close();
        System.out.println("Directory structure setup completed.");
    }
}
