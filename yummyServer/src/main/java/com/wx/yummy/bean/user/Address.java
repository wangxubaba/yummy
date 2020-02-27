package com.wx.yummy.bean.user;

public class Address {
    private String province;
    private String city;
    private String area;
    private String detail;
    private double lat;
    private double lng;

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public Address(){
    }

    public Address(String province, String city, String area, String detail){
        this.province=province;
        this.city=city;
        this.area = area;
        this.detail=detail;
    }

    public String getCity() {
        return city;
    }

    public String getDetail() {
        return detail;
    }

    public String getArea() {
        return area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress(){
        return province+city+ area +detail;
    }
}
