package com.wx.yummy.bean.manager;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.utility.enums.CanteenCheckState;

import java.util.ArrayList;

public class DailyRegisterCanteen {
    String date;
    int waiting;
    int checked;
    int refuse;
    int pass;
    ArrayList<CanteenCheck> waitingCheckCanteens;
    ArrayList<CanteenCheck> alreadyCheckedCanteens;

    public void setCounts(){
        waiting=waitingCheckCanteens.size();
        checked=alreadyCheckedCanteens.size();
        for(int i=0;i<alreadyCheckedCanteens.size();i++){
            if(alreadyCheckedCanteens.get(i).getCheckState()==CanteenCheckState.pass){
                pass++;
            }
            else if(alreadyCheckedCanteens.get(i).getCheckState()==CanteenCheckState.refuse){
                refuse++;
            }
        }
    }

    public int getChecked() {
        return checked;
    }

    public int getPass() {
        return pass;
    }

    public int getRefuse() {
        return refuse;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public void setRefuse(int refuse) {
        this.refuse = refuse;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public ArrayList<CanteenCheck> getAlreadyCheckedCanteens() {
        return alreadyCheckedCanteens;
    }

    public ArrayList<CanteenCheck> getWaitingCheckCanteens() {
        return waitingCheckCanteens;
    }

    public String getDate() {
        return date;
    }

    public void setAlreadyCheckedCanteens(ArrayList<CanteenCheck> alreadyCheckedCanteens) {
        this.alreadyCheckedCanteens = alreadyCheckedCanteens;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWaitingCheckCanteens(ArrayList<CanteenCheck> waitingCheckCanteens) {
        this.waitingCheckCanteens = waitingCheckCanteens;
    }
}
