package com.wx.yummy.bean.order;

import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.utility.enums.OrderState;

import java.util.Date;

public class OrderInfo {
    private int orderID;
    private int canteenID;
    private String customerID;
    private Date validTimeBegin;
    private OrderState state;
    private String destination;
    private int deliveryID;
    private double dishSum;
    private double deliverySum;
    private double discountSum;
    private OrderedDishes orderedDishes;
    private OrderState cancelState;
    private double distance;
    private double predictTime;
    private Customer customer;
    private Canteen canteen;

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public double getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPredictTime() {
        return predictTime;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPredictTime(double predictTime) {
        this.predictTime = predictTime;
    }

    public OrderState getCancelState() {
        return cancelState;
    }

    public void setCancelState(OrderState cancelState) {
        this.cancelState = cancelState;
    }

    public void setCanteenID(int canteenID) {
        this.canteenID = canteenID;
    }

    public Date getValidTimeBegin() {
        return validTimeBegin;
    }

    public double getDeliverySum() {
        return deliverySum;
    }

    public int getCanteenID() {
        return canteenID;
    }

    public double getDishSum() {
        return dishSum;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public int getOrderID() {
        return orderID;
    }

    public OrderedDishes getOrderedDishes() {
        return orderedDishes;
    }

    public OrderState getState() {
        return state;
    }

    public String getDestination() {
        return destination;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public void setDeliverySum(double deliverySum) {
        this.deliverySum = deliverySum;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDishSum(double dishSum) {
        this.dishSum = dishSum;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setValidTimeBegin(Date validTimeBegin) {
        this.validTimeBegin = validTimeBegin;
    }

    public void setOrderedDishes(OrderedDishes orderedDishes) {
        this.orderedDishes = orderedDishes;
    }
}
