package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Stream;
import com.example.demo.Models.Entites.Tag;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    @Query("SELECT t.stream FROM tags t WHERE t.tag_name = :name")
    List<Stream> getStreamsByTag(@Param("name") String name);
}
