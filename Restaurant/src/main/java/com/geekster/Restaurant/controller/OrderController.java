package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.model.Order;
import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.dto.SignInInput;
import com.geekster.Restaurant.model.dto.SignUpOutput;
import com.geekster.Restaurant.service.AuthenticationService;
import com.geekster.Restaurant.service.OrderService;
import com.geekster.Restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
   OrderService orderService;

    @Autowired
    AuthenticationService authenticationService;

    @PutMapping("user/signIn/order")
    public String PlaceAnOrder(@RequestBody @Valid Order  foodItem, User user)
    {
        return orderService.PlaceAnOrder(foodItem,user);
    }


}
