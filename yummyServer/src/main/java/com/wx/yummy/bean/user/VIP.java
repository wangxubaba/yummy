package com.wx.yummy.bean.user;

import java.util.Date;

public class VIP {
    private int id;
    private int uid;
    private Date begin;
    private Date end;
    private boolean valid;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public Date getBegin() {
        return begin;
    }

    public boolean isValid() {
        return valid;
    }

    public Date getEnd() {
        return end;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
