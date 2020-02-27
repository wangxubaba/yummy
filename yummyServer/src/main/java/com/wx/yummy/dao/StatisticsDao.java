package com.wx.yummy.dao;

import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.statistics.Mode2;
import com.wx.yummy.bean.statistics.Mode3;

import java.util.ArrayList;
import java.util.Date;

public interface StatisticsDao {
    //用户
    public String[][] getCanteensByMostOrdered(String customerID, Date date);

    public String[][] getDiscountSumPerMonth(String customerID);

    public String[][] getCanteenTypeOrderedNum(String customerID);

    public String[][] getCanteenTypeCashSum(String customerID);

    //餐厅
    public String[][] getMostPopularDishesThisTime(String canteenID);

    public String[][] getMostPopularDishesLastTime(String canteenID);

    public String[][] getCanteenOrdersNumPerDay(String canteenID);

    public String[][] getCanteenCashNumPerDay(String canteenID);

    public ArrayList<Mode2> getCancelOrdersNumPerMonthByType(String canteenID);

    //经理-餐厅
    public ArrayList<Mode2> getNewRegisterCanteensPerMonth();

    public String[][] getCanteensByType();

    //经理-客户
    public String[][] getNewRegisterCustomersPerMonth();

    public String[][] getCustomersNumGroupByProvince();

    //经理-财务
    public String[][] getMonthlyCash();

    //date指某个月，格式2019/02
    public String[][] getCashEarnedGroupByCareer(String date);

}
