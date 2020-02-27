package com.wx.yummy.bean.dish;

import java.util.ArrayList;

public class Discount {
    private int id;
    private String name;
    private Double discount_price;
    private ArrayList<Dish> dishes;
    private int amount;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setDiscount_price(Double discount_price) {
        this.discount_price = discount_price;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public Double getDiscount_price() {
        return discount_price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
