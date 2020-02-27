package com.wx.yummy.bean.order;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;

import java.util.ArrayList;

public class OrderedDishes {
    private ArrayList<Dish> dishes;
    private ArrayList<Discount> discounts;

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setDiscounts(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }
}
