package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.DishType;
import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.dao.MenuDao;
import com.wx.yummy.service.CanteenService;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CanteenServiceImpl implements CanteenService {
    @Autowired
    MenuDao menuDao;

    @Override
    public Menu getTodayMenu(String canteenID) {
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
        int today=Integer.parseInt(format.format(date));
        Menu menu=menuDao.initialTodayMenuTime(canteenID,today);
        ArrayList<DishType> dishTypes=menuDao.getDishTypes(canteenID,today);
        ArrayList<Discount> discounts=menuDao.getDiscounts(canteenID,today);
        menu.setDishDiscount(discounts);
        menu.setTypes(dishTypes);
        return menu;
    }

    @Override
    public Menu getLatestMenu(String canteenID) {
        Menu menu=menuDao.initialNewMenuTime(canteenID);
        int endTime=menuDao.getLastEndTime(canteenID);
        ArrayList<DishType> dishTypes=menuDao.getDishTypes(canteenID,endTime);
        ArrayList<Discount> discounts=menuDao.getDiscounts(canteenID,endTime);
        menu.setDishDiscount(discounts);
        menu.setTypes(dishTypes);
        return menu;
    }

    @Override
    public ResultMessage setNextMenu(Menu menu) {
        menuDao.setDishes(menu.getCanteenID(),menu.getTypes(),Integer.parseInt(menu.getBeginTime()),Integer.parseInt(menu.getEndTime()));
        menuDao.setDiscounts(menu.getCanteenID(),menu.getDishDiscount(),Integer.parseInt(menu.getBeginTime()),Integer.parseInt(menu.getEndTime()));
        return ResultMessage.SUCCESS;
    }

}
