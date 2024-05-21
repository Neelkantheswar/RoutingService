package org.entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryPartner implements RouteComponent {
    private final String id;
    private final String email;
    private String name;
    private String phoneNumber;
    private Address address;
    private int deliveryRating;
    private DeliveryStatus isActive;

    public DeliveryPartner(String name,String email, Address address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(int deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public DeliveryStatus getIsActive() {
        return isActive;
    }

    public void setIsActive(DeliveryStatus isActive) {
        this.isActive = isActive;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "DeliveryPartner{" +
                " name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public void displayRouteDetails() {
        System.out.println(this);
    }
}
