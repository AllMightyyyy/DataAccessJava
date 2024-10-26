import java.io.*;

public class Main {
    public static void main(String[] args) {
        /*
        Create a program to read a text file line by line and display each line on the console.
        Use `BufferedReader` to read each line and display line numbers with each line of text.
        Implement a feature to stop reading after a certain line count (e.g., after 100 lines).
            */
        try {
            String filePath = "textToReader";
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(i + ": " + line);
                i++;
                if (i == 101) {
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}