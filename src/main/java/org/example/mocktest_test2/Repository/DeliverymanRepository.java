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
                if (row.length >= 3) {
                    Deliveryman deliveryman = new Deliveryman(
                            Integer.parseInt(row[0]), // ID
                            row[1], // Name
                            row[2], // Phone Number
                            new ArrayList<>() // Empty orders to be resolved later
                    );
                    deliverymanCache.put(deliveryman.getID(), deliveryman);
                }
            }
            dataLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Deliveryman> findAll() {
        loadData();
        return new ArrayList<>(deliverymanCache.values());
    }

    public Deliveryman findById(int id) {
        loadData();
        return deliverymanCache.get(id);
    }

    public void save(Deliveryman deliveryman) {
        loadData();
        deliverymanCache.put(deliveryman.getID(), deliveryman);
        writeToFile();
    }

    public void update(Deliveryman deliveryman) {
        loadData();
        deliverymanCache.put(deliveryman.getID(), deliveryman);
        writeToFile();
    }

    public void delete(int id) {
        loadData();
        deliverymanCache.remove(id);
        writeToFile();
    }

    private void writeToFile() {
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
