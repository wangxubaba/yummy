package com.wx.yummy.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String getFormatDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static Date getRelatedDate(String date){
        return new Date(date);
    }
}
