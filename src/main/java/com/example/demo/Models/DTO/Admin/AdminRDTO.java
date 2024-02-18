package com.example.demo.Models.DTO.Admin;

import jakarta.validation.constraints.NotBlank;
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
public class AdminRDTO {
    private long id;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    // @NotBlank(message = "role is required")
    // private String Role;
}
