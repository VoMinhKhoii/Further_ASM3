package org.example.mocktest_test2.Repository;

import org.example.mocktest_test2.Model.Customer;
import org.example.mocktest_test2.Util.CSVUtil;

import java.io.IOException;
import java.util.*;

public class CustomerRepository {
    private static final String FILE_PATH = "src/main/resources/Database/Customer.txt";
    private static CustomerRepository instance;

    private final Map<Integer, Customer> customerCache = new HashMap<>();
    private boolean dataLoaded = false;

    private CustomerRepository() {}

    public static synchronized CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    private synchronized void loadData() {
        if (dataLoaded) return;

        try {
            List<String[]> data = CSVUtil.readCsv(FILE_PATH);
            for (String[] row : data) {
                if (row.length >= 4) {
                    Customer customer = new Customer(
                            Integer.parseInt(row[0]), // ID
                            row[1], // Name
                            row[2], // Phone Number
                            row[3], // Address
                            new ArrayList<>() // Empty orders to be resolved later
                    );
                    customerCache.put(customer.getID(), customer);
                }
            }
            dataLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> findAll() {
        loadData();
        return new ArrayList<>(customerCache.values());
    }

    public Customer findById(int id) {
        loadData();
        return customerCache.get(id);
    }

    public void save(Customer customer) {
        loadData();
        customerCache.put(customer.getID(), customer);
        writeToFile();
    }

    public void update(Customer customer) {
        loadData();
        customerCache.put(customer.getID(), customer);
        writeToFile();
    }

    public void delete(int id) {
        loadData();
        customerCache.remove(id);
        writeToFile();
    }

    private void writeToFile() {
        List<String[]> data = new ArrayList<>();
        for (Customer customer : customerCache.values()) {
            data.add(new String[]{
                    String.valueOf(customer.getID()),
                    customer.getName(),
                    customer.getPhoneNumber(),
                    customer.getAddress(),
                    "" // Placeholder for orders, to be resolved in the UI
            });
        }
        try {
            CSVUtil.writeCsv(FILE_PATH, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
