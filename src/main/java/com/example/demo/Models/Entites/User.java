package com.example.demo.Models.Entites;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class User {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String password;
}
