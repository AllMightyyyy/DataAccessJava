import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String path = "C://Users//zakar//testCopy";
        try {
            System.out.println(Files.list(Paths.get(path)));
            Files.copy(Paths.get(path), Paths.get("C://Users//zakar//FileOperations//FileManagerNIO//testPaste"));
            Files.delete(Paths.get(path));
            System.out.println("done");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}