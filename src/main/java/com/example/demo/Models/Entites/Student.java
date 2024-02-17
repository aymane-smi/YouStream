package com.example.demo.Models.Entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student extends User{
    private long id;
    private boolean isAlive;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "claimer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Student> claimers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Stream> streams;
}
