package org.service.impl;

import org.entity.DeliveryPartner;
import org.entity.DeliveryStatus;
import org.entity.Order;
import org.service.DeliveryPartnerService;
import org.service.OrderAssignmentService;
import org.service.OrderService;
import org.service.RestaurantService;
import org.service.RoutingService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OrderAssignmentServiceImpl implements OrderAssignmentService {
    private final DeliveryPartnerService deliveryPartnerService;
    private final RestaurantService restaurantService;

    private final OrderService orderService;
    private final Deque<Order> unassignedOrderQueue;

    public OrderAssignmentServiceImpl(DeliveryPartnerService deliveryPartnerService, RestaurantService restaurantService, OrderService orderService) {
        this.deliveryPartnerService = deliveryPartnerService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
        this.unassignedOrderQueue = new ArrayDeque<>();
    }
    @Override
    public void update(Order order) {
        this.unassignedOrderQueue.addLast(order);
    }

    @Override
    public void assignOrdersToDeliveryPartner() {
        DeliveryPartner firstAvailableDeliveryPartner = deliveryPartnerService.getAllAvailableDeliveryPartner().get(0);
        deliveryPartnerService.updateDeliveryPartnerStatus(firstAvailableDeliveryPartner.getId(), DeliveryStatus.ON_DELIVERY);
        List<Order> orderList = new ArrayList<>();
        for (Order order : unassignedOrderQueue) {
            orderList.add(order);
            unassignedOrderQueue.removeFirst();
            orderService.updateOrderDeliveryPartner(order.getId(),firstAvailableDeliveryPartner);
            restaurantService.prepareMeal(order.getRestaurant(),order);
        }
        int deliveryNumber = deliveryPartnerService.addNewOrderListToDeliveryPartner(firstAvailableDeliveryPartner.getId(), orderList);
    }
}
