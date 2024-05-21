package org.service;

import org.entity.Address;
import org.entity.FoodCategory;
import org.entity.FoodItem;
import org.entity.Order;
import org.entity.Restaurant;

import java.util.List;

public interface RestaurantService{

    String addRestaurant(String restaurantName, Address address);
    void addFoodItemToRestaurant(String restaurantID,String foodName,FoodCategory foodCategoryType,int preparationTimeInMin);
    void deleteRestaurant(String restaurantID);

    Restaurant getRestaurant(String restaurantId);
    List<FoodItem> getAllFoodItemListForARestaurant(String restaurantID);
    void prepareMeal(Restaurant restaurant,Order order);
}
