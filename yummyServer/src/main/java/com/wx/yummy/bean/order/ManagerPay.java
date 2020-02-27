package com.wx.yummy.bean.order;

import java.util.Date;

public class ManagerPay {
    private int id;
    private double dishSum;
    private double discountSum;
    private double deliverySum;
    private double chargeAmount;
    private Date ordersDate;
    private Date payDate;
    private boolean valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public double getDiscountSum() {
        return discountSum;
    }

    public void setDishSum(double dishSum) {
        this.dishSum = dishSum;
    }

    public void setDeliverySum(double deliverySum) {
        this.deliverySum = deliverySum;
    }

    public double getDishSum() {
        return dishSum;
    }

    public double getDeliverySum() {
        return deliverySum;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public Date getOrdersDate() {
        return ordersDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
