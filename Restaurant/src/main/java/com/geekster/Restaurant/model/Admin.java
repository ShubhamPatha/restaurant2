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
