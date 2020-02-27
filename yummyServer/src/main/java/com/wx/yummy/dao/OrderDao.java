package com.wx.yummy.dao;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;
import com.wx.yummy.bean.order.ManagerPay;
import com.wx.yummy.bean.order.OrderInfo;
import com.wx.yummy.bean.order.OrderedDishes;
import com.wx.yummy.utility.enums.OrderState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;

import java.util.ArrayList;
import java.util.Date;

public interface OrderDao {
    //返回插入后订单的id编号
    public int insertOrder(OrderInfo info);

    //返回更新后订单的id编号
    public int updateOrder(OrderInfo info);

    public OrderInfo getOrder(int orderID);

    public ArrayList<OrderInfo> getCustomerOrders(String customerID);

    public ArrayList<OrderInfo> getCustomerOrdersByCash(String customerID);

    public ArrayList<ArrayList<OrderInfo>> getCustomerOrdersByCanteen(String customerID);

    public ArrayList<OrderInfo> getCanteenOrdersByTime(String canteenID, Date date);

    public ArrayList<OrderInfo> getCanteenOrdersByCash(String canteenID, Date begin, Date end);

    public ArrayList<OrderInfo> getCanteenOrdersByStatePerDay(String canteenID, OrderState state, Date date);

    //查询该餐厅在某天菜品dishID卖了多少
    public int getOrdersContainDishesNum(String canteenID, Date date, int dishID);

    //查询该餐厅在某天菜品discountID卖了多少
    public int getOrdersContainDiscountsNum(String canteenID, Date date, int discountID);

    public ArrayList<OrderInfo> getCustomerOrdersByDate(String customerID, Date date);

    public ArrayList<ManagerPay> getAlreadyPayedManagerOrder();

    public ArrayList<ManagerPay> getNotPayedManagerOrder();

    public PayMessage managerPay(Date date);
}
