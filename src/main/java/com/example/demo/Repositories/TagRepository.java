package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    
}
