package org.example.mocktest_test2.Model;

import java.util.List;
import java.util.stream.Collectors;

public class Customer extends Person{
    private String address;
    private List<Order> orders;

    public Customer(int ID, String name, String phoneNumber, String address, List<Order> orders) {
        super(ID, name, phoneNumber);
        this.address = address;
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getOrderIDs() {
        if (orders == null || orders.isEmpty()) {
            return "No Orders";
        }
        return orders.stream()
                .map(order -> String.valueOf(order.getID()))
                .collect(Collectors.joining(", "));
    }
}
