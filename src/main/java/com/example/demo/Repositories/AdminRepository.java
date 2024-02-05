package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    
}
