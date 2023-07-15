package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.AuthenticationToken;
import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.dto.SignInInput;
import com.geekster.Restaurant.model.dto.SignUpOutput;
import com.geekster.Restaurant.repository.FooditemRepo;
import com.geekster.Restaurant.repository.IAuthTokenRepo;
import com.geekster.Restaurant.repository.OrderRepo;
import com.geekster.Restaurant.repository.UserRepo;
import com.geekster.Restaurant.service.utility.EmailHandler;
import com.geekster.Restaurant.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;

@Service

public class UserService {


    @Autowired
    FooditemRepo fooditemRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    IAuthTokenRepo authTokenRepo;

    @Autowired
    UserRepo userRepo;



    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this patient email already exists ??
        User existingPatient = userRepo.findfirstbyuseremail(newEmail);

        if(existingPatient != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());

            //saveAppointment the patient with the new encrypted password

            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }


    public String signInPatient(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this patient email already exists ??
     User existingPatient = userRepo.findfirstbyuseremail(signInEmail);

        if(existingPatient == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingPatient.getPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingPatient);
                authTokenRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }






    public String sigOutPatient(String email) {

        User user = userRepo.findfirstbyuseremail(email);
        authTokenRepo.delete(authTokenRepo.findFirstByUser(user));
        return "Patient Signed out successfully";
    }

}
