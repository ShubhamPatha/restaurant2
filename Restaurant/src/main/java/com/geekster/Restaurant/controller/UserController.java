package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.dto.SignInInput;
import com.geekster.Restaurant.model.dto.SignUpOutput;
import com.geekster.Restaurant.service.AuthenticationService;
import com.geekster.Restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    UserService userService;





    @Autowired
    AuthenticationService authenticationService;




    @PostMapping("user/signup")
    public SignUpOutput signUpPatient(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInPatient(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInPatient(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutPatient(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutPatient(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }

    @GetMapping("users")
    List<User> getAlLUser()
    {
        return userService.getAllUser();
    }


}
