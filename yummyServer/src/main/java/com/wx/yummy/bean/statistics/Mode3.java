package com.wx.yummy.bean.statistics;

import com.wx.yummy.bean.user.CanteenUser;

public class Mode3 {
    private double total_cash;
    private CanteenUser canteen;

    public CanteenUser getCanteen() {
        return canteen;
    }

    public double getTotal_cash() {
        return total_cash;
    }

    public void setTotal_cash(double total_cash) {
        this.total_cash = total_cash;
    }

    public void setCanteen(CanteenUser canteen) {
        this.canteen = canteen;
    }
}
