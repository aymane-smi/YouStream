package com.example.demo.Models.DTO.Tag;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private int id;
    @NotBlank(message = "tag name can't be null")
    private String tag_name;
}
