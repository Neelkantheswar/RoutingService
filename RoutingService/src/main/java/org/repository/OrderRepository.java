package org.repository;

import org.entity.DeliveryPartner;
import org.entity.Order;
import org.entity.OrderStatus;

import java.util.List;

public interface OrderRepository {
    void createOrder(Order order);
    Order findOrderById(String orderId);
    void updateOrderStatus(String orderId, OrderStatus newStatus);

    void updateOrderDeliveryPartner(String orderId, DeliveryPartner deliveryPartner);
}
