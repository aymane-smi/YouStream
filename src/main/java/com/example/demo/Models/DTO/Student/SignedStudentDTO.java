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
public class SignedStudentDTO {
    private long id;
    private String username;
    private String token;
    private String refresh_token;
}
