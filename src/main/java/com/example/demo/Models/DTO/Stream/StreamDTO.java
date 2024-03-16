package com.example.demo.Models.DTO.Stream;


import java.util.List;

import com.example.demo.Models.DTO.Student.StudentDTO;
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
public class StreamDTO {
    private long id;
    private int viewsNbr;
    private boolean restricted;
    private String file_name;
    private List<TagDTO> tags;
    private StudentDTO student;
    private StudentDTO owner;
}
