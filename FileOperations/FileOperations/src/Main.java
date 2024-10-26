import java.io.File;
import java.util.Arrays;

public class Main {
    /*
    Write a program that creates a directory structure for a project, such as "src", "bin", and "resources" folders.
     */
    public static void main(String[] args) {
        File project = new File("project");
        project.mkdir();
        String[] subDirs = {"src", "bin", "resources"};
        for (String subDir : subDirs) {
            File subDirectory = new File(project, subDir);
            subDirectory.mkdir();
        }
        System.out.println(Arrays.stream(project.list()).toList());
    }
}