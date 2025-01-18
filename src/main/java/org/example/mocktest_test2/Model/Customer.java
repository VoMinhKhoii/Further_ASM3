package org.example.mocktest_test2.Model;

import java.util.List;
import java.util.stream.Collectors;

public class Customer extends Person{
    private String address;

    public Customer(int ID, String name, String phoneNumber, String address) {
        super(ID, name, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
