package com.example.demo.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Entites.AdminRefreshToken;

public interface AdminRefreshRepository  extends JpaRepository<AdminRefreshToken, UUID>{
    
}
