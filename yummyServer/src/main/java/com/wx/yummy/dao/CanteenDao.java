package com.wx.yummy.dao;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.order.CanteenOrder;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.ResultMessage;

import java.util.ArrayList;
import java.util.Date;

public interface CanteenDao {
    public ArrayList<CanteenCheck> getWaitingCheckCanteens(Date date);

    public ArrayList<CanteenCheck> getAlreadyCheckedCanteens(Date date);

    public ResultMessage changeCanteenCheckState(String canteenID, CanteenCheckState state);

    public ResultMessage changeCanteenUpdateState(String canteenID, CanteenCheckState state);

    public ResultMessage createCanteenDishTable(String canteenID);

    public ArrayList<CanteenOrder> getNearCanteens(String province,String city, String area);

}
