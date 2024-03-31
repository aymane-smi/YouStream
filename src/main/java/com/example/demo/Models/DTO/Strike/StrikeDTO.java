package com.example.demo.Models.DTO.Strike;

import com.example.demo.Models.DTO.Student.StudentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class StrikeDTO {
    private long id;
    private StudentDTO claimer;
    private StudentDTO streamer;
    private String description;
}
