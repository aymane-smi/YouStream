package com.example.demo.Services;

import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;

public interface StudentService {
    public SignedStudentDTO login(StudentLoginDTO student);
    //public logout();
    public StudentDTO signup(StudentRDTO student);
}
