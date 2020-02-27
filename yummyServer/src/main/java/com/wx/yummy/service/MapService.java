package com.wx.yummy.service;

import com.wx.yummy.bean.user.Address;

import java.util.ArrayList;

public interface MapService {
    public ArrayList<Address> getRecommendPlaces(String input, String province, String city, String area);

    public double calculateDistance(Address address1, Address address2);
}
