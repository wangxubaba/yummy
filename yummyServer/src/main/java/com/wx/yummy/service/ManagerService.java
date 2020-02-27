package com.wx.yummy.service;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.manager.DailyRegisterCanteen;
import com.wx.yummy.bean.order.ManagerPay;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;

import java.util.ArrayList;
import java.util.Date;

public interface ManagerService {
    //获取近7天需要管理员审核及已经审核的餐厅信息列表
    public ArrayList<DailyRegisterCanteen> getCheckCanteens();

    //处理总经理审核结果
    public ResultMessage checkCanteen(CanteenCheck canteen);

    //获取未支付的订单
    public ArrayList<ManagerPay> getNotPayedOrders();

    //获取已支付的订单
    public ArrayList<ManagerPay> getPayedOrders();

    //支付订单
    public PayMessage payOrder(Date ordersDate);
}
