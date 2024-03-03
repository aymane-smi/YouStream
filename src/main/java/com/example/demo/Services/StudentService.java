package com.example.demo.Services;

import java.util.UUID;

import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;

public interface StudentService {
    public SignedStudentDTO login(StudentLoginDTO student);
    public void logout(UUID refresh_token);
    public SignedStudentDTO createRefreshToken(UUID refresh_token);
    public StudentDTO signup(StudentRDTO student);
}
