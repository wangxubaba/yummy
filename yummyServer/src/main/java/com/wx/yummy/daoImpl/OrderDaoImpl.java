package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;
import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.order.ManagerPay;
import com.wx.yummy.bean.order.OrderInfo;
import com.wx.yummy.bean.order.OrderedDishes;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.dao.BalanceDao;
import com.wx.yummy.dao.OrderDao;
import com.wx.yummy.dao.UserDao;
import com.wx.yummy.utility.DateFormat;
import com.wx.yummy.utility.ConstantValues;
import com.wx.yummy.utility.enums.OrderState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import static org.aspectj.weaver.Advice.ConstantValue;

@Service
public class OrderDaoImpl implements OrderDao {
    @Autowired
    JdbcTemplate template;

    @Autowired
    UserDao userDao;

    @Autowired
    BalanceDao balanceDao;

    @Override
    public int insertOrder(OrderInfo info) {
        String sql="insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,null,info.getCustomerID(),info.getCanteenID(),DateFormat.getFormatDate(info.getValidTimeBegin()),info.getState().name(),info.getDestination(),info.getDishSum(),info.getDeliverySum(),getDishIDStr(info.getOrderedDishes().getDishes()),getGroupIDStr(info.getOrderedDishes().getDiscounts()),info.getDeliveryID(),info.getCancelState().name(),info.getDiscountSum());

        String sql2="select oid from orders where customerID=? and canteenID=? and timeBegin=? and state=?";
        int result=template.queryForObject(sql2,Integer.class,info.getCustomerID(),info.getCanteenID(),DateFormat.getFormatDate(info.getValidTimeBegin()),info.getState().name());

        return result;
    }

    @Override
    public int updateOrder(OrderInfo info) {
        String sql="update orders set customerID=?, canteenID=?, timeBegin=?, state=?, destination=?, dishSum=?, deliverySum=?, dishes=?, groups=?, deliveryID=?, cancel=?, discountSum=? where oid=?";
        template.update(sql,info.getCustomerID(),info.getCanteenID(),DateFormat.getFormatDate(info.getValidTimeBegin()),info.getState().name(),info.getDestination(),info.getDishSum(),info.getDeliverySum(),getDishIDStr(info.getOrderedDishes().getDishes()),getGroupIDStr(info.getOrderedDishes().getDiscounts()),info.getDeliveryID(),info.getCancelState().name(),info.getDiscountSum(),info.getOrderID());
        return info.getOrderID();
    }

