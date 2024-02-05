package com.example.demo.Models.Entites;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="admins")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Admin extends User{
    private long id;
}
