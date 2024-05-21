package org.repository.impl;

import org.entity.FoodItem;
import org.entity.Restaurant;
import org.repository.RestaurantRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final Map<String,Restaurant> restaurantTable;

    public RestaurantRepositoryImpl() {
        this.restaurantTable = new HashMap<>();
    }

    @Override
    public Restaurant findRestaurantById(String restaurantId) {
        return restaurantTable.get(restaurantId);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurantTable.put(restaurant.getId(),restaurant);
    }

    @Override
    public void addFoodItemInRestaurant(String restaurantId, FoodItem foodItem, int preparationTimeInMin) {
        restaurantTable.get(restaurantId).addFoodItem(foodItem,preparationTimeInMin);
    }

    @Override
    public void removeRestaurant(String restaurantId) {
        restaurantTable.remove(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantTable.values().stream().toList();
    }
}
