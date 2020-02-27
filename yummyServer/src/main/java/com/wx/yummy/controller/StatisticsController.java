package com.wx.yummy.controller;

import com.wx.yummy.bean.statistics.Mode2;
import com.wx.yummy.dao.StatisticsDao;
import com.wx.yummy.service.StatisticsService;
import com.wx.yummy.utility.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;
    //总经理
    //获取每月新注册餐厅信息
    @RequestMapping(value = "/get/new/register/canteens/per/month", method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<Mode2> getNewRegisterCanteensPerMonth(){
        return statisticsService.getNewRegisterCanteensPerMonth();
    }

    //获取现有餐厅分类的数量
    @RequestMapping(value = "/get/canteens/by/type", method = RequestMethod.POST)
    public @ResponseBody
    String[][] getCanteensByType(){
        return statisticsService.getCanteensByType();
    }

    //获取每月新注册用户信息
    @RequestMapping(value = "/get/new/register/customer/per/month", method = RequestMethod.POST)
    public @ResponseBody
    String[][] getNewRegisterCustomerPerMonth(){
        return statisticsService.getNewRegisterCustomersPerMonth();
    }

    //获取各省用户数
    @RequestMapping(value = "/get/customers/by/province", method = RequestMethod.POST)
    public @ResponseBody
    String[][] getCustomersByProvince(){
        return statisticsService.getCustomersNumGroupByProvince();
    }

    //获取没用金额
    @RequestMapping(value = "/get/monthly/cash", method = RequestMethod.POST)
    public @ResponseBody
    String[][] getMonthLyCash(){
        return statisticsService.getMonthlyCash();
    }

    //获取各省用户数
    @RequestMapping(value = "/cash/career", method = RequestMethod.POST)
    public @ResponseBody
    String[][] getCashByCareer(){
        return statisticsService.getCashEarnedGroupByCareer("2019/03");
    }
}
