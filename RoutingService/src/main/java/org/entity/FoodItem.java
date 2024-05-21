package org.entity;

public class FoodItem {
    private String name;
    private FoodCategory foodCategoryType;
    private String foodIngredientsDescription;

    public FoodItem(String name, FoodCategory foodCategoryType) {
        this.name = name;
        this.foodCategoryType = foodCategoryType;
    }

    public void setFoodIngredientsDescription(String foodIngredientsDescription) {
        this.foodIngredientsDescription = foodIngredientsDescription;
    }

    public String getName() {
        return name;
    }

    public FoodCategory getFoodCategoryType() {
        return foodCategoryType;
    }

    public String getFoodIngredientsDescription() {
        return foodIngredientsDescription;
    }
}
