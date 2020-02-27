package com.wx.yummy.bean.dish;

import java.util.ArrayList;

public class Menu {
    private String canteenID;
    private String beginTime; //菜单生效的开始时间，格式2019-01-01
    private String endTime;
    private ArrayList<DishType> types;
    private ArrayList<Discount> dishDiscount;

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getCanteenID() {
        return canteenID;
    }

    public void setTypes(ArrayList<DishType> types) {
        this.types = types;
    }

    public ArrayList<DishType> getTypes() {
        return types;
    }

    public void setCanteenID(String canteenID) {
        this.canteenID = canteenID;
    }

    public ArrayList<Discount> getDishDiscount() {
        return dishDiscount;
    }

    public void setDishDiscount(ArrayList<Discount> dishDiscount) {
        this.dishDiscount = dishDiscount;
    }

}

