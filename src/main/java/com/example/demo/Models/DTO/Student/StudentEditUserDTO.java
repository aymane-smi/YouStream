package com.example.demo.Models.DTO.Student;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentEditUserDTO {
    @NotBlank(message = "username is required")
    private String username;
}