    @Override
    public OrderInfo getOrder(int orderID) {
        String sql="select * from orders where oid=?";
        OrderInfo info=template.queryForObject(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                OrderInfo temp=new OrderInfo();
                temp.setOrderID(resultSet.getInt("oid"));
                temp.setCustomerID(resultSet.getString("customerID"));
                temp.setCanteenID(resultSet.getInt("canteenID"));
                temp.setValidTimeBegin(DateFormat.getRelatedDate(resultSet.getString("timeBegin")));
                temp.setDestination(resultSet.getString("destination"));
                temp.setDishSum(resultSet.getDouble("dishSum"));
                temp.setDeliverySum(resultSet.getDouble("deliverySum"));
                temp.setDiscountSum(resultSet.getDouble("discountSum"));
                String cancel=resultSet.getString("cancel");
                if(cancel!=null){
                    temp.setCancelState(OrderState.valueOf(resultSet.getString("cancel")));
                }
                //处理状态
                OrderState state=OrderState.valueOf(resultSet.getString("state"));
                if(state==OrderState.WaitingPay){
                    Date constructTime=DateFormat.getRelatedDate(resultSet.getString("timeBegin"));
                    Date timeNow=new Date();
                    int interval= (int) (timeNow.getTime()-constructTime.getTime());
                    if(interval>ConstantValues.getBiggestPayTime()*60*1000){
                        temp.setState(OrderState.Cancel);
                        temp.setCancelState(OrderState.WaitingPay);
                    }
                    else{
                        temp.setState(state);
                    }
                }
                else{
                    temp.setState(state);
                }
                Canteen canteen=new Canteen();
                canteen.setId(temp.getCanteenID());
                OrderedDishes orderedDishes=new OrderedDishes();
                orderedDishes.setDishes(getDishesByStr(resultSet.getString("dishes"),canteen.getId()));
                orderedDishes.setDiscounts(getDiscountsByStr(resultSet.getString("groups"),canteen.getId()));
                temp.setOrderedDishes(orderedDishes);
                temp.setDeliveryID(resultSet.getInt("deliveryID"));
                temp.setCustomer(userDao.getCustomer(temp.getCustomerID()));
                return temp;
            }
        }, orderID);
        return info;
    }

    @Override
    public ArrayList<OrderInfo> getCustomerOrders(String customerID) {
        String sql="select * from orders where customerID=?";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, customerID);
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<OrderInfo> getCustomerOrdersByCash(String customerID) {
        String sql="select * from orders where customerID=? order by (dishSum+deliverySum-discountSum) desc";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, customerID);
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<ArrayList<OrderInfo>> getCustomerOrdersByCanteen(String customerID) {
        //1.获得所有订单信息
        String sql="select * from orders where customerID=? order by canteenID";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                OrderInfo temp=getOrder(resultSet.getInt("oid"));
                Canteen canteen=new Canteen();
                canteen.setId(temp.getCanteenID());
                temp.setCanteen(userDao.getCanteen(canteen.getId()));
                return temp;
            }
        }, customerID);
        ArrayList<OrderInfo> array=new ArrayList(list);

        //2.将信息按照餐厅分类
        ArrayList<ArrayList<OrderInfo>> result=new ArrayList<>();
        for(int i=0;i<array.size();i++){
            if(i==0){
                ArrayList<OrderInfo> temp=new ArrayList<>();
                temp.add(array.get(0));
                result.add(temp);
                continue;
            }
            int lastIndex=result.size()-1;
            if(result.get(lastIndex).get(0).getCanteenID()==array.get(i).getCanteenID()){
                result.get(lastIndex).add(array.get(i));
                continue;
            }
            else{
                ArrayList<OrderInfo> temp=new ArrayList<>();
                temp.add(array.get(i));
                result.add(temp);
                continue;
            }
        }

        return result;
    }

    @Override
    public ArrayList<OrderInfo> getCanteenOrdersByTime(String canteenID, Date date) {
        String time1=DateFormat.getFormatDate(date).substring(0,10)+" 00:00:00";
        String time2=DateFormat.getFormatDate(date).substring(0,10)+" 23:59:59";
        String sql="select * from orders where canteenID=? and timeBegin<=? and timeBegin>=? order by timeBegin";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, canteenID, time2, time1);

        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<OrderInfo> getCanteenOrdersByCash(String canteenID, Date begin, Date end) {
        String time1=DateFormat.getFormatDate(begin).substring(0,10)+" 00:00:00";
        String time2=DateFormat.getFormatDate(end).substring(0,10)+" 23:59:59";
        String sql="select * from orders where canteenID=? and timeBegin<=? and timeBegin>=? order by (dishSum-discountSum)";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, canteenID, time2, time1);

        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<OrderInfo> getCanteenOrdersByStatePerDay(String canteenID, OrderState state, Date date) {
        String formatDate=DateFormat.getFormatDate(date).substring(0,10);
        String sql="select * from orders where canteenID=? and state=? and timeBegin Like '"+formatDate+"%'";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, Integer.parseInt(canteenID), state.name());
        return new ArrayList<>(list);
    }

    @Override
    public int getOrdersContainDishesNum(String canteenID, Date date, int dishID) {
        String day=DateFormat.getFormatDate(date).substring(0,10);
        String sql="select dishes from orders where dishes like '%"+dishID+"%' and canteenID=? and timeBegin like '"+day+"%'";
        List list=template.query(sql, new RowMapper<String[]>() {
            @Override
            public String[] mapRow(ResultSet resultSet, int i) throws SQLException {
                String dishes=resultSet.getString("dishes");
                String[] result=dishes.split(",");
                return result;
            }
        }, Integer.parseInt(canteenID));

        int result=0;
        for(int i=0;i<list.size();i++){
            String[] temp= (String[]) list.get(i);
            for(int j=0;j<temp.length;j++){
                if(temp[j].equals(dishID+"")){
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public int getOrdersContainDiscountsNum(String canteenID, Date date, int discountID) {
        String day=DateFormat.getFormatDate(date).substring(0,10);
        String sql="select groups from orders where groups like '%"+discountID+"%' and canteenID=? and timeBegin like '"+day+"%'";
        List list=template.query(sql, new RowMapper<String[]>() {
            @Override
            public String[] mapRow(ResultSet resultSet, int i) throws SQLException {
                String dishes=resultSet.getString("groups");
                String[] result=dishes.split(",");
                return result;
            }
        }, Integer.parseInt(canteenID));

        int result=0;
        for(int i=0;i<list.size();i++){
            String[] temp= (String[]) list.get(i);
            for(int j=0;j<temp.length;j++){
                if(temp[j].equals(discountID+"")){
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<OrderInfo> getCustomerOrdersByDate(String customerID, Date date) {
        String day=DateFormat.getFormatDate(date).substring(0,10);
        String sql="select * from orders where customerID=? and timeBegin like '"+day+"%'";
        List list=template.query(sql, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        },customerID);
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<ManagerPay> getAlreadyPayedManagerOrder() {
        updatePayRecord();
        String sql="select * from pay_record where valid=? order by ordersDate";
        List list=template.query(sql, new RowMapper<ManagerPay>() {
            @Override
            public ManagerPay mapRow(ResultSet resultSet, int i) throws SQLException {
                return getPayRecord(resultSet.getInt("id"));
            }
        }, true);
        return new ArrayList<>(list);
    }

    @Override
    public ArrayList<ManagerPay> getNotPayedManagerOrder() {
        updatePayRecord();
        String sql="select * from pay_record where valid=? order by ordersDate";
        List list=template.query(sql, new RowMapper<ManagerPay>() {
            @Override
            public ManagerPay mapRow(ResultSet resultSet, int i) throws SQLException {
                return getPayRecord(resultSet.getInt("id"));
            }
        }, false);
        return new ArrayList<>(list);
    }

    @Override
    public PayMessage managerPay(Date date) {
        String prefix=DateFormat.getFormatDate(date).substring(0,10);
        String begin=prefix+" 00:00:00";
        String end=prefix+" 23:59:59";
        String sql1="select * from orders where timeBegin>=? and timeBegin<=?";
        List list1=template.query(sql1, new RowMapper<OrderInfo>() {
            @Override
            public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return getOrder(resultSet.getInt("oid"));
            }
        }, begin, end);
        ArrayList<OrderInfo> orders=new ArrayList<>(list1);
        double percent=1-ConstantValues.getYummy_earned();
        for(int i=0;i<orders.size();i++){
            CanteenUser canteen=userDao.getCanteen(orders.get(i).getCanteenID()+"");
            double canteen_money=(orders.get(i).getDishSum()-orders.get(i).getDiscountSum())*percent;
            balanceDao.transfer("admin",canteen.getAccount(),canteen_money);
        }

        String sql2="update pay_record set valid=?, payTime=? where ordersDate=?";
        template.update(sql2,true,DateFormat.getFormatDate(new Date()),begin);
        return PayMessage.PaySuccess;
    }

    private ResultMessage updatePayRecord(){
        //1.先找到表中最后一个信息
        String sql1="select id from pay_record where ordersDate>=all(select ordersDate from pay_record)";
        int id=0;
        Calendar calendar=Calendar.getInstance();
        try {
            id=template.queryForObject(sql1,Integer.class);
            ManagerPay managerPay=getPayRecord(id);
            String ordersDay=DateFormat.getFormatDate(managerPay.getOrdersDate()).substring(0,10)+" 23:59:59";
            calendar.setTime(DateFormat.getRelatedDate(ordersDay));
            calendar.add(Calendar.DATE,1);
        }catch (EmptyResultDataAccessException e){
            String sql="select timeBegin from orders where timeBegin<=all(select timeBegin from orders)";
            Date temp=template.queryForObject(sql, new RowMapper<Date>() {
                @Override
                public Date mapRow(ResultSet resultSet, int i) throws SQLException {
                    String time=resultSet.getString("timeBegin").substring(0,10)+" 23:59:59";
                    return DateFormat.getRelatedDate(time);
                }
            });
            calendar.setTime(temp);
        }

        String today=DateFormat.getFormatDate(new Date()).substring(0,10)+" 00:00:00";
        while(calendar.getTime().getTime()<DateFormat.getRelatedDate(today).getTime()){
            String prefix=DateFormat.getFormatDate(calendar.getTime()).substring(0,10);
            String begin=prefix+" 00:00:00";
            String end=prefix+" 23:59:59";
            String sql2="select sum(dishSum) as dishSum, sum(discountSum) as discountSum, sum(deliverySum) as deliverySum, sum((dishSum-discountSum+deliverySum)*?) as charge from orders where timeBegin>=? and timeBegin<=?";
            double percent=1-ConstantValues.getYummy_earned();
            ManagerPay temp=template.queryForObject(sql2, new RowMapper<ManagerPay>() {
                @Override
                public ManagerPay mapRow(ResultSet resultSet, int i) throws SQLException {
                    ManagerPay temp=new ManagerPay();
                    temp.setDishSum(resultSet.getDouble("dishSum"));
                    temp.setDiscountSum(resultSet.getDouble("discountSum"));
                    temp.setDeliverySum(resultSet.getDouble("deliverySum"));
                    temp.setChargeAmount(resultSet.getDouble("charge"));
                    return temp;
                }
            }, percent, begin, end);
            String sql3="insert into pay_record values(?,?,?,?,?,?,?,?)";
            template.update(sql3,null,null,begin,temp.getDishSum(),temp.getDiscountSum(),temp.getDeliverySum(), temp.getChargeAmount(),false);
            calendar.add(Calendar.DATE,1);
        }
        return ResultMessage.SUCCESS;
    }

    private ManagerPay getPayRecord(int id){
        String sql="select * from pay_record where id=?";
        ManagerPay managerPay=template.queryForObject(sql, new RowMapper<ManagerPay>() {
            @Override
            public ManagerPay mapRow(ResultSet resultSet, int i) throws SQLException {
                ManagerPay temp=new ManagerPay();
                temp.setId(resultSet.getInt("id"));
                temp.setOrdersDate(DateFormat.getRelatedDate(resultSet.getString("ordersDate")));
                temp.setDishSum(resultSet.getDouble("dishSum"));
                temp.setDiscountSum(resultSet.getDouble("discountSum"));
                temp.setDeliverySum(resultSet.getDouble("deliverySum"));
                temp.setChargeAmount(resultSet.getDouble("payAmount"));
                temp.setValid(resultSet.getBoolean("valid"));
                if(temp.isValid()){
                    temp.setPayDate(DateFormat.getRelatedDate(resultSet.getString("payTime")));
                }
                return temp;
            }
        },id);
        return managerPay;
    }

    private String getDishIDStr(ArrayList<Dish> dishes){
        String result="";
        for(int i=0;i<dishes.size();i++){
            for(int j=0;j<dishes.get(i).getAmount();j++){
                result=result+","+dishes.get(i).getId();
            }
        }
        if(result.length()>0){
            return result.substring(1);
        }
        return result;
    }

    private String getGroupIDStr(ArrayList<Discount> discounts){
        String result="";
        for(int i=0;i<discounts.size();i++){
            for(int j=0;j<discounts.get(i).getAmount();j++){
                result=result+","+discounts.get(i).getId();
            }
        }
        if(result.length()>0){
            return result.substring(1);
        }
        return result;
    }

    private ArrayList<Dish> getDishesByStr(String str,String canteenID){
        if(str.equals("")){
            return new ArrayList<>();
        }
        ArrayList<Dish> dishes=new ArrayList<>();
        String[] ids=str.split(",");
        for(int i=0;i<ids.length;i++){
            if(dishes.size()==0){
                dishes.add(getDishByID(Integer.parseInt(ids[i]),canteenID));
            }
            else{
                Dish temp=dishes.get(dishes.size()-1);
                if((temp.getId()+"").equals(ids[i])){
                    dishes.get(dishes.size()-1).setAmount(temp.getAmount()+1);
                }
                else{
                    dishes.add(getDishByID(Integer.parseInt(ids[i]),canteenID));
                }
            }
        }
        return dishes;
    }

    private ArrayList<Discount> getDiscountsByStr(String str, String canteenID){
        if(str.equals("")){
            return new ArrayList<>();
        }
        String[] ids=str.split(",");
        ArrayList<Discount> discounts=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            if(discounts.size()==0){
                discounts.add(getDiscountByID(Integer.parseInt(ids[i]),canteenID));
            }
            else{
                if((discounts.get(discounts.size()-1).getId()+"").equals(ids[i])){
                    discounts.get(discounts.size()-1).setAmount(discounts.get(discounts.size()-1).getAmount()+1);
                }
                else {
                    discounts.add(getDiscountByID(Integer.parseInt(ids[i]),canteenID));
                }
            }
        }
        return discounts;
    }

    public Dish getDishByID(int id, String canteenID){
        String sql="select * from dish"+canteenID+" where did=?";
        Dish dish=template.queryForObject(sql, new RowMapper<Dish>() {
            @Override
            public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
                Dish temp=new Dish();
                temp.setId(resultSet.getInt("did"));
                temp.setName(resultSet.getString("dname"));
                temp.setPrice(resultSet.getDouble("dprice"));
                temp.setAmount(1);
                temp.setType(resultSet.getString("dtype"));
                return temp;
            }
        }, id);
        return dish;
    }

    public Discount getDiscountByID(int id, String canteenID){
        String sql="select * from group"+canteenID+" where gid=?";
        Discount discount=template.queryForObject(sql, new RowMapper<Discount>() {
            @Override
            public Discount mapRow(ResultSet resultSet, int i) throws SQLException {
                Discount temp=new Discount();
                temp.setId(resultSet.getInt("gid"));
                temp.setAmount(1);
                temp.setName(resultSet.getString("gname"));
                temp.setDiscount_price(resultSet.getDouble("gprice"));
                temp.setDishes(getDishesByStr(resultSet.getString("gdishes"),canteenID));
                return temp;
            }
        }, id);
        return discount;
    }
}
