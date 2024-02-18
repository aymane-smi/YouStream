package com.example.demo.Models.DTO.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.Models.Enums.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentRDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    //private String Role;
    @NotNull(message = "active status is required")
    private boolean isActive;
}
