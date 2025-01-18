package org.example.mocktest_test2.Repository;

import org.example.mocktest_test2.Model.Deliveryman;
import org.example.mocktest_test2.Util.CSVUtil;

import java.io.IOException;
import java.util.*;

public class DeliverymanRepository {
    private static final String FILE_PATH = "src/main/resources/Database/Deliveryman.txt";
    private static DeliverymanRepository instance;

    private final Map<Integer, Deliveryman> deliverymanCache = new HashMap<>();
    private boolean dataLoaded = false;

    private DeliverymanRepository() {}

    public static synchronized DeliverymanRepository getInstance() {
        if (instance == null) {
            instance = new DeliverymanRepository();
        }
        return instance;
    }

    private synchronized void loadData() {
        if (dataLoaded) return;

        try {
            List<String[]> data = CSVUtil.readCsv(FILE_PATH);
            for (String[] row : data) {
                if (row.length >= 3 && isNumeric(row[0])) {
                    Deliveryman deliveryman = new Deliveryman(
                            Integer.parseInt(row[0]), // ID
                            row[1], // Name
                            row[2]  // Phone Number
                    );
                    deliverymanCache.put(deliveryman.getID(), deliveryman);
                } else {
                    System.err.println("Invalid row: " + Arrays.toString(row));
                }
            }
            dataLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public synchronized List<Deliveryman> findAll() {
        loadData();
        return new ArrayList<>(deliverymanCache.values());
    }

    public synchronized Optional<Deliveryman> findById(int id) {
        loadData();
        return Optional.ofNullable(deliverymanCache.get(id));
    }

    public synchronized void save(Deliveryman deliveryman) {
        loadData();
        deliverymanCache.put(deliveryman.getID(), deliveryman);
        writeToFile();
    }

    public synchronized void update(Deliveryman deliveryman) {
        loadData();
        deliverymanCache.put(deliveryman.getID(), deliveryman);
        writeToFile();
    }

    public synchronized void delete(int id) {
        loadData();
        deliverymanCache.remove(id);
        writeToFile();
    }

    private synchronized void writeToFile() {
        List<String[]> data = new ArrayList<>();
        for (Deliveryman deliveryman : deliverymanCache.values()) {
            data.add(new String[]{
                    String.valueOf(deliveryman.getID()),
                    deliveryman.getName(),
                    deliveryman.getPhoneNumber()
            });
        }
        try {
            CSVUtil.writeCsv(FILE_PATH, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
