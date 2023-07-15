package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
