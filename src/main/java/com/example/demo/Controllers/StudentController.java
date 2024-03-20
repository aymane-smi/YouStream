package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Student.StudentEditPwdDTO;
import com.example.demo.Models.DTO.Student.StudentEditUserDTO;
import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Services.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping("/editUsername")
    public ResponseEntity<Map<String, String>> editUsername(@Valid @RequestBody StudentEditUserDTO username) {
        Map<String, String> map = new HashMap<>();
        map.put("message", studentService.editUsername(username.getUsername()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PostMapping("/editPassword")
    public ResponseEntity<Map<String, Boolean>> editPassword(@Valid @RequestBody StudentEditPwdDTO password) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("changed", studentService.editPassword(password.getPassword()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
    
}
