package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Entites.Stream;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long>{
    public List<Stream> findFirst10ByOrderByViewsNbrDesc();
    public Page<Stream> findByRestrictedIs(boolean restricted, Pageable peagable);
}
