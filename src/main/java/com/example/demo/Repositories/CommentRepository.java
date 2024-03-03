package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
