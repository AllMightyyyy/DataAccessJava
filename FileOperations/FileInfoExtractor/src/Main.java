import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Program that takes a file path as input and displays detailed information about the file.
        Display the file’s name, path, absolute path, size (in bytes), and last modified date.
        Check if the file is readable, writable, and executable.
        Implement this as a loop so the user can check multiple files without restarting the program.
         */

        boolean run = true;
        while (run) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a file path to display information about, or type exit");
            String path = sc.nextLine();
            if (path.toLowerCase().equals("exit")) {
                run = false;
                break;
            }
            File file = new File(path);
            String[] fileSubDirs = file.list();
            System.out.println(file.getAbsolutePath() + " privilieges are : ");
            String canExecute = file.canExecute() ? "can execute the file" : "can not execute the file";
            String canRead = file.canRead() ? "can read the file" : "can not read the file";
            String canWrite = file.canWrite() ? "can write the file" : "can not write the file";
            System.out.println("the related privilieges are : " + canExecute + " and " + canRead + " and " + canWrite);
            System.out.println("File name : " + file.getName());
            System.out.println("File path : " + path);
            System.out.println("File is directory : " + file.isDirectory());
            System.out.println("File is a file : " + file.isFile());
            System.out.println("File size ( in bytes ) : " + file.length());
            System.out.println("File last modified date : " + file.lastModified());
        }
    }
}