package com.example.demo.Models.Entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="streams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
