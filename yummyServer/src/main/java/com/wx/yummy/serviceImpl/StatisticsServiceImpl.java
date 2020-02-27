package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.statistics.Mode2;
import com.wx.yummy.dao.StatisticsDao;
import com.wx.yummy.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsDao statisticsDao;
    @Override//上月
    public String[][] first10CanteensCustomerOrdered(String customerID) {
        return statisticsDao.getCanteensByMostOrdered(customerID,getLastMonth(new Date()));
    }

    @Override//购置了VIP的月
    public String[][] VIPDiscountSumPerMonth(String customerID) {
        return statisticsDao.getDiscountSumPerMonth(customerID);
    }

    @Override//至今为止
    public String[][] customerOrderedCanteensGroupByType(String customerID) {
        return statisticsDao.getCanteenTypeOrderedNum(customerID);
    }

    @Override
    public String[][] getCanteenTypeCashSum(String customerID) {
        return new String[0][];
    }

    @Override
    public ArrayList<Mode2> getNewRegisterCanteensPerMonth() {
        return statisticsDao.getNewRegisterCanteensPerMonth();
    }

    @Override
    public String[][] getCanteensByType() {
        return statisticsDao.getCanteensByType();
    }

    @Override
    public String[][] getNewRegisterCustomersPerMonth() {
        return statisticsDao.getNewRegisterCustomersPerMonth();
    }

    @Override
    public String[][] getCustomersNumGroupByProvince() {
        return statisticsDao.getCustomersNumGroupByProvince();
    }

    @Override
    public String[][] getMonthlyCash() {
        return statisticsDao.getMonthlyCash();
    }

    @Override
    public String[][] getCashEarnedGroupByCareer(String date) {
        return statisticsDao.getCashEarnedGroupByCareer(date);
    }

    private Date getLastMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTime();
    }

    private Date getLastDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        return calendar.getTime();
    }
}
