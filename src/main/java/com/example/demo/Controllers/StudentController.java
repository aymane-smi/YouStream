package com.example.demo.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Services.StudentService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins =  "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    public StudentController(
        StudentService studentService
    ){
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentListDTO>> getUsers(){
        return new ResponseEntity<>(studentService.getListStudent(), HttpStatus.OK);
    }
}
