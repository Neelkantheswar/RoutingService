package org.service;

import org.entity.DeliveryPartner;
import org.entity.FoodItem;
import org.entity.Order;
import org.entity.OrderStatus;
import org.notification.Observer;
import org.notification.Subject;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderService implements Subject {
    public abstract String createOrder(String customerId, String restaurantId, List<FoodItem> orderedFoodItemList);
    public abstract void updateOrderStatus(String orderId, OrderStatus orderStatus);
    public abstract void updateOrderDeliveryPartner(String orderId, DeliveryPartner deliveryPartner);
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Order order) {
        for (Observer observer : observers) {
            observer.update(order);
        }
    }
}
