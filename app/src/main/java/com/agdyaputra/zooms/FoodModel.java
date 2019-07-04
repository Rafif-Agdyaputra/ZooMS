package com.agdyaputra.zooms;

public class FoodModel {
    private String food_id, food_name, stock;

    public FoodModel() {
    }

    public FoodModel(String foodID, String foodName, String foodStock) {
        this.food_id = foodID;
        this.food_name = foodName;
        this.stock = foodStock;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
