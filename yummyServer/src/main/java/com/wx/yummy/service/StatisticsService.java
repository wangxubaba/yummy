package com.wx.yummy.service;

import com.wx.yummy.bean.statistics.Mode2;

import java.util.ArrayList;

public interface StatisticsService {
    //用户统计数据
    //某用户点餐最多的前十个餐厅
    public String[][] first10CanteensCustomerOrdered(String customerID);

    //每月会员优惠的价格
    public String[][] VIPDiscountSumPerMonth(String customerID);

    //点餐餐厅按照餐厅分类提供比例
    public String[][]  customerOrderedCanteensGroupByType(String customerID);

    public String[][] getCanteenTypeCashSum(String customerID);

    //餐厅统计数据
    //本月销量最好的菜品

    //每天订单的数量

    // 每天销售额

    //每个月在不同状态下取消订单的数量（多个折线图并在一起）

    //经理
    //餐厅
    //近六月每月新注册餐厅数（折线）各类别餐厅注册量（内部折线）
    public ArrayList<Mode2> getNewRegisterCanteensPerMonth();
    //现有所有餐厅分类别比例图（饼图）
    public String[][] getCanteensByType();

    //本月餐厅销售额前十（金额，餐厅具体信息）

    //客户
    //近六月每月新注册用户数（折线）
    public String[][] getNewRegisterCustomersPerMonth();
    //顾客分布省份比例图（标明前三，标明倒三）
    public String[][] getCustomersNumGroupByProvince();
    //财务
    //各月销售额（折线）
    public String[][] getMonthlyCash();

    //通过会员挣的钱和通过普通用户挣的钱还有配送挣的钱的饼图
    public String[][] getCashEarnedGroupByCareer(String date);
}
