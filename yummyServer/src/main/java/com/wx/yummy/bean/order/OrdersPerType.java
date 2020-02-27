package com.wx.yummy.bean.order;

import java.util.ArrayList;

public class OrdersPerType {
    private ArrayList<OrderInfo> newOrder=new ArrayList<>();
    private ArrayList<OrderInfo> solving=new ArrayList<>();
    private ArrayList<OrderInfo> waitingDelivery=new ArrayList<>();
    private ArrayList<OrderInfo> delivering=new ArrayList<>();
    private ArrayList<OrderInfo> sign=new ArrayList<>();
    private ArrayList<OrderInfo> cancel=new ArrayList<>();

    public ArrayList<OrderInfo> getDelivering() {
        return delivering;
    }

    public ArrayList<OrderInfo> getCancel() {
        return cancel;
    }

    public ArrayList<OrderInfo> getNewOrder() {
        return newOrder;
    }

    public ArrayList<OrderInfo> getSign() {
        return sign;
    }

    public ArrayList<OrderInfo> getSolving() {
        return solving;
    }

    public ArrayList<OrderInfo> getWaitingDelivery() {
        return waitingDelivery;
    }

    public void setCancel(ArrayList<OrderInfo> cancel) {
        this.cancel = cancel;
    }

    public void setNewOrder(ArrayList<OrderInfo> newOrder) {
        this.newOrder = newOrder;
    }

    public void setDelivering(ArrayList<OrderInfo> delivering) {
        this.delivering = delivering;
    }

    public void setSign(ArrayList<OrderInfo> sign) {
        this.sign = sign;
    }

    public void setSolving(ArrayList<OrderInfo> solving) {
        this.solving = solving;
    }

    public void setWaitingDelivery(ArrayList<OrderInfo> waitingDelivery) {
        this.waitingDelivery = waitingDelivery;
    }
}
