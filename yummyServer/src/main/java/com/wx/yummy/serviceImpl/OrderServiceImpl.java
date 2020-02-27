package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;
import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.order.CanteenOrder;
import com.wx.yummy.bean.order.OrderInfo;
import com.wx.yummy.bean.order.OrdersPerType;
import com.wx.yummy.bean.statistics.Mode4;
import com.wx.yummy.bean.user.Address;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.bean.user.VIP;
import com.wx.yummy.dao.*;
import com.wx.yummy.service.OrderService;
import com.wx.yummy.utility.enums.DiscountType;
import com.wx.yummy.utility.enums.OrderState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CanteenDao canteenDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BalanceDao balanceDao;
    @Override
    public ArrayList<CanteenOrder> getNearCanteens(String username) {
        //先拿到客户信息
        Customer customer=userDao.getCustomer(username);

        //根据地址去搜索附近的餐厅
        Address default_address=customer.getAddresses().get(0);
        ArrayList<CanteenOrder> canteens=canteenDao.getNearCanteens(default_address.getProvince(),default_address.getCity(),default_address.getArea());

        //把每个餐厅的menu和距离加进去
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYYMMdd");
        int day=Integer.parseInt(simpleDateFormat.format(date));
        for(int i=0;i<canteens.size();i++){
            String canteenID=canteens.get(i).getId();
            Menu menu=new Menu();
            menu.setCanteenID(canteenID);
            menu.setTypes(menuDao.getDishTypes(canteenID,day));
            menu.setDishDiscount(menuDao.getDiscounts(canteenID,day));
            canteens.get(i).setMenu(menu);
        }

        return canteens;
    }

    @Override
    public double calculateDistance(Address customerAddress, Address canteenAddress) {
        return Math.random()*10;
    }

    @Override
    public ArrayList<String> confirmMenu(OrderInfo info) {
        //1.先判断菜够不够卖
        Canteen canteen=new Canteen();
        canteen.setId(info.getCanteenID());
        canteen=userDao.getCanteen(canteen.getId());
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
        String dateStr=format.format(date);
        int day=Integer.parseInt(dateStr);

        ArrayList<String> notEnough=new ArrayList<>();
        ArrayList<Dish> dishes=info.getOrderedDishes().getDishes();
        ArrayList<Discount> discounts=info.getOrderedDishes().getDiscounts();
        for(int i=0;i<dishes.size();i++){
            Dish originDish=menuDao.getDishByID(canteen.getId(),(int)dishes.get(i).getId());
            int alreadySold=orderDao.getOrdersContainDishesNum(canteen.getId(),date,(int)dishes.get(i).getId());
            if(originDish.getAmount()<alreadySold+dishes.get(i).getAmount()){
                String remind=originDish.getType()+" "+originDish.getName()+"仅剩"+(originDish.getAmount()-alreadySold)+"份";
                notEnough.add(remind);
            }
        }
        for(int i=0;i<discounts.size();i++){
            Discount originDiscount=menuDao.getDiscountByID(canteen.getId(),discounts.get(i).getId());
            int alreadySold=orderDao.getOrdersContainDiscountsNum(canteen.getId(),date,discounts.get(i).getId());
            if(originDiscount.getAmount()<discounts.get(i).getAmount()+alreadySold){
                String remind=originDiscount.getName()+"仅剩"+(originDiscount.getAmount()-alreadySold)+"份";
                notEnough.add(remind);
            }
        }

        //2.如果都够卖，那么就把菜品加入到order表中
        if(notEnough.size()==0){
            //先计算可不可以给优惠
            Customer customer=new Customer();
            customer=userDao.getCustomer(info.getCustomerID());
            DiscountType type=getDiscountType(customer);
            if(type==DiscountType.VIPDiscount){
                double dis=0;
                if(info.getDishSum()>=100){
                    dis=10;
                }
                else if(info.getDishSum()>=80){
                    dis=8;
                }
                else if(info.getDishSum()>=60){
                    dis=6;
                }
                else if(info.getDishSum()>=40){
                    dis=4;
                }
                else if(info.getDishSum()>=20){
                    dis=2;
                }
                else {
                    dis=0;
                }
                info.setDiscountSum(dis);
            }
            else{
                info.setDiscountSum(0);
            }
            //然后加到表中
            info.setValidTimeBegin(new Date());
            info.setCancelState(OrderState.Cancel);
            orderDao.insertOrder(info);
        }


        return notEnough;
    }

    @Override
    public PayMessage confirmPay(int orderID, String accountID, String accountPassword, double cash) {
        //1.先得到order订单
        OrderInfo order=orderDao.getOrder(orderID);
        //2.判断用户账户是否可用
        PayMessage message=balanceDao.pay(accountID,accountPassword,cash);
        //3.若支付成功则修改order信息及用户积分
        if(message==PayMessage.PaySuccess){
            order.setState(OrderState.WaitingAccept);
            order.setValidTimeBegin(new Date());
            orderDao.updateOrder(order);
        }
        return message;
    }

    @Override
    public ArrayList<OrderInfo> getCustomerOrders(String customerID) {
        //1.先根据用户账号获取用户的所有订单
        ArrayList<OrderInfo> orders=orderDao.getCustomerOrders(customerID);

        //2.将得到的订单按照日期顺序倒叙排列
        ArrayList<OrderInfo> result=sortOrders(orders);
        return result;
    }

    @Override
    public ArrayList<OrderInfo> getCustomerOrdersByCash(String customerID) {
        return orderDao.getCustomerOrdersByCash(customerID);
    }

    @Override
    public ArrayList<ArrayList<OrderInfo>> getCustomerOrdersByCanteen(String customerID) {
        return orderDao.getCustomerOrdersByCanteen(customerID);
    }

    @Override
    public ResultMessage cancelOrder(int orderID) {
        OrderInfo info=orderDao.getOrder(orderID);
        Customer customer=userDao.getCustomer(info.getCustomerID());
        double cash=calculateBackMoney(info);
        //未被自动取消
        if(info.getState()!=OrderState.Cancel){
            //修改order的信息
            info.setCancelState(info.getState());
            info.setState(OrderState.Cancel);
            orderDao.updateOrder(info);

            //计算并退款
            balanceDao.transfer("admin",customer.getPay(),cash);
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public double calculateBackMoney(OrderInfo info) {
        double result=getBackDishMoney(info.getState(),info.getDishSum())+getBackDeliveryMoney(info.getState(),info.getDeliverySum())-info.getDiscountSum();
        if(result<0){
            result=0;
        }
        return result;
    }

    @Override
    public OrdersPerType getCanteenOrders(String canteenID) {
        Date date=new Date();
        OrdersPerType ordersPerType=getSpecificDayCanteenOrders(canteenID,date);
        return ordersPerType;
    }

    @Override
    public ArrayList<Mode4> getCanteenOrdersByTime(String canteenID, Date begin, Date end) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(begin);
        ArrayList<Mode4> result=new ArrayList<>();
        while(calendar.getTime().getTime()<=end.getTime()){
            Mode4 temp=new Mode4();
            temp.setInfos(orderDao.getCanteenOrdersByTime(canteenID,calendar.getTime()));
            temp.setName(simpleDateFormat.format(calendar.getTime()));
            temp.statistics1();
            result.add(temp);
            calendar.add(Calendar.DATE,1);
        }
        return result;
    }

    @Override
    public Mode4 getCanteenOrdersByCash(String canteenID, Date begin, Date end) {
        ArrayList<OrderInfo> orders=orderDao.getCanteenOrdersByCash(canteenID,begin,end);
        Mode4 mode4=new Mode4();
        mode4.setInfos(orders);
        mode4.statistics2();
        return mode4;
    }

    @Override
    public OrdersPerType getCanteenOrdersByType(String canteenID, Date begin, Date end) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(begin);
        OrdersPerType result=new OrdersPerType();
        while(calendar.getTime().getTime()<=end.getTime()){
            OrdersPerType temp=getSpecificDayCanteenOrders(canteenID,calendar.getTime());
            result.getNewOrder().addAll(temp.getNewOrder());
            result.getSolving().addAll(temp.getSolving());
            result.getWaitingDelivery().addAll(temp.getWaitingDelivery());
            result.getDelivering().addAll(temp.getDelivering());
            result.getSign().addAll(temp.getSign());
            result.getCancel().addAll(temp.getCancel());
            calendar.add(Calendar.DATE,1);
        }
        return result;
    }

    @Override
    public ResultMessage changeOrderState(int orderID, OrderState state) {
        OrderInfo info=orderDao.getOrder(orderID);
        if(OrderState.WaitingAccept==state){
            info.setState(OrderState.Making);
        }
        else if(OrderState.Making==state){
            info.setState(OrderState.WaitingDelivery);
        }
        else if(OrderState.WaitingDelivery==state){
            info.setState(OrderState.Delivering);
        }
        else if(OrderState.Delivering==state){
            info.setState(OrderState.Arrived);
            //同时设置用户积分
            Customer customer=userDao.getCustomer(info.getCustomerID());
            int addCredits= (int) (info.getDishSum()/10);
            customer.setCredits(customer.getCredits()+addCredits);
            userDao.updateCustomer(customer);
        }
        orderDao.updateOrder(info);
        return ResultMessage.SUCCESS;
    }

    @Override
    public DiscountType getDiscountType(Customer customer) {
        Customer detailCustomer=userDao.getCustomer(customer.getEmail());
        VIP vip=userDao.getVIP((int)detailCustomer.getId());
        if(vip==null){
            return DiscountType.NotVIP;
        }
        else{
            ArrayList<OrderInfo> infos=orderDao.getCustomerOrdersByDate(detailCustomer.getEmail(),new Date());
            for(int i=0;i<infos.size();i++){
                if(infos.get(i).getState()!=OrderState.Cancel){
                    if(infos.get(i).getDiscountSum()>0){
                        return DiscountType.NotFirstDiscountToday;
                    }
                }
            }
            return DiscountType.VIPDiscount;
        }
    }

    private OrdersPerType getSpecificDayCanteenOrders(String canteenID, Date date){
        OrdersPerType ordersPerType=new OrdersPerType();
        ordersPerType.setNewOrder(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.WaitingAccept,date)));
        ordersPerType.setSolving(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.Making,date)));
        ordersPerType.setWaitingDelivery(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.WaitingDelivery,date)));
        ordersPerType.setDelivering(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.Delivering,date)));
        ordersPerType.setSign(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.Arrived,date)));
        ordersPerType.setCancel(sortOrders(orderDao.getCanteenOrdersByStatePerDay(canteenID,OrderState.Cancel,date)));
        return ordersPerType;
    }

    private double getBackDishMoney(OrderState state,double dishSum){
        if(state==OrderState.WaitingPay){
            return 0;
        }
        else if(state==OrderState.WaitingAccept){
            return dishSum;
        }
        else if(state==OrderState.Making){
            return dishSum*0.5;
        }
        else if(state==OrderState.WaitingDelivery){
            return dishSum*0.3;
        }
        else if(state==OrderState.Delivering){
            return dishSum*0.1;
        }
        else{
            return 0;
        }
    }

    private double getBackDeliveryMoney(OrderState state, double deliverySum){
        if(state==OrderState.WaitingPay){
            return 0;
        }
        else if(state==OrderState.WaitingAccept){
            return deliverySum;
        }
        else if(state==OrderState.Making){
            return deliverySum;
        }
        else if(state==OrderState.WaitingDelivery){
            return deliverySum*0.8;
        }
        else if(state==OrderState.Delivering){
            return deliverySum*0.2;
        }
        else{
            return 0;
        }
    }

    private ArrayList<OrderInfo> sortOrders(ArrayList<OrderInfo> orders){
        for(int i=0;i<orders.size();i++){
            for(int j=0;j<orders.size()-1;j++){
                if(orders.get(j).getValidTimeBegin().getTime()<orders.get(j+1).getValidTimeBegin().getTime()){
                    OrderInfo temp=orders.get(j);
                    orders.set(j,orders.get(j+1));
                    orders.set(j+1,temp);
                }
            }
        }
        return orders;
    }

}
