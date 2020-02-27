package com.wx.yummy.bean.dish;

import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.utility.enums.CanteenCheckState;

public class CanteenCheck extends CanteenUser {
    protected CanteenCheckState checkState;
    protected String applyDate;

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public CanteenCheckState getCheckState() {
        return checkState;
    }

    public void setCheckState(CanteenCheckState checkState) {
        this.checkState = checkState;
    }
}
