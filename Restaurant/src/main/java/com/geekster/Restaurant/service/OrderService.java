package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.model.Order;
import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.enums.Sta;
import com.geekster.Restaurant.repository.OrderRepo;
import com.geekster.Restaurant.repository.UserRepo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;



    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }


    @OneToMany(mappedBy = "ITEM")
    List<FoodItem> foodItems;
    public String PlaceAnOrder(Order order, User user) {
      User Usforid =userRepo.findfirstbyuseremail(user.getEmail());
      if(Usforid.getUsername()==null){
          return "invalid user";
      }

      orderRepo.save(order);
              return "Order placed";
    }
}
