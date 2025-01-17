package org.example.mocktest_test2.Repository;

import org.example.mocktest_test2.Model.Customer;
import org.example.mocktest_test2.Model.Deliveryman;
import org.example.mocktest_test2.Model.Item;
import org.example.mocktest_test2.Model.Order;
import org.example.mocktest_test2.Util.CSVUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OrderRepository {
    private static final String FILE_PATH = "src/main/resources/Database/Order.txt";
    private static OrderRepository instance;

    private final Map<Integer, Order> orderCache = new HashMap<>();
    private boolean dataLoaded = false;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private OrderRepository() {}

    public static synchronized OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    private synchronized void loadData() {
        if (dataLoaded) return;

        try {
            List<String[]> data = CSVUtil.readCsv(FILE_PATH);

            // Load raw orders without resolving dependencies
            for (String[] row : data) {
                if (row.length >= 6) {
                    int orderId = Integer.parseInt(row[0]);
                    float totalPrice = Float.parseFloat(row[2]);
                    Date date = dateFormat.parse(row[3]);
                    int customerId = Integer.parseInt(row[4]);
                    int deliverymanId = Integer.parseInt(row[5]);

                    Order order = new Order(
                            orderId,
                            new ArrayList<>(), // Items to be resolved later
                            totalPrice,
                            date,
                            new Customer(customerId, null, null, null, null), // Placeholder customer
                            new Deliveryman(deliverymanId, null, null, null)  // Placeholder deliveryman
                    );
                    orderCache.put(orderId, order);
                }
            }

            resolveDependencies(); // Resolve dependencies after loading raw data
            dataLoaded = true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void resolveDependencies() {
        ItemRepository itemRepository = ItemRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        DeliverymanRepository deliverymanRepository = DeliverymanRepository.getInstance();

        List<Item> allItems = itemRepository.findAll(); // Load items once
        List<Customer> allCustomers = customerRepository.findAll(); // Load customers once
        List<Deliveryman> allDeliverymen = deliverymanRepository.findAll(); // Load deliverymen once

        for (Order order : orderCache.values()) {
            // Resolve items
            List<Item> items = Arrays.stream(order.getItems().stream().map(Item::getID).toArray(Integer[]::new))
                    .map(itemId -> allItems.stream()
                            .filter(item -> item.getID() == itemId)
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
            order.setItems(items);

            // Resolve customer
            order.setCustomer(allCustomers.stream()
                    .filter(c -> c.getID() == order.getCustomer().getID())
                    .findFirst()
                    .orElse(null));

            // Resolve deliveryman
            order.setDeliveryman(allDeliverymen.stream()
                    .filter(d -> d.getID() == order.getDeliveryman().getID())
                    .findFirst()
                    .orElse(null));
        }
    }

    public List<Order> findAll() {
        loadData();
        return new ArrayList<>(orderCache.values());
    }

    public Order findById(int id) {
        loadData();
        return orderCache.get(id);
    }

    public void save(Order order) {
        loadData();
        orderCache.put(order.getID(), order);
        writeToFile();
    }

    public void update(Order order) {
        loadData();
        if (orderCache.containsKey(order.getID())) {
            orderCache.put(order.getID(), order);
            writeToFile();
        }
    }

    public void delete(int id) {
        loadData();
        orderCache.remove(id);
        writeToFile();
    }

    private void writeToFile() {
        List<String[]> data = new ArrayList<>();
        for (Order order : orderCache.values()) {
            String items = order.getItems().stream()
                    .map(item -> String.valueOf(item.getID()))
                    .collect(Collectors.joining("|"));

            data.add(new String[]{
                    String.valueOf(order.getID()),
                    items,
                    String.valueOf(order.getTotalPrice()),
                    dateFormat.format(order.getDate()),
                    String.valueOf(order.getCustomer() != null ? order.getCustomer().getID() : ""),
                    String.valueOf(order.getDeliveryman() != null ? order.getDeliveryman().getID() : "")
            });
        }

        try {
            CSVUtil.writeCsv(FILE_PATH, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
