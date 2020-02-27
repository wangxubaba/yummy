package com.wx.yummy.service;

import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.utility.enums.ResultMessage;

public interface CanteenService {
    //获得当天的菜单
    public Menu getTodayMenu(String canteenID);

    //获得上一次修改过的菜单
    public Menu getLatestMenu(String canteenID);

    //设置下一次的菜单
    public ResultMessage setNextMenu(Menu menu);
}
