package com.wx.yummy.bean.statistics;

import java.util.ArrayList;

public class Mode2 {
    private String name;
    private int int_data;
    private double double_data;
    private ArrayList<Mode1> array;

    public void setInt_data(int int_data) {
        this.int_data = int_data;
    }

    public void setDouble_data(double double_data) {
        this.double_data = double_data;
    }

    public int getInt_data() {
        return int_data;
    }

    public double getDouble_data() {
        return double_data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Mode1> getArray() {
        return array;
    }

    public void setArray(ArrayList<Mode1> array) {
        this.array = array;
    }
}
