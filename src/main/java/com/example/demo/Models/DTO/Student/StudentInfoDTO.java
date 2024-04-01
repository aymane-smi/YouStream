package com.example.demo.Models.DTO.Student;

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
@ToString
@Builder
public class StudentInfoDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
}
