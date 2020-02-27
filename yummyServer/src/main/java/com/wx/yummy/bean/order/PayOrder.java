package com.wx.yummy.bean.order;

public class PayOrder {
    private OrderInfo info;
    private Balance balance;

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Balance getBalance() {
        return balance;
    }

    public OrderInfo getInfo() {
        return info;
    }

    public void setInfo(OrderInfo info) {
        this.info = info;
    }
}
