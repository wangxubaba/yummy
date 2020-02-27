package com.wx.yummy.controller;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.manager.DailyRegisterCanteen;
import com.wx.yummy.bean.order.ManagerPay;
import com.wx.yummy.service.ManagerService;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/manage")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    //返回总经理需要审核的餐厅列表
    @RequestMapping(value = "/checkList",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<DailyRegisterCanteen> getRegisterCanteens(){
        return managerService.getCheckCanteens();
    }

    //总经理执行审核餐厅操作，通过则发邮件通知可以登陆，否则告知重新注册
    @RequestMapping(value = "/checkCanteen",method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage checkCanteen(@RequestBody CanteenCheck canteen){
        return managerService.checkCanteen(canteen);
    }

    //总经理获得未付款的订单信息
    @RequestMapping(value = "/get/unpayed",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<ManagerPay> getUnpayedOrders(){
        return managerService.getNotPayedOrders();
    }

    //总经理获得已付款的订单信息
    @RequestMapping(value = "/get/payed",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<ManagerPay> getPayedOrders(){
        return managerService.getPayedOrders();
    }

    //支付订单
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public @ResponseBody
    PayMessage pay(@RequestBody ManagerPay managerPay){
        return managerService.payOrder(managerPay.getOrdersDate());
    }
}
