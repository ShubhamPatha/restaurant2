package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.repository.AdminRepo;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    AdminRepo adminRepo;
    public List<Admin> getAllAdmin() {
        return adminRepo.findAll();
    }

    public void addadmin(Admin admin) {
        adminRepo.save(admin);
    }

}