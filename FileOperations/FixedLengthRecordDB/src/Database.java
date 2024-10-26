import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private final File databaseFile;
    private final File indexFile;
    private final Map<Integer, Long> indexMap = new HashMap<>();

    public Database(String dbFilePath, String indexFilePath) throws IOException {
        this.databaseFile = new File(dbFilePath);
        this.indexFile = new File(indexFilePath);

        // Load existing index if available
        if (indexFile.exists()) {
            loadIndex();
        }
    }

    // Load the index file into memory
    private void loadIndex() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(indexFile, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                long position = raf.readLong();
                indexMap.put(id, position);
            }
        }
    }

    // Save the index map to the index file
    private void saveIndex() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(indexFile, "rw")) {
            raf.setLength(0); // Clear the file
            for (Map.Entry<Integer, Long> entry : indexMap.entrySet()) {
                raf.writeInt(entry.getKey());
                raf.writeLong(entry.getValue());
            }
        }
    }

    // Add a new record
    public void addRecord(Record record) throws IOException {
        if (indexMap.containsKey(record.getId())) {
            System.out.println("Record with ID already exists.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(databaseFile, "rw")) {
            long position = raf.length(); // Append at the end of the file
            raf.seek(position);
            raf.write(record.toByteArray());

            // Update index
            indexMap.put(record.getId(), position);
            saveIndex();
        }
    }

    // Retrieve a record by ID
    public Record getRecord(int id) throws IOException {
        Long position = indexMap.get(id);
        if (position == null) {
            System.out.println("Record not found.");
            return null;
        }

        try (RandomAccessFile raf = new RandomAccessFile(databaseFile, "r")) {
            raf.seek(position);
            byte[] buffer = new byte[Record.RECORD_SIZE];
            raf.readFully(buffer);
            return Record.fromByteArray(buffer);
        }
    }

    // Update an existing record
    public void updateRecord(Record record) throws IOException {
        Long position = indexMap.get(record.getId());
        if (position == null) {
            System.out.println("Record not found.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(databaseFile, "rw")) {
            raf.seek(position);
            raf.write(record.toByteArray());
        }
    }

    // Delete a record
    public void deleteRecord(int id) throws IOException {
        Long position = indexMap.remove(id);
        if (position == null) {
            System.out.println("Record not found.");
            return;
        }

        // Mark the record as deleted by setting the ID to -1
        try (RandomAccessFile raf = new RandomAccessFile(databaseFile, "rw")) {
            raf.seek(position);
            raf.writeInt(-1);
        }

        // Update the index file
        saveIndex();
    }
}
