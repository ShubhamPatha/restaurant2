package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.service.FooditemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodItemController {

    @Autowired
    FooditemService fooditemService;
    @PostMapping("Additem")
    String additem1(@RequestBody FoodItem foodItem){
       return fooditemService.additem(foodItem);

    }
}
