import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Create a fixed-length record database file that allows for quick access to individual records.
        Each record should be exactly 100 bytes and contain fields like ID, name, and age.
        Implement methods to add, update, retrieve, and delete records by record ID.
        Add an index file to speed up record searches by ID.
         */

        try {
            Database db = new Database("database.dat", "index.dat");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add record");
                System.out.println("2. Get record");
                System.out.println("3. Update record");
                System.out.println("4. Delete record");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter age: ");
                        int age = Integer.parseInt(sc.nextLine());
                        db.addRecord(new Record(id, name, age));
                        break;
                    case 2:
                        System.out.print("Enter ID: ");
                        id = Integer.parseInt(sc.nextLine());
                        Record record = db.getRecord(id);
                        if (record != null) {
                            System.out.println("ID: " + record.getId());
                            System.out.println("Name: " + record.getName());
                            System.out.println("Age: " + record.getAge());
                        }
                        break;
                    case 3:
                        System.out.print("Enter ID: ");
                        id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new name: ");
                        name = sc.nextLine();
                        System.out.print("Enter new age: ");
                        age = Integer.parseInt(sc.nextLine());
                        db.updateRecord(new Record(id, name, age));
                        break;
                    case 4:
                        System.out.print("Enter ID: ");
                        id = Integer.parseInt(sc.nextLine());
                        db.deleteRecord(id);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}