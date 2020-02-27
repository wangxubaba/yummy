package com.wx.yummy.dao;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.bean.user.CheckCode;
import com.wx.yummy.bean.user.VIP;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.LoginMessage;
import com.wx.yummy.utility.enums.ResultMessage;

public interface UserDao {
    public ResultMessage addNewCheckCode(CheckCode checkCode);

    public ResultMessage removeCheckCode(String email);

    public CheckCode getCheckCode(String email);

    public ResultMessage registerUser(Customer customer);

    public ResultMessage registerCanteen(CanteenCheck canteen);

    public ResultMessage changeCanteenCheckType(String canteenID, CanteenCheckState state);

    public ResultMessage removeInvalidCanteen(String canteenID);

    public LoginMessage customerLogin(String username, String password);

    public LoginMessage canteenLogin(String username, String password);

    public LoginMessage adminLogin(String username, String password);

    public Customer getCustomer(String username);

    public CanteenUser getCanteen(String username);

    public ResultMessage updateCanteen(CanteenUser canteen);

    public ResultMessage updateCustomer(Customer customer);

    public VIP getVIP(int uid);

    public ResultMessage setVIP(VIP vip);

    public ResultMessage insertModifiedCanteen(CanteenCheck canteen);

    public ResultMessage cancelRegister(String username);

}
