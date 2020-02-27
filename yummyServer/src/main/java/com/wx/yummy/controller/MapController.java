package com.wx.yummy.controller;

import com.wx.yummy.bean.user.Address;
import com.wx.yummy.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/map")
public class MapController {
    @Autowired
    private MapService mapService;
    @RequestMapping(value = "/get/recommend",method = RequestMethod.POST)
    public @ResponseBody ArrayList<Address> getRecommendAddresses(@RequestBody Address address){
        return mapService.getRecommendPlaces(address.getDetail(),address.getProvince(),address.getCity(),address.getArea());
    }

    @RequestMapping(value = "/calculate/distance", method = RequestMethod.POST)
    public @ResponseBody double calculateDistance(@RequestBody ArrayList<Address> addresses){
        return mapService.calculateDistance(addresses.get(0),addresses.get(1));
    }
}
