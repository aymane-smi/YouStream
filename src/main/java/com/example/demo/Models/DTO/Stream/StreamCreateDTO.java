package com.example.demo.Models.DTO.Stream;

import java.util.List;

import com.example.demo.Models.DTO.Tag.TagDTO;

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
public class StreamCreateDTO {
    private List<TagDTO> tags;
}
