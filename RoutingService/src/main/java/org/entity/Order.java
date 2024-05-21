package org.entity;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final Customer customer;
    private final Restaurant restaurant;
    private final List<FoodItem> foodItemOrderedList;
    private DeliveryPartner deliveryPartner;
    private OrderStatus orderStatus;

    public Order(Customer customer, Restaurant restaurant, List<FoodItem> foodItemOrderedList) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.restaurant = restaurant;
        this.foodItemOrderedList = foodItemOrderedList;
        this.orderStatus = OrderStatus.CREATED;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public DeliveryPartner getDeliveryPartner() {
        return deliveryPartner;
    }

    public List<FoodItem> getFoodItemOrderedList() {
        return foodItemOrderedList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                '}';
    }
}
