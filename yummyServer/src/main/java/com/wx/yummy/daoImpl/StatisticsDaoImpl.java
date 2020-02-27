package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.statistics.Mode1;
import com.wx.yummy.bean.statistics.Mode2;
import com.wx.yummy.bean.statistics.Mode3;
import com.wx.yummy.dao.StatisticsDao;
import com.wx.yummy.dao.UserDao;
import com.wx.yummy.utility.DateFormat;
import com.wx.yummy.utility.enums.CanteenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class StatisticsDaoImpl implements StatisticsDao {

    @Autowired
    JdbcTemplate template;
    @Autowired
    UserDao userDao;

    @Override
    public String[][] getCanteensByMostOrdered(String customerID, Date date) {
        String day=DateFormat.getFormatDate(date).substring(0,8);
        String sql="select o.canteenID, c.cname, count(oid) as num from orders o, canteen c where o.canteenID=c.cid and o.customerID=? and o.timeBegin like '"+day+"%' group by canteenID order by num desc";
        String[] names=new String[10];
        String[] times=new String[10];
        List list =template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("cname"));
                temp.setInt_data(resultSet.getInt("num"));
                return temp;
            }
        }, customerID);
        for(int i=0;((i<list.size())&&(i<10));i++){
            Mode1 temp= (Mode1) list.get(i);
            names[i]=temp.getName();
            times[i]=temp.getInt_data()+"";
        }
        String[][] result=new String[2][10];
        result[0]=names;
        result[1]=times;
        return result;
    }

    @Override
    public String[][] getDiscountSumPerMonth(String customerID) {
        //1.先获得所有的有优惠的订单,按照时间倒叙排列
        String sql="select * from orders where customerID=? and discountSum>0 and state<>'Cancel' order by timeBegin desc";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                Date date=DateFormat.getRelatedDate(resultSet.getString("timeBegin"));
                SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
                String day=format.format(date);
                temp.setName(day.substring(0,4)+day.substring(4,6));
                temp.setDouble_data(resultSet.getDouble("discountSum"));
                return temp;
            }
        }, customerID);

        //2.将订单按照时间来组合
        ArrayList<Mode1> finalList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Mode1 mode1= (Mode1) list.get(i);
            if(finalList.size()==0){
                Mode1 temp=new Mode1();
                temp.setName(mode1.getName());
                temp.setDouble_data(mode1.getDouble_data());
                finalList.add(temp);
                continue;
            }

            int lastIndex=finalList.size()-1;
            if(mode1.getName().equals(finalList.get(lastIndex).getName())){
                finalList.get(lastIndex).setDouble_data(finalList.get(lastIndex).getDouble_data()+mode1.getDouble_data());
                continue;
            }
            else{
                Mode1 temp=new Mode1();
                temp.setName(mode1.getName());
                temp.setDouble_data(mode1.getDouble_data());
                finalList.add(temp);
                continue;
            }
        }

        //3.返回结果值
        String[][] result=changeDoubleMode1ToString(finalList);
        return result;
    }

    @Override
    public String[][] getCanteenTypeOrderedNum(String customerID) {
        String sql="select count(o.oid) as num, c.ctype from orders o, canteen c where o.canteenID=c.cid and o.state<>'Cancel'and o.customerID=? group by c.ctype";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("ctype"));
                temp.setInt_data(resultSet.getInt("num"));
                return temp;
            }
        },customerID);
        ArrayList<Mode1> array=new ArrayList<>(list);
        array=searchSpecificType(array,"汉餐");
        array=searchSpecificType(array,"回餐");
        array=searchSpecificType(array,"快餐");
        array=searchSpecificType(array,"西餐");
        array=searchSpecificType(array,"其他");
        String[][] result=changeIntMode1ToString(array);
        return result;
    }

    private ArrayList<Mode1> searchSpecificType(ArrayList<Mode1> array, String type){
        boolean conclude=false;
        for(int i=0;i<array.size();i++){
            if(array.get(i).getName().equals(type)){
                conclude=true;
                break;
            }
        }
        if(!conclude){
            Mode1 temp=new Mode1();
            temp.setName(type);
            temp.setInt_data(0);
            array.add(temp);
        }
        return array;
    }

    @Override
    public String[][] getCanteenTypeCashSum(String customerID) {
        String sql="select c.ctype, sum(dishSum) as DishSum, sum(deliverySum) as DeliverySum, sum(discountSum) as DiscountSum from orders o, canteen c where o.canteenID=c.cid and o.state<>'Cancel' and customerID=? group by c.ctype";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("ctype"));
                temp.setDouble_data(resultSet.getDouble("DishSum")+resultSet.getDouble("DeliverSum")-resultSet.getDouble("DiscountSum"));
                return temp;
            }
        },customerID);
        String[][] result=changeDoubleMode1ToString(new ArrayList<>(list));
        return result;
    }

    @Override
    public String[][] getMostPopularDishesThisTime(String canteenID) {
        String sql1="select distinct dbegin from dish"+canteenID+" where dbegin>=all(select dbegin from dish"+canteenID+")";
        String dbegin=template.queryForObject(sql1,String.class);
        String sql2="select distinct dend from dish"+canteenID+" where dbegin>=all(select dbegin from dish"+canteenID+")";
        String dend=template.queryForObject(sql1,String.class);
        String dateStr=dbegin.substring(0,4)+"/"+dbegin.substring(4,6)+"/"+dbegin.substring(6,8)+" 00:00:00";
        Date beginTime=DateFormat.getRelatedDate(dateStr);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-1);
        Date endTime=calendar.getTime();
        int begin=Integer.parseInt(dbegin);
        int end=Integer.parseInt(dend);

        return getMostPopularDishes(canteenID,beginTime,endTime,begin,end);
    }

    @Override
    public String[][] getMostPopularDishesLastTime(String canteenID) {
        String sql1="select distinct dbegin from dish"+canteenID+" where dbegin>=all(select dbegin from dish"+canteenID+")";
        int thisBegin=template.queryForObject(sql1,Integer.class);
        String sql2="select distinct dbegin from dish"+canteenID+" where dbegin>=all(select dbegin from dish"+canteenID+" where dbegin<?)";
        String lastBegin=template.queryForObject(sql2,String.class);
        String sql3="select distinct dend from dish"+canteenID+" where dbegin>=all(select dbegin from dish"+canteenID+" where dbegin<?)";
        String lastEnd=template.queryForObject(sql2,String.class);
        String dateStr1=lastBegin.substring(0,4)+"/"+lastBegin.substring(4,6)+"/"+lastBegin.substring(6,8)+" 00:00:00";
        String dateStr2=lastEnd.substring(0,4)+"/"+lastEnd.substring(4,6)+"/"+lastEnd.substring(6,8)+" 00:00:00";
        Date beginTime=DateFormat.getRelatedDate(dateStr1);
        Date tempEndTime=DateFormat.getRelatedDate(dateStr2);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(tempEndTime);
        calendar.add(Calendar.DATE,1);
        Date endTime=calendar.getTime();

        return getMostPopularDishes(canteenID,beginTime,endTime,Integer.parseInt(lastBegin),Integer.parseInt(lastEnd));
    }

    @Override
    public String[][] getCanteenOrdersNumPerDay(String canteenID) {
        //1.查找该点是从那天开始出售菜品的
        String sql1="select dbegin from dish"+canteenID+" where dbegin<=all(select dbegin from dish"+canteenID+")";
        String firstBegin=template.queryForObject(sql1,String.class);

        //2.查找自第一天以后每天的订单数量
        Calendar calendar=Calendar.getInstance();
        String dateStr=firstBegin.substring(0,4)+"/"+firstBegin.substring(4,6)+"/"+firstBegin.substring(6,8)+" 00:00:00";
        Date beginDay=DateFormat.getRelatedDate(dateStr);
        calendar.setTime(beginDay);
        Date today=new Date();
        ArrayList<Mode1> orders=new ArrayList<>();
        while(calendar.getTime().getTime()<today.getTime()){
            Date temp=calendar.getTime();
            String timeBegin=DateFormat.getFormatDate(temp).substring(0,10);
            String sql2="select count(oid) as num from orders where timeBegin like '"+timeBegin+"%' and canteenID=? and state<>'Cancel'";
            int num=template.queryForObject(sql2,Integer.class);
            Mode1 tempMode1=new Mode1();
            tempMode1.setName(timeBegin);
            tempMode1.setInt_data(num);
            orders.add(tempMode1);
            calendar.add(Calendar.DATE,1);
        }

        //3.返回结果
        String[][] result=changeIntMode1ToString(orders);
        return result;
    }

    @Override
    public String[][] getCanteenCashNumPerDay(String canteenID) {
        //1.查找该点是从那天开始出售菜品的
        String sql1="select dbegin from dish"+canteenID+" where dbegin<=all(select dbegin from dish"+canteenID+")";
        String firstBegin=template.queryForObject(sql1,String.class);

        //2.查找自第一天以后每天的订单价格
        Calendar calendar=Calendar.getInstance();
        String dateStr=firstBegin.substring(0,4)+"/"+firstBegin.substring(4,6)+"/"+firstBegin.substring(6,8)+" 00:00:00";
        Date beginDay=DateFormat.getRelatedDate(dateStr);
        calendar.setTime(beginDay);
        Date today=new Date();
        ArrayList<Mode1> orders=new ArrayList<>();
        while(calendar.getTime().getTime()<today.getTime()){
            Date temp=calendar.getTime();
            String timeBegin=DateFormat.getFormatDate(temp).substring(0,10);
            String sql2="select sum(dishSum) as DishSum, sum(deliverySum) as deliverySum, sum(discountSum) as DiscountSum from orders where timeBegin like '"+timeBegin+"%' and canteenID=? and state<>'Cancel'";
            List list=template.query(sql2, new RowMapper<Double>() {
                @Override
                public Double mapRow(ResultSet resultSet, int i) throws SQLException {
                    double dishSum=resultSet.getDouble("DishSum");
                    double deliverySum=resultSet.getDouble("deliverySum");
                    double discountSum=resultSet.getDouble("discountSum");
                    return dishSum-discountSum;
                }
            });
            Mode1 tempMode1=new Mode1();
            tempMode1.setName(timeBegin);
            tempMode1.setDouble_data((Double) list.get(0));
            orders.add(tempMode1);
            calendar.add(Calendar.DATE,1);
        }

        //3.返回结果
        String[][] result=changeIntMode1ToString(orders);
        return result;
    }

    @Override
    public ArrayList<Mode2> getCancelOrdersNumPerMonthByType(String canteenID) {
        //1.查找该点是从那天开始出售菜品的
        String sql1="select dbegin from dish"+canteenID+" where dbegin<=all(select dbegin from dish"+canteenID+")";
        String firstBegin=template.queryForObject(sql1,String.class);

        //2.查找每个月取消的订单
        Calendar calendar=Calendar.getInstance();
        String dateStr=firstBegin.substring(0,4)+"/"+firstBegin.substring(4,6)+"/"+firstBegin.substring(6,8)+" 00:00:00";
        Date beginDate=DateFormat.getRelatedDate(dateStr);
        Date today=new Date();
        ArrayList<Mode2> resultArray=new ArrayList<>();
        while(calendar.getTime().getTime()<today.getTime()){
            String month=DateFormat.getFormatDate(calendar.getTime()).substring(0,8);
            Mode2 mode2=new Mode2();
            mode2.setName(month);
            String sql2="select count(oid) as num, cancel from orders where timeBegin like '"+month+"%' and state='Cancel' group by cancel";
            List list=template.query(sql2, new RowMapper<Mode1>() {
                @Override
                public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                    Mode1 temp=new Mode1();
                    temp.setName(resultSet.getString("cancel"));
                    temp.setInt_data(resultSet.getInt("num"));
                    return temp;
                }
            });
            mode2.setArray(new ArrayList<>(list));
            String sql3="select count(oid) as num from orders where timeBegin like '"+month+"%' and state='Cancel'";
            int num=template.queryForObject(sql3,Integer.class);
            mode2.setInt_data(num);
            resultArray.add(mode2);
        }
        return resultArray;
    }

    @Override
    public ArrayList<Mode2> getNewRegisterCanteensPerMonth() {
        //1.找到所有新注册的餐厅信息
        Date today=new Date();
        String sql1="select applyDate from canteen order by applyDate";
        ArrayList<Mode1> array=solveRegisterStatistics(sql1);

        ArrayList<Mode2> result=new ArrayList<>();
        for(int i=0;i<array.size();i++){
            Mode2 temp=new Mode2();
            temp.setInt_data(array.get(i).getInt_data());
            temp.setName(array.get(i).getName());
            String day=array.get(i).getName().substring(0,4)+"/"+array.get(i).getName().substring(5,7);
            String sql2="select count(cid) as num, ctype from canteen where applyDate like '"+day+"%' group by ctype order by ctype";
            List list=template.query(sql2, new RowMapper<Mode1>() {
                @Override
                public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                    Mode1 mode1=new Mode1();
                    mode1.setName(resultSet.getString("ctype"));
                    mode1.setInt_data(resultSet.getInt("num"));
                    return mode1;
                }
            });
            temp.setArray(new ArrayList<>(list));
            result.add(temp);
        }

        return result;
    }

    @Override
    public String[][] getCanteensByType() {
        String sql="select count(cid) as num, ctype from canteen group by ctype";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("ctype"));
                temp.setInt_data(resultSet.getInt("num"));
                return temp;
            }
        });
        return changeIntMode1ToString(new ArrayList<>(list));
    }

    @Override
    public String[][] getNewRegisterCustomersPerMonth() {
        String sql="select applyDate from user order by applyDate";
        ArrayList<Mode1> array=solveRegisterStatistics(sql);
        String[][] result=changeIntMode1ToString(array);
        return result;
    }

    @Override
    public String[][] getCustomersNumGroupByProvince() {
        String sql="select count(u.uid) as num, a.province from user u, address a where u.valid=? and u.uid=a.uid and a.order=1 group by province order by num;";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("province"));
                temp.setInt_data(resultSet.getInt("num"));
                return temp;
            }
        },true);
        String[][] result=changeIntMode1ToString(new ArrayList<>(list));
        return result;
    }

    @Override
    public String[][] getMonthlyCash() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-1);
        String sql="select dishSum, discountSum, timeBegin from orders order by timeBegin";
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                String time=resultSet.getString("timeBegin");
                Mode1 temp=new Mode1();
                temp.setName(time.substring(0,4)+"-"+time.substring(5,7));
                temp.setDouble_data(resultSet.getDouble("dishSum")-resultSet.getDouble("discountSum"));
                return temp;
            }
        });
        ArrayList<Mode1> array=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Mode1 temp= (Mode1) list.get(i);
            if(i==0){
                array.add(temp);
                continue;
            }

            int lastIndex=array.size()-1;
            if(array.get(lastIndex).getName().equals(temp.getName())){
                array.get(lastIndex).setDouble_data(array.get(lastIndex).getDouble_data()+temp.getDouble_data());
                continue;
            }
            else{
                array.add(temp);
            }
        }

        String[][] result=changeDoubleMode1ToString(array);
        return result;
    }

    @Override
    public String[][] getCashEarnedGroupByCareer(String date) {
        //1.找到该月使用了会员资格的用户花销
        String sql1="select sum(dishSum-discountSum) as num from orders where timeBegin like '"+date+"%' and customerID in (select distinct customerID from orders where timeBegin like '"+date+"%' and discountSum>0)";
        double vipSum=0;
        try {
            vipSum=template.queryForObject(sql1,Double.class);
        }catch (NullPointerException e){
            vipSum=0;
        }

        //2.找到该月普通的用户花销
        String sql2="select sum(dishSum) as num from orders where timeBegin like '"+date+"%' and customerID not in (select distinct customerID from orders where timeBegin like '"+date+"%' and discountSum>0)";
        double commonSum=0;
        try {
            commonSum=template.queryForObject(sql2,Double.class);
        }catch (NullPointerException e){
            commonSum=0;
        }

        //3.找到该月递送的花销
        String sql3="select sum(deliverySum) as num from orders where timeBegin like '"+date+"%'";
        double deliverySum=0;
        try {
            deliverySum=template.queryForObject(sql3,Double.class);
        }catch (NullPointerException e){
            deliverySum=0;
        }

        String[][] result={{"VIP用户开支","普通用户开支","递送开支"},{vipSum+"",commonSum+"",deliverySum+""}};
        return result;
    }


    //begin end是数据表中的
    public String[][] getMostPopularDishes(String canteenID, Date beginTime, Date endTime, int begin, int end) {
        //1.先找到上一次修改菜单后该餐厅提供的菜品的id
        String sql1="select distinct did, dname from dish"+canteenID+" where dbegin>=? and dend<=end";
        List list1=template.query(sql1, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                Mode1 temp=new Mode1();
                temp.setName(resultSet.getString("dname"));
                temp.setInt_data(resultSet.getInt("did"));
                return temp;
            }
        },begin);

        //2.然后搜索订单，并计算从开始卖到今天，平均每天卖掉多少个
        ArrayList<Mode1> dishSellSum=new ArrayList<>();
        for(int i=0;i<list1.size();i++){
            Mode1 dish= (Mode1) list1.get(i);
            int id=dish.getInt_data();

            String sql2="select dishes from orders where canteenID=? and dishes like '%,"+id+"%' or dishes like '%"+id+",%' and state<>'Cancel'";
            Mode1 dishSell=template.queryForObject(sql2, new RowMapper<Mode1>() {
                @Override
                public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                    Mode1 temp=new Mode1();
                    temp.setName(dish.getName());
                    temp.setInt_data(countNumOfArray(resultSet.getString("dishes").split(","),id+""));
                    return temp;
                }
            }, canteenID);
            dishSellSum.add(dishSell);
        }

        //3.计算总共卖了多少天
        int days= (int) ((endTime.getTime()-beginTime.getTime())/(1000*60*60*24));

        //4.按照菜品卖出的平均数量进行排序
        for(int i=0;i<dishSellSum.size();i++){
            for(int j=0;j<dishSellSum.size()-1;j++){
                if(dishSellSum.get(j).getInt_data()<dishSellSum.get(j+1).getInt_data()){
                    Mode1 temp=dishSellSum.get(j);
                    dishSellSum.set(j,dishSellSum.get(j+1));
                    dishSellSum.set(j+1,temp);
                }
            }
        }

        //5.将model中的数据都除以days
        ArrayList<Mode1> dishes=new ArrayList<>();
        for(int i=0;i<dishSellSum.size();i++){
            Mode1 mode1=new Mode1();
            mode1.setName(dishSellSum.get(i).getName());
            mode1.setDouble_data(dishSellSum.get(i).getInt_data()*1.0/days);
            dishes.add(mode1);
        }

        String[][] result=changeDoubleMode1ToString(dishes);
        return result;
    }

    private String[][] changeDoubleMode1ToString(ArrayList<Mode1> list){
        String[][] result=new String[2][list.size()];
        for(int i=0;i<list.size();i++){
            result[0][i]=list.get(i).getName();
            result[1][i]=list.get(i).getDouble_data()+"";
        }
        return result;
    }

    private String[][] changeIntMode1ToString(ArrayList<Mode1> list){
        String[][] result=new String[2][list.size()];
        for(int i=0;i<list.size();i++){
            result[0][i]=list.get(i).getName();
            result[1][i]=list.get(i).getInt_data()+"";
        }
        return result;
    }

    private int countNumOfArray(String[] array, String target){
        int result=0;
        for(int i=0;i<array.length;i++){
            if(array[i].equals(target)){
                result++;
            }
        }
        return result;
    }

    private ArrayList<Mode1> solveRegisterStatistics(String sql){
        //1.获得信息列表
        List list=template.query(sql, new RowMapper<Mode1>() {
            @Override
            public Mode1 mapRow(ResultSet resultSet, int i) throws SQLException {
                String date=resultSet.getString("applyDate");
                Mode1 temp=new Mode1();
                temp.setName(date.substring(0,4)+"-"+date.substring(5,7));
                temp.setInt_data(1);
                return temp;
            }
        });

        //2.将同一个月的信息进行分类(不包括本月信息)
        ArrayList<Mode1> array=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Mode1 temp= (Mode1) list.get(i);
            if(i==0){
                array.add(temp);
                continue;
            }

            int lastIndex=array.size()-1;
            if(array.get(lastIndex).getName().equals(temp.getName())){
                array.get(lastIndex).setInt_data(array.get(lastIndex).getInt_data()+1);
                continue;
            }
            else{
                array.add(temp);
            }
        }
        return array;
    }
}
