# restaurant2




## FRAMEWORK AND LANGUAGE USED
* JAVA 17
* MAVEN
* SPRINGBOOT 3.1.1
* MySql
<!-- Headings -->   
## DATA FLOW

<!-- Code Blocks -->

### CONTROLLER
#### UserController
   ```
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

```
#### OrderController
   ```
package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.model.Order;
import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.dto.SignInInput;
import com.geekster.Restaurant.model.dto.SignUpOutput;
import com.geekster.Restaurant.service.AuthenticationService;
import com.geekster.Restaurant.service.OrderService;
import com.geekster.Restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
   OrderService orderService;

    @Autowired
    AuthenticationService authenticationService;

    @PutMapping("user/signIn/order")
    public String PlaceAnOrder(@RequestBody @Valid Order  foodItem, User user)
    {
        return orderService.PlaceAnOrder(foodItem,user);
    }


}

```
#### FoodItemController
   ```
package com.geekster.Restaurant.controller;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.service.FooditemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodItemController {

    @Autowired
    FooditemService fooditemService;
    @PostMapping("Additem")
    String additem1(@RequestBody FoodItem foodItem){
       return fooditemService.additem(foodItem);

    }
}

```
#### AdminController
   
   ```
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

```


 ### MODEL
  #### SignInInput
  ``` 
package com.geekster.Restaurant.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInInput {

    @Pattern(regexp = "^.+@(?![Hh][Oo][Ss][Pp][Aa][Dd][Mm][Ii][Nn]\\.[Cc][Oo][Mm]$).+$")
    private String email;
    private String password;
}

```
  #### SignUpOutput
  ``` 
package com.geekster.Restaurant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpOutput {

    private boolean signUpStatus;
    private String signUpStatusMessage;


}

```
#### enum sta
  ```
package com.geekster.Restaurant.model.enums;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum Sta {
    created, dispatch , delivered
}


```
#### User
  ```
package com.geekster.Restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    private String  username;
    private String password;
    @Pattern(regexp = "^.+@(?![Hh][Oo][Ss][Pp][Aa][Dd][Mm][Ii][Nn]\\.[Cc][Oo][Mm]$).+$")
    @Column(unique = true)
    private String email;
}


```
#### Order
  ```
package com.geekster.Restaurant.model;

import com.geekster.Restaurant.model.enums.Sta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {

    @Id
    private Integer orderID;
    private Integer FoodItemID;
    private Integer quantity;
    private Sta Status;
    @OneToOne
    @JoinColumn(name="Userid")
    User user;

    @OneToMany
    @JoinColumn(name = "Fooditem")
   List< FoodItem > foodItems;
}


```
#### FoodItem
  ```

package com.geekster.Restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class FoodItem {

   @Id
    private String title;
    private String description;
    private Integer price;
    @OneToOne
    Admin admin;

}

```

#### AuthenticationToken
  ```
package com.geekster.Restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String tokenValue;
    private LocalDateTime tokenCreationDateTime;

    //mapping
    @OneToOne
    @JoinColumn(name = "fk_User_id")
    User user;


    //create a parameterized constructor which takes patient as an argument
    public AuthenticationToken(User user)
    {
        this.user = user;
        this.tokenValue = UUID.randomUUID().toString();
        this.tokenCreationDateTime = LocalDateTime.now();
    }
}


```
#### Admin
  ```
package com.geekster.Restaurant.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Admin {

    @Id
    private String  username;
    private String password;
    @Pattern(regexp = "^.+@admin\\.com$")
    @Column(unique = true)
    private String email;
}

```


