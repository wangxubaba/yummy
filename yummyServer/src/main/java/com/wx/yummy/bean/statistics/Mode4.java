package com.wx.yummy.bean.statistics;

import com.wx.yummy.bean.order.OrderInfo;
import com.wx.yummy.utility.enums.OrderState;

import java.util.ArrayList;

public class Mode4 {
    private ArrayList<OrderInfo> infos;
    private String name;
    private ArrayList<Mode1> statistics;

    public ArrayList<Mode1> getStatistics() {
        return statistics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OrderInfo> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<OrderInfo> infos) {
        this.infos = infos;
    }

    public void statistics1(){
        int waitingPay=0;
        int waitingAccept=0;
        int making=0;
        int waitingDelivery=0;
        int delivering=0;
        int accept=0;
        int cancel=0;
        for(int i=0;i<infos.size();i++){
            if(infos.get(i).getState()==OrderState.WaitingPay){
                waitingPay++;
            }
            else if(infos.get(i).getState()==OrderState.WaitingAccept){
                waitingAccept++;
            }
            else if(infos.get(i).getState()==OrderState.Making){
                making++;
            }
            else if(infos.get(i).getState()==OrderState.WaitingDelivery){
                waitingDelivery++;
            }
            else if(infos.get(i).getState()==OrderState.Delivering){
                delivering++;
            }
            else if(infos.get(i).getState()==OrderState.Arrived){
                accept++;
            }
            else if(infos.get(i).getState()==OrderState.Cancel){
                cancel++;
            }
        }

        Mode1 temp1=new Mode1();
        temp1.setName("待支付");
        temp1.setInt_data(waitingPay);
        Mode1 temp2=new Mode1();
        temp2.setName("待接收");
        temp2.setInt_data(waitingAccept);
        Mode1 temp3=new Mode1();
        temp3.setName("制作中");
        temp3.setInt_data(making);
        Mode1 temp4=new Mode1();
        temp4.setName("待配送");
        temp4.setInt_data(waitingDelivery);
        Mode1 temp5=new Mode1();
        temp5.setName("配送中");
        temp5.setInt_data(delivering);
        Mode1 temp6=new Mode1();
        temp6.setName("已签收");
        temp6.setInt_data(accept);
        Mode1 temp7=new Mode1();
        temp7.setName("已取消");
        temp7.setInt_data(cancel);
        statistics=new ArrayList<>();
        statistics.add(temp1);
        statistics.add(temp2);
        statistics.add(temp3);
        statistics.add(temp4);
        statistics.add(temp5);
        statistics.add(temp6);
        statistics.add(temp7);
    }

    public void statistics2(){
        int fifty=0;
        int hundred=0;
        int hundred2=0;
        int hundred3=0;
        int hundred4=0;
        int hundred5=0;
        int hundredMore=0;
        int cancel=0;
        for(int i=0;i<infos.size();i++){
            if(infos.get(i).getState()==OrderState.Cancel){
                cancel++;
                continue;
            }
            double money=infos.get(i).getDishSum()-infos.get(i).getDiscountSum();
            if(money<=50){
                fifty++;
            }
            else if(money<=100){
                hundred++;
            }
            else if(money<=200){
                hundred2++;
            }
            else if(money<=300){
                hundred3++;
            }
            else if(money<=400){
                hundred4++;
            }
            else if(money<=500){
                hundred5++;
            }
            else{
                hundredMore++;
            }
        }
        Mode1 temp1=new Mode1();
        temp1.setName("≤50");
        temp1.setInt_data(fifty);
        Mode1 temp2=new Mode1();
        temp2.setName("≤100");
        temp2.setInt_data(hundred);
        Mode1 temp3=new Mode1();
        temp3.setName("≤200");
        temp3.setInt_data(hundred2);
        Mode1 temp4=new Mode1();
        temp4.setName("≤300");
        temp4.setInt_data(hundred3);
        Mode1 temp5=new Mode1();
        temp5.setName("≤400");
        temp5.setInt_data(hundred4);
        Mode1 temp6=new Mode1();
        temp6.setName("≤500");
        temp6.setInt_data(hundred5);
        Mode1 temp7=new Mode1();
        temp7.setName("＞500");
        temp7.setInt_data(hundredMore);
        Mode1 temp8=new Mode1();
        temp8.setName("取消");
        temp8.setInt_data(cancel);
        statistics=new ArrayList<>();
        statistics.add(temp1);
        statistics.add(temp2);
        statistics.add(temp3);
        statistics.add(temp4);
        statistics.add(temp5);
        statistics.add(temp6);
        statistics.add(temp7);
    }
}
