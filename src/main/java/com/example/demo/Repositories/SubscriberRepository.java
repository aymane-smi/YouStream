package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Entites.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    @Query("SELECT sub.subscriber FROM Subscriber sub WHERE sub.streamer.id = :streamerId")
    public List<Student> findSubscribers(@Param("streamerId") long streamerId);
}
