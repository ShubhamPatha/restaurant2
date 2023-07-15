package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.AuthenticationToken;
import com.geekster.Restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}