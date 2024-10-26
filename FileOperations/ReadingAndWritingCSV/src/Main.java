import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a month");
        System.out.println("Options are : JAN , FEB , MAR , APR , MAY , JUN, JUL, AUG, SEP, OCT, NOV, DEC");
        String month = sc.nextLine().toUpperCase();  // Ensure uppercase input for consistency
        System.out.println("Enter year, Options are : 1958 , 1959 , 1960");
        int year = sc.nextInt();

        File csvFile = new File("data.csv");
        if (!csvFile.exists()) {
            System.out.println("CSV file not found!");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String header = reader.readLine();  // Skip header line
            System.out.println("Header: " + header);  // Debug: Print header

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                System.out.println("Reading line: " + line);  // Debug: Print each line read
                String[] data = line.split(",");
                if (data.length < 4) {
                    System.out.println("Skipping malformed line: " + line);  // Debug: Line format check
                    continue;
                }

                if ((data[0].trim()).contains(month)) {
                    System.out.println("Match found for month: " + data[0].trim());  // Debug: Print matched month
                    AirTravelStats airTravelStat = new AirTravelStats(
                            data[0].trim(),
                            Integer.parseInt(data[1].trim()),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim())
                    );

                    switch (year) {
                        case 1958:
                            System.out.println("Value for 1958: " + airTravelStat.getYear1());
                            break;
                        case 1959:
                            System.out.println("Value for 1959: " + airTravelStat.getYear2());
                            break;
                        case 1960:
                            System.out.println("Value for 1960: " + airTravelStat.getYear3());
                            break;
                        default:
                            System.out.println("Year not available");
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Month not found in data.");
            }
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
