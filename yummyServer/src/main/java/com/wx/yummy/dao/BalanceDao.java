package com.wx.yummy.dao;

import com.wx.yummy.utility.enums.PayMessage;

public interface BalanceDao {
     public PayMessage pay(String accountID, String password, double cash);
     public PayMessage charge(String accountID, double cash);
     public PayMessage transfer(String fromID, String toID, double cash);
}
