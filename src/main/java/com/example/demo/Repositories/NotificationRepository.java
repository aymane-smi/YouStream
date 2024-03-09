package com.example.demo.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Models.Entites.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>{
    
}
