package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo  extends JpaRepository<Admin,String> {

    @Query(value = "select * from user where email = :newemail" , nativeQuery = true)
    Admin findfirstbyadminemail(String newemail);
}
