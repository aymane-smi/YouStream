package com.example.demo.Services;

import java.util.List;
import java.util.UUID;

import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentDTO;
import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;

public interface StudentService {
    public SignedStudentDTO login(StudentLoginDTO student);
    public void logout(UUID refresh_token);
    public SignedStudentDTO createRefreshToken(UUID refresh_token);
    public StudentDTO signup(StudentRDTO student);
    public List<StudentListDTO> getListStudent();
    public String editUsername(String username);
    public boolean editPassword(String password);
}
