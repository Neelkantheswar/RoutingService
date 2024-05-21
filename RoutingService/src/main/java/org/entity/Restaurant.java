package org.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Restaurant implements RouteComponent{
    private final String id;
    private String name;
    private Address address;
    private Map<FoodItem,Integer> foodItemToItsPreprationTimeMap;

    public Restaurant(String name, Address address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.foodItemToItsPreprationTimeMap = new HashMap<>();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getFoodItemPreprationTime(FoodItem foodItem) {
        return foodItemToItsPreprationTimeMap.get(foodItem);
    }

    public void addFoodItem(FoodItem foodItem, Integer preprationTime){
        foodItemToItsPreprationTimeMap.put(foodItem,preprationTime);
    }

    public List<FoodItem> getAllFoodItemList(){
        return foodItemToItsPreprationTimeMap.keySet().stream().toList();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                " name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public void displayRouteDetails() {
        System.out.println(this);
    }
}
