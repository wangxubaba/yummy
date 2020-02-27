package com.wx.yummy.service;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.bean.user.VIP;
import com.wx.yummy.utility.enums.LoginMessage;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.RegisterMessage;
import com.wx.yummy.utility.enums.ResultMessage;


public interface UserService {
    //用户注册第一步，填写邮箱获取验证码
    public RegisterMessage customerFirstRegister(String email);

    //用户注册第二步，填写验证码
    public RegisterMessage customerConfirmCheck(String email, String checkCode);

    //用户注册第三步，完成基本信息录入
    public RegisterMessage customerReviseRegister(Customer customer);

    //餐厅注册第一步，完成基本信息录入
    public RegisterMessage canteenRegister(CanteenCheck canteen);

    //餐厅注册第二步，等待总经理审核，将结果发送到注册人邮箱中
    public ResultMessage managerCheck(CanteenUser canteenUser, ResultMessage result);

    //登陆
    public LoginMessage login(String username, String password);

    //获得客户信息
    public Customer getCustomer(String username);

    //获得餐厅信息
    public CanteenUser getCanteen(String username);

    public ResultMessage updateCustomer(Customer customer);

    public ResultMessage updateCanteen(CanteenUser canteenUser);

    public PayMessage PayVIPByAccount(Customer customer,String password);

    public PayMessage PayVIPByCredits(Customer customer);

    public VIP getVIP(String email);

    public ResultMessage modifyCanteen(CanteenCheck canteen);

    public ResultMessage cancelUser(String username);
}
