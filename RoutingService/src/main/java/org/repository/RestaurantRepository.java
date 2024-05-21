package org.repository;

import org.entity.FoodItem;
import org.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant findRestaurantById(String restaurantId);
    void addRestaurant(Restaurant restaurant);
    void addFoodItemInRestaurant(String restaurantId, FoodItem foodItem, int preparationTimeInMin);
    void removeRestaurant(String restaurantId);

    List<Restaurant> getAllRestaurant();
}
