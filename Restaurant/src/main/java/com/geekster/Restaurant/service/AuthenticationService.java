package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.AuthenticationToken;
import com.geekster.Restaurant.repository.IAuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import com.geekster.Restaurant.model.User;

import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    @Autowired
    IAuthTokenRepo authTokenRepo;

    public boolean authenticate(String email, String authTokenValue) {
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(authTokenValue);

        if (authToken == null) {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getEmail();
        return tokenConnectedEmail.equals(email);

    }
}
