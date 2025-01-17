package org.example.mocktest_test2.Repository;

import org.example.mocktest_test2.Model.Item;
import org.example.mocktest_test2.Util.CSVUtil;

import java.io.IOException;
import java.util.*;

public class ItemRepository {
    private static final String FILE_PATH = "src/main/resources/Database/Item.txt";
    private static ItemRepository instance;

    private final Map<Integer, Item> itemCache = new HashMap<>();
    private boolean dataLoaded = false;

    private ItemRepository() {}

    public static synchronized ItemRepository getInstance() {
        if (instance == null) {
            instance = new ItemRepository();
        }
        return instance;
    }

    private synchronized void loadData() {
        if (dataLoaded) return;

        try {
            List<String[]> data = CSVUtil.readCsv(FILE_PATH);
            for (String[] row : data) {
                if (row.length >= 3) {
                    Item item = new Item(
                            Integer.parseInt(row[0]), // ID
                            row[1], // Name
                            Double.parseDouble(row[2]) // Price
                    );
                    itemCache.put(item.getID(), item);
                }
            }
            dataLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> findAll() {
        loadData();
        return new ArrayList<>(itemCache.values());
    }

    public Item findById(int id) {
        loadData();
        return itemCache.get(id);
    }

    public void save(Item item) {
        loadData();
        itemCache.put(item.getID(), item);
        writeToFile();
    }

    public void update(Item item) {
        loadData();
        itemCache.put(item.getID(), item);
        writeToFile();
    }

    public void delete(int id) {
        loadData();
        itemCache.remove(id);
        writeToFile();
    }

    private void writeToFile() {
        List<String[]> data = new ArrayList<>();
        for (Item item : itemCache.values()) {
            data.add(new String[]{
                    String.valueOf(item.getID()),
                    item.getName(),
                    String.valueOf(item.getPrice())
            });
        }
        try {
            CSVUtil.writeCsv(FILE_PATH, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
