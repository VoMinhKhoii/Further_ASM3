package org.example.mocktest_test2.Model;

import java.util.List;

public class Deliveryman extends Person {
    private List<Order> orders;

    public Deliveryman(int ID, String name, String phoneNumber, List<Order> orders) {
        super(ID, name, phoneNumber);
        this.orders = orders;
    }
}
