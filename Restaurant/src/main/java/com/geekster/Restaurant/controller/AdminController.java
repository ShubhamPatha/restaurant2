package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping("Addadmin")
    void addamin(@RequestBody Admin admin){
        adminService.addadmin(admin);

    }
}
