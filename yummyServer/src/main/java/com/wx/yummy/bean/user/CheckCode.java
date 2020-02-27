package com.wx.yummy.bean.user;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckCode {
    private String email;
    private String checkCode;
    private Date constructTime;

    public CheckCode(String email, String checkCode, Date constructTime){
        this.email=email;
        this.checkCode=checkCode;
        this.constructTime=constructTime;
    }

    public CheckCode(String email, String checkCode, String time){
        this.email=email;
        this.checkCode=checkCode;
        this.constructTime=new Date(time);
    }

    public CheckCode(){}

    public String getEmail() {
        return email;
    }

    public String getConstructTime() {
        return changeTimeFormat(constructTime);
    }

    public String getCheckCode() {
        return checkCode;
    }

    public int getIntervalSeconds(Date date){
        long diff=date.getTime()-constructTime.getTime();
        double seconds=diff*1.0/1000;
        return (int)seconds;
    }

    private String changeTimeFormat(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
