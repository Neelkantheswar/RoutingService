package org.service.impl;

import org.entity.Address;
import org.entity.FoodCategory;
import org.entity.FoodItem;
import org.entity.Order;
import org.entity.Restaurant;
import org.repository.RestaurantRepository;
import org.service.OrderService;
import org.service.RestaurantService;

import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public void prepareMeal(Restaurant restaurant, Order order) {
        /*
        This method basically notifies Restaurant about the order so that it can start preparing orderedFoodItemsList.
         */
    }

    @Override
    public String addRestaurant(String restaurantName, Address address) {
        Restaurant restaurant = new Restaurant(restaurantName, address);
        restaurantRepository.addRestaurant(restaurant);
        return restaurant.getId();
    }

    @Override
    public void addFoodItemToRestaurant(String restaurantId, String foodName, FoodCategory foodCategoryType, int preparationTimeInMin) {
        FoodItem foodItem = new FoodItem(foodName, foodCategoryType);
        restaurantRepository.addFoodItemInRestaurant(restaurantId, foodItem,preparationTimeInMin);
    }

    @Override
    public void deleteRestaurant(String restaurantId) {
        restaurantRepository.removeRestaurant(restaurantId);
    }

    @Override
    public Restaurant getRestaurant(String restaurantId) {
        return restaurantRepository.findRestaurantById(restaurantId);
    }

    @Override
    public List<FoodItem> getAllFoodItemListForARestaurant(String restaurantId) {
        return restaurantRepository.findRestaurantById(restaurantId).getAllFoodItemList();
    }
}
