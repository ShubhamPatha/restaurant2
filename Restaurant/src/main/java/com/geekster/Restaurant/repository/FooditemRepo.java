package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooditemRepo extends JpaRepository<FoodItem,String> {
}
