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
