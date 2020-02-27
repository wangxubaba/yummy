package com.wx.yummy.dao;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;
import com.wx.yummy.bean.dish.DishType;
import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.utility.enums.ResultMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface MenuDao {
    public Menu initialNewMenuTime(String canteenID);

    public Menu initialTodayMenuTime(String canteenID, int today);

    public ArrayList<DishType> getDishTypes(String canteenID, int day);

    public ArrayList<Discount> getDiscounts(String canteenID, int day);

    public int getLastEndTime(String canteenID);

    public ResultMessage setDishes(String canteenID, ArrayList<DishType> types, int begin, int end);

    public ResultMessage setDiscounts(String canteenID, ArrayList<Discount> discounts, int begin, int end);

    public Dish getDishByID(String canteenID, int dishID);

    //这个discount中没有菜品信息
    public Discount getDiscountByID(String canteenID, int dishID);
}
