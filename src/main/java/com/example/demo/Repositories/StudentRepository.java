package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    @Query("SELECT s FROM Student s WHERE s.username = :username")
    public Optional<Student> findByUser(@Param("username") String username);
}
