package com.wx.yummy.controller;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.order.Balance;
import com.wx.yummy.bean.order.PayOrder;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.bean.user.CheckCode;
import com.wx.yummy.bean.user.VIP;
import com.wx.yummy.service.UserService;
import com.wx.yummy.utility.enums.LoginMessage;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.RegisterMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/customer/get/checkCode",method = RequestMethod.POST)
    public @ResponseBody ResultMessage getCheckCode(@RequestBody Customer customer){
        RegisterMessage message= userService.customerFirstRegister(customer.getEmail());
        return ResultMessage.SUCCESS;
    }

    @RequestMapping(value = "/customer/check/checkCode",method = RequestMethod.POST)
    public @ResponseBody RegisterMessage checkCheckCode(@RequestBody CheckCode checkCode){
        RegisterMessage message= userService.customerConfirmCheck(checkCode.getEmail(),checkCode.getCheckCode());
        return message;
    }

    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    public @ResponseBody RegisterMessage customerRegister(@RequestBody Customer customer){
        RegisterMessage message= userService.customerReviseRegister(customer);
        return message;
    }

    @RequestMapping(value = "/canteen/register", method = RequestMethod.POST)
    public @ResponseBody RegisterMessage canteenRegister(@RequestBody CanteenCheck canteen){
        RegisterMessage message= userService.canteenRegister(canteen);
        return message;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody LoginMessage login(@RequestBody Customer customer){
        LoginMessage message= userService.login(customer.getEmail(),customer.getPassword());
        return message;
    }

    @RequestMapping(value = "/get/customer",method = RequestMethod.POST)
    public @ResponseBody Customer getCustomer(@RequestBody Customer customer){
        return userService.getCustomer(customer.getEmail());
    }

    @RequestMapping(value = "/get/canteen",method = RequestMethod.POST)
    public @ResponseBody CanteenUser getCanteen(@RequestBody Canteen canteen){
        return userService.getCanteen(canteen.getId());
    }

    @RequestMapping(value = "/update/customer",method = RequestMethod.POST)
    public @ResponseBody ResultMessage updateCustomer(@RequestBody Customer customer){
        return userService.updateCustomer(customer);
    }

    @RequestMapping(value = "/update/canteen",method = RequestMethod.POST)
    public @ResponseBody ResultMessage updateCanteen(@RequestBody CanteenUser canteenUser){
        return userService.updateCanteen(canteenUser);
    }

    @RequestMapping(value = "/pay/vip/account",method = RequestMethod.POST)
    public @ResponseBody PayMessage payVIPByAccount(@RequestBody PayOrder payOrder){
        Customer customer=userService.getCustomer(payOrder.getInfo().getCustomerID());
        Balance balance=payOrder.getBalance();
        return userService.PayVIPByAccount(customer,balance.getPassword());
    }

    @RequestMapping(value = "/pay/vip/credits",method = RequestMethod.POST)
    public @ResponseBody PayMessage payVIPByCredits(@RequestBody Customer customer){
        return userService.PayVIPByCredits(customer);
    }

    @RequestMapping(value = "/get/vip",method = RequestMethod.POST)
    public @ResponseBody VIP checkVIPState(@RequestBody Customer customer){
        return userService.getVIP(customer.getEmail());
    }

    @RequestMapping(value = "/modify/canteen", method = RequestMethod.POST)
    public @ResponseBody ResultMessage modifyCanteen(@RequestBody CanteenCheck canteen){
        return userService.modifyCanteen(canteen);
    }

    @RequestMapping(value = "/cancel/customer/register", method = RequestMethod.POST)
    public @ResponseBody ResultMessage cancelCustomerRegister(@RequestBody Customer customer){
        return userService.cancelUser(customer.getEmail());
    }
}
