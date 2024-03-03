package com.example.demo.Models.DTO.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentDTO {
    private long id;
    private String username;
    private String password;
    private String Role;
    private boolean isActive;
}
