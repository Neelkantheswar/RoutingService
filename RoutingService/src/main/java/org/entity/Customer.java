package org.entity;

import java.util.Random;
import java.util.UUID;

public class Customer implements RouteComponent{
    private final String id;
    private String name;
    private final String email;
    private Address address;

    public Customer(String name, String email, Address address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                " name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public void displayRouteDetails() {
        System.out.println(this);
    }
}
