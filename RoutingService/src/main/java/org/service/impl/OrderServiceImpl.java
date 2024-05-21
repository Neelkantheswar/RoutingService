package org.service.impl;

import org.entity.Customer;
import org.entity.DeliveryPartner;
import org.entity.FoodItem;
import org.entity.Order;
import org.entity.OrderStatus;
import org.entity.Restaurant;
import org.repository.OrderRepository;
import org.service.CustomerService;
import org.service.OrderService;
import org.service.RestaurantService;

import java.util.List;

public class OrderServiceImpl extends OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
    }


    public String createOrder(String customerId, String restaurantId, List<FoodItem> orderedFoodItemList){
        Customer customer = customerService.getCustomer(customerId);
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        Order order = new Order(customer, restaurant, orderedFoodItemList);
        orderRepository.createOrder(order);
        notifyObserver(order);
        return order.getId();
    }

    public void updateOrderStatus(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId,orderStatus);
    }

    public void updateOrderDeliveryPartner(String orderId, DeliveryPartner deliveryPartner){
        orderRepository.updateOrderDeliveryPartner(orderId,deliveryPartner);
    }
}
