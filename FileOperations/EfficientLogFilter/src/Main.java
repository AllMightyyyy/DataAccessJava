import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Create a program to filter specific log entries from a large log file.
        Read the log file using `BufferedReader` and filter entries based on keywords (e.g., "ERROR", "WARNING").
        Write filtered lines to a new file named `filtered_log.txt`.
        Implement filtering by date range if the log entries include timestamps.
         */

        Scanner sc = new Scanner(System.in);

        // Prompt user for the log file path
        System.out.println("Enter the path of the log file:");
        String logFilePath = sc.nextLine();

        // Prompt user for keywords to filter by (comma-separated)
        System.out.println("Enter the keywords to filter by (e.g., ERROR, WARNING), separated by commas:");
        String[] keywords = sc.nextLine().split(",");

        // Prompt user for date range filtering
        System.out.println("Do you want to filter by date range? (Y/N)");
        boolean filterByDateRange = sc.nextLine().trim().equalsIgnoreCase("Y");

        String startDateStr = null;
        String endDateStr = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (filterByDateRange) {
            // Prompt user for start and end dates
            System.out.println("Enter the start date (yyyy-MM-dd):");
            startDateStr = sc.nextLine();

            System.out.println("Enter the end date (yyyy-MM-dd):");
            endDateStr = sc.nextLine();
        }

        // Filter the log file
        try {
            filterLogFile(logFilePath, keywords, filterByDateRange, startDateStr, endDateStr, dateFormat);
            System.out.println("Log file filtered successfully. Output saved to 'filtered_log.txt'.");
        } catch (IOException | ParseException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        sc.close();
    }

    // Method to filter the log file based on keywords and an optional date range
    public static void filterLogFile(String logFilePath, String[] keywords, boolean filterByDateRange,
                                     String startDateStr, String endDateStr, SimpleDateFormat dateFormat)
            throws IOException, ParseException {

        // Convert start and end dates to Date objects, if filtering by date range
        Date startDate = null;
        Date endDate = null;

        if (filterByDateRange) {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        }

        // Open the log file for reading
        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter("filtered_log.txt"));

        String line;
        while ((line = reader.readLine()) != null) {
            // Check if the line contains any of the specified keywords
            boolean containsKeyword = false;
            for (String keyword : keywords) {
                if (line.contains(keyword.trim())) {
                    containsKeyword = true;
                    break;
                }
            }

            // If the line doesn't contain any keyword, skip it
            if (!containsKeyword) {
                continue;
            }

            // If filtering by date range, check if the log entry's date is within the range
            if (filterByDateRange) {
                // Assume the date is at the beginning of the line in the format yyyy-MM-dd
                String dateStr = line.substring(0, 10);
                Date logDate = dateFormat.parse(dateStr);

                // If the log date is outside the specified range, skip it
                if (logDate.before(startDate) || logDate.after(endDate)) {
                    continue;
                }
            }

            // Write the filtered line to the output file
            writer.write(line);
            writer.newLine();
        }

        // Close resources
        reader.close();
        writer.close();
    }
}