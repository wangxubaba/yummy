package com.wx.yummy.bean.father;

import com.wx.yummy.utility.enums.CanteenType;

public class Canteen {
    protected int id;
    protected String name;
    protected String address;
    protected CanteenType type;
    protected String beginTime;
    protected String endTime;
    protected String province;
    protected String city;
    protected String area;
    protected double lat;
    protected double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getId() {
        String tempID=id+"";
        int length=8-tempID.length();
        String result="";
        for(int i=0;i<length;i++){
            result+="0";
        }
        result=result+tempID;
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(CanteenType type) {
        this.type = type;
    }

    public CanteenType getType() {
        return type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
