package com.wx.yummy.controller;

import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.order.*;
import com.wx.yummy.bean.statistics.Mode4;
import com.wx.yummy.bean.user.Address;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.service.OrderService;
import com.wx.yummy.utility.enums.DiscountType;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.text.normalizer.NormalizerBase;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //根据用户的默认地址获得用户周边的餐厅
    @RequestMapping(value = "/get/near/canteens",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<CanteenOrder> getNearCanteens(@RequestBody Customer customer){
        return orderService.getNearCanteens(customer.getEmail());
    }

    //根据用户和餐厅的地址信息，计算二者之间的距离
    @RequestMapping(value = "/calculate/distance",method = RequestMethod.POST)
    public @ResponseBody
    double calculateDistance(@RequestBody Customer customer, @RequestBody Canteen canteen){
        Address customerAddress=customer.getAddresses().get(0);
        Address canteenAddress=new Address(canteen.getProvince(),canteen.getCity(),canteen.getArea(),canteen.getAddress());
        return orderService.calculateDistance(customerAddress,canteenAddress);
    }

    //用户完成选菜操作，系统将其加入到待支付订单中
    @RequestMapping(value = "/finish/order", method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<String> userFinishOrder(@RequestBody OrderInfo info){
        return orderService.confirmMenu(info);
    }

    //根据用户ID返回用户所有订单信息，并按照时间顺序倒叙排列
    @RequestMapping(value = "/customer/get/orders",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<OrderInfo> getCustomerOrders(@RequestBody Customer customer){
        return orderService.getCustomerOrders(customer.getEmail());
    }

    //根据用户ID返回用户所有订单信息，并按照金额顺序倒序排列
    @RequestMapping(value = "/customer/get/orders/cash",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<OrderInfo> getCustomerOrdersByCash(@RequestBody Customer customer){
        return orderService.getCustomerOrdersByCash(customer.getEmail());
    }

    //根据用户ID返回用户所有订单信息，并按照餐厅进行分类
    @RequestMapping(value = "/customer/get/orders/canteen",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<ArrayList<OrderInfo>> getCustomerOrdersByCanteen(@RequestBody Customer customer){
        return orderService.getCustomerOrdersByCanteen(customer.getEmail());
    }

    //用户完成支付操作
    @RequestMapping(value = "/customer/pay",method = RequestMethod.POST)
    public @ResponseBody
    PayMessage dealCustomerPay(@RequestBody PayOrder payOrder){
        return orderService.confirmPay(payOrder.getInfo().getOrderID(),payOrder.getBalance().getAccount(),payOrder.getBalance().getPassword(),payOrder.getInfo().getDishSum()+payOrder.getInfo().getDeliverySum()-payOrder.getInfo().getDiscountSum());
    }

    //用户在某个时段选择取消时，计算返回的价钱
    @RequestMapping(value = "/customer/return/money",method = RequestMethod.POST)
    public @ResponseBody
    double calculateReturnMoney(@RequestBody OrderInfo info){
        return orderService.calculateBackMoney(info);
    }

    //用户选择取消订单，根据不同的阶段进行不同的取消操作
    @RequestMapping(value = "/customer/cancel",method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage cancelOrder(@RequestBody OrderInfo info){
        return orderService.cancelOrder(info.getOrderID());
    }

    //餐厅根据餐厅id返回当天的订单信息，分好类，并按照时间顺序倒叙排列
    @RequestMapping(value = "/canteen/get/orders",method = RequestMethod.POST)
    public @ResponseBody
    OrdersPerType getCanteenOrders(@RequestBody Canteen canteen){
        return orderService.getCanteenOrders(canteen.getId());
    }

    @RequestMapping(value = "/canteen/change/order/state", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage changeOrderStateByCanteen(@RequestBody OrderInfo info){
        return orderService.changeOrderState(info.getOrderID(),info.getState());
    }

    @RequestMapping(value = "/discount/type",method = RequestMethod.POST)
    public @ResponseBody
    DiscountType searchDiscountType(@RequestBody Customer customer){
        return orderService.getDiscountType(customer);
    }

    //data中第一个是餐厅id,第二个是开始时间，第三个是结束时间
    @RequestMapping(value = "/canteen/get/time/orders",method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<Mode4> getCanteenOrdersByTime(@RequestBody ArrayList<String> data){
        String date1=data.get(1).substring(0,4)+"/"+data.get(1).substring(5,7)+"/"+data.get(1).substring(8,10);
        String date2=data.get(2).substring(0,4)+"/"+data.get(2).substring(5,7)+"/"+data.get(2).substring(8,10);
        return orderService.getCanteenOrdersByTime(data.get(0),new Date(date1),new Date(date2));
    }

    //data中第一个是餐厅id,第二个是开始时间，第三个是结束时间
    @RequestMapping(value = "/canteen/get/cash/orders",method = RequestMethod.POST)
    public @ResponseBody
    Mode4 getCanteenOrdersByCash(@RequestBody ArrayList<String> data){
        String date1=data.get(1).substring(0,4)+"/"+data.get(1).substring(5,7)+"/"+data.get(1).substring(8,10);
        String date2=data.get(2).substring(0,4)+"/"+data.get(2).substring(5,7)+"/"+data.get(2).substring(8,10);
        return orderService.getCanteenOrdersByCash(data.get(0),new Date(date1),new Date(date2));
    }

    //data中第一个是餐厅id,第二个是开始时间，第三个是结束时间
    @RequestMapping(value = "/canteen/get/type/orders",method = RequestMethod.POST)
    public @ResponseBody
    OrdersPerType getCanteenOrdersByType(@RequestBody ArrayList<String> data){
        String date1=data.get(1).substring(0,4)+"/"+data.get(1).substring(5,7)+"/"+data.get(1).substring(8,10);
        String date2=data.get(2).substring(0,4)+"/"+data.get(2).substring(5,7)+"/"+data.get(2).substring(8,10);
        return orderService.getCanteenOrdersByType(data.get(0),new Date(date1),new Date(date2));
    }

}
