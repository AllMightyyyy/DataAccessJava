import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Record {
    private int id;
    private String name;
    private int age;

    public static final int RECORD_SIZE = 100;
    private static final int NAME_SIZE = 80;

    public Record(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public byte[] toByteArray() {
        byte[] record = new byte[RECORD_SIZE];
        Arrays.fill(record, (byte) 0); // Fill with zeroes to finish the 100 required

        // convert ID to bytes
        record[0] = (byte) (id >> 24);
        record[1] = (byte) (id >> 16);
        record[2] = (byte) (id >> 8);
        record[3] = (byte) id;

        // convert name to bytes
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(nameBytes, 0, record, 4, Math.min(nameBytes.length, NAME_SIZE));

        // convert age to bytes
        record[84] = (byte) (age >> 24);
        record[85] = (byte) (age >> 16);
        record[86] = (byte) (age >> 8);
        record[87] = (byte) (age);

        return record;
    }

    // Create a record from a byte array
    public static Record fromByteArray(byte[] byteArray) {
        if (byteArray.length != RECORD_SIZE) {
            throw new IllegalArgumentException("Invalid record size.");
        }

        // Read ID
        int id = ((byteArray[0] & 0xFF) << 24) | ((byteArray[1] & 0xFF) << 16)
                | ((byteArray[2] & 0xFF) << 8) | (byteArray[3] & 0xFF);

        // Read name
        String name = new String(byteArray, 4, NAME_SIZE, StandardCharsets.UTF_8).trim();

        // Read age
        int age = ((byteArray[84] & 0xFF) << 24) | ((byteArray[85] & 0xFF) << 16)
                | ((byteArray[86] & 0xFF) << 8) | (byteArray[87] & 0xFF);

        return new Record(id, name, age);
    }
}
