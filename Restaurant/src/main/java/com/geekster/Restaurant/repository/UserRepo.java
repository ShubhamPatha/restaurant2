package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,String> {
    @Query(value = "select * from user where email = :newemail" , nativeQuery = true)
    User findfirstbyuseremail(String newemail);
}
