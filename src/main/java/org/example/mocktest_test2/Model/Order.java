package org.example.mocktest_test2.Model;

import java.util.Date;
import java.util.List;

public class Order {
    private int ID;
    private List<Item> items;
    private float totalPrice;
    private Date date;
    private Customer customer;
    private Deliveryman deliveryman;

    public Order(int ID, List<Item> items, float totalPrice, Date date, Customer customer, Deliveryman deliveryman) {
        this.ID = ID;
        this.items = items;
        this.totalPrice = totalPrice;
        this.date = date;
        this.customer = customer;
        this.deliveryman = deliveryman;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        for (Item i : this.items){
            this.totalPrice += i.getPrice();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Deliveryman getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(Deliveryman deliveryman) {
        this.deliveryman = deliveryman;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