### REPO
#### AdminRepo
  ``` 
package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo  extends JpaRepository<Admin,String> {

    @Query(value = "select * from user where email = :newemail" , nativeQuery = true)
    Admin findfirstbyadminemail(String newemail);
}

```
#### FooditemRepo
  ``` 
package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooditemRepo extends JpaRepository<FoodItem,String> {
}

```
#### IAuthTokenRepo
  ``` 
package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.AuthenticationToken;
import com.geekster.Restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
```
#### OrderRepo
  ```
package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}


```
#### UserRepo
  ``` 

package com.geekster.Restaurant.repository;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,String> {
    @Query(value = "select * from user where email = :newemail" , nativeQuery = true)
    User findfirstbyuseremail(String newemail);
}

```

### Utility
#### PasswordEncrypter
  ``` 
package com.geekster.Restaurant.service.utility;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {

    public static String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

        return DatatypeConverter.printHexBinary(digested);


    }
}

```
#### EmailHandler
  ``` 

package com.geekster.Restaurant.service.utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHandler {

    private static final String EMAIL_USERNAME ="mainakgh1@gmail.com";
    private static final String EMAIL_PASSWORD ="iyyjxbrjqvchtdhv";


    public static void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

```

### SERVICE
#### UserService
  ``` 
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

```
#### OrderService
  ``` 
package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.FoodItem;
import com.geekster.Restaurant.model.Order;
import com.geekster.Restaurant.model.User;
import com.geekster.Restaurant.model.enums.Sta;
import com.geekster.Restaurant.repository.OrderRepo;
import com.geekster.Restaurant.repository.UserRepo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;



    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }


    @OneToMany(mappedBy = "ITEM")
    List<FoodItem> foodItems;
    public String PlaceAnOrder(Order order, User user) {
      User Usforid =userRepo.findfirstbyuseremail(user.getEmail());
      if(Usforid.getUsername()==null){
          return "invalid user";
      }

      orderRepo.save(order);
              return "Order placed";
    }
}

```

#### FooditemService
  ```
package com.geekster.Restaurant.service;

import com.geekster.Restaurant.model.Admin;
import com.geekster.Restaurant.model.FoodItem;

import com.geekster.Restaurant.repository.AdminRepo;
import com.geekster.Restaurant.repository.FooditemRepo;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FooditemService {

    @Autowired
    AdminRepo adminRepo;
    @Autowired
    FooditemRepo fooditemRepo;

    public List<FoodItem> getAllFoodItem() {
        return fooditemRepo.findAll();
    }
    public String additem(FoodItem foodItem) {

        String  username=foodItem.getAdmin().getUsername();

        boolean isadminValid = adminRepo.existsById(username);

        //id of patient

        String foodtitle = foodItem.getTitle();
        boolean isfooditemValid = fooditemRepo.existsById(foodtitle);

        if(isadminValid && isfooditemValid)
        {
            fooditemRepo.save(foodItem);
            return "Added";
        }
        else {
            return "Failed";
        }




    }
}


```
#### AuthenticationService
  ``` 
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

```

#### AdminService
  ``` 
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
```


### MAIN
  ``` 

package com.geekster.Restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

}


```



 ### Application Properties
  ```
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant
spring.datasource.username=root
spring.datasource.password=Shubham12@
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true


```
 ### POM
  ```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.geekster</groupId>
	<artifactId>Restaurant</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Restaurant</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.1.0</version>
		</dependency>
		<dependency>
			<groupId>com.sun.mail</groupId>
			<artifactId>javax.mail</artifactId>
			<version>1.6.2</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>

```
## DATA STRUCTURE USED
* LIST 
* STRING
* LOCAL DATE
* INTEGER
* USER

# PROJECT SUMMARY

## Restaurant management service API
CRUD operation in Spring boot on Restaurant management system

*
### Endpoint to be provided 
* SIgnup User
* Sign in User
* getUser
* Update order
* update Food item
* Add admin









<!-- Headings -->   
# Author
SHUBHAM PATHAK
 <!-- UL -->
* Twitter <!-- Links -->
[@ShubhamPathak]( https://twitter.com/Shubham15022000)
* Github  <!-- Links -->
[@ShubhamPathak]( https://github.com/ShubhamPatha)
<!-- Headings -->   
