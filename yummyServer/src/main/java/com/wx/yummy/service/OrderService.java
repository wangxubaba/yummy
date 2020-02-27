package com.wx.yummy.service;

import com.wx.yummy.bean.order.CanteenOrder;
import com.wx.yummy.bean.order.OrderInfo;
import com.wx.yummy.bean.order.OrdersPerType;
import com.wx.yummy.bean.statistics.Mode4;
import com.wx.yummy.bean.user.Address;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.utility.enums.DiscountType;
import com.wx.yummy.utility.enums.OrderState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.Date;

public interface OrderService {
    public ArrayList<CanteenOrder> getNearCanteens(String customerID);

    public double calculateDistance(Address customerAddress, Address canteenAddress);

    public ArrayList<String> confirmMenu(OrderInfo info);

    public PayMessage confirmPay(int orderID, String accountID, String accountPassword, double cash);

    public ArrayList<OrderInfo> getCustomerOrders(String customerID);

    public ArrayList<OrderInfo> getCustomerOrdersByCash(String customerID);

    public ArrayList<ArrayList<OrderInfo>> getCustomerOrdersByCanteen(String customerID);

    public ResultMessage cancelOrder(int orderID);

    public double calculateBackMoney(OrderInfo info);

    public OrdersPerType getCanteenOrders(String canteenID);

    public ArrayList<Mode4> getCanteenOrdersByTime(String canteenID, Date begin, Date end);

    public Mode4 getCanteenOrdersByCash(String canteenID, Date begin, Date end);

    public OrdersPerType getCanteenOrdersByType(String canteenID, Date begin, Date end);

    public ResultMessage changeOrderState(int orderID, OrderState state);

    public DiscountType getDiscountType(Customer customer);
}
