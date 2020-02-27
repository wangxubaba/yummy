package com.wx.yummy.controller;

import com.wx.yummy.bean.dish.Menu;

import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.service.CanteenService;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/canteen")
public class CanteenController {
    @Autowired
    CanteenService canteenService;

    //某个餐厅最后一次修改的餐厅菜单
    @RequestMapping(value = "/dish/latest",method = RequestMethod.POST)
    public @ResponseBody
    Menu getLatestMenu(@RequestBody Canteen canteen){
        return canteenService.getLatestMenu(canteen.getId());
    }

    //某个餐厅今天执行的餐厅菜单
    @RequestMapping(value = "/dish/today",method = RequestMethod.POST)
    public @ResponseBody
    Menu getTodayMenu(@RequestBody Canteen canteen){
        return canteenService.getTodayMenu(canteen.getId());
    }

    //设置下一个阶段餐厅需要执行的餐厅菜单
    @RequestMapping(value = "/set/menu",method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage setNextMenu(@RequestBody Menu menu){
        return canteenService.setNextMenu(menu);
    }
}
