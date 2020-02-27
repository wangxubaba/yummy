package com.wx.yummy.bean.dish;

import java.util.ArrayList;

public class DishType {
    private String name;
    private ArrayList<Dish> dishes;

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
}
