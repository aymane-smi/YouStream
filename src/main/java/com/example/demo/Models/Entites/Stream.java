package com.example.demo.Models.Entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="streams")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Stream {
    private long id;
    private int viewsNbr;
    private boolean restricted;
    private String file_name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student owner;
}
