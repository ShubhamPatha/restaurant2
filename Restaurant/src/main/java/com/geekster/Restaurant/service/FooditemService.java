package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.FoodItem;

import com.geekster.Restaurant.repository.AdminRepo;
import com.geekster.Restaurant.repository.FooditemRepo;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FooditemService {

    @Autowired
    AdminRepo adminRepo;
    @Autowired
    FooditemRepo fooditemRepo;

    public List<FoodItem> getAllFoodItem() {
        return fooditemRepo.findAll();
    }
    public String additem(FoodItem foodItem) {

        String  username=foodItem.getAdmin().getUsername();

        boolean isadminValid = adminRepo.existsById(username);

        //id of patient

        String foodtitle = foodItem.getTitle();
        boolean isfooditemValid = fooditemRepo.existsById(foodtitle);

        if(isadminValid && isfooditemValid)
        {
            fooditemRepo.save(foodItem);
            return "Added";
        }
        else {
            return "Failed";
        }




    }
}
