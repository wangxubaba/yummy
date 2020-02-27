package com.wx.yummy.bean.user;

import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.utility.enums.CanteenType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CanteenUser extends Canteen {
    protected String hostName;
    protected String idNumber;
    protected String email;
    protected String password;
    protected String account;

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String changeTimeFormat(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
