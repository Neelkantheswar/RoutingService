package org.repository.impl;

import org.entity.DeliveryPartner;
import org.entity.Order;
import org.entity.OrderStatus;
import org.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {
    private final Map<String,Order> orderTable;

    public OrderRepositoryImpl() {
        this.orderTable = new HashMap<>();
    }

    @Override
    public void createOrder(Order order) {
        orderTable.put(order.getId(),order);
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderTable.get(orderId);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        orderTable.get(orderId).setOrderStatus(newStatus);
    }

    @Override
    public void updateOrderDeliveryPartner(String orderId, DeliveryPartner deliveryPartner) {
        orderTable.get(orderId).setDeliveryPartner(deliveryPartner);
    }
}
