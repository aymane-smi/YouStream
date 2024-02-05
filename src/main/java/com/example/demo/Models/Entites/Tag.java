package com.example.demo.Models.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="tags")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tag {
    private long id;
    private String tag_name;
    @OneToMany(fetch = FetchType.LAZY)
    private Tag tag;
}
