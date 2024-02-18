package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    @Query("SELECT a FROM Admin a WHERE a.username = :username")
    public Optional<Admin> findByUsername(@Param("username") String username);
    @Query("select a.id FROM Admin a WHERE a.username = :username")
    public Optional<Long> findIdByUsername(@Param("username") String username);
}
