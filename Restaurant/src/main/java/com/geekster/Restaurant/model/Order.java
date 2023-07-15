package com.geekster.Restaurant.model;

import com.geekster.Restaurant.model.enums.Sta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {

    @Id
    private Integer orderID;
    private Integer FoodItemID;
    private Integer quantity;
    private Sta Status;
    @OneToOne
    @JoinColumn(name="Userid")
    User user;

    @OneToMany
    @JoinColumn(name = "Fooditem")
   List< FoodItem > foodItems;
}
