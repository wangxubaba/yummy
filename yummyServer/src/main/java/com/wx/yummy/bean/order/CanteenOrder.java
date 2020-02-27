package com.wx.yummy.bean.order;

import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.bean.father.Canteen;

public class CanteenOrder extends Canteen {
    private Menu menu;
    private double distance;

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
