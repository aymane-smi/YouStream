package com.example.demo.Models.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment {
    private long id;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
