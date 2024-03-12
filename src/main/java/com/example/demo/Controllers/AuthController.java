package com.example.demo.Controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;
import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;
import com.example.demo.Services.AdminService;
import com.example.demo.Services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final StudentService studentService;
    private final AdminService adminService;

    public AuthController(
        StudentService studentService,
        AdminService adminService
        ){
        this.studentService = studentService;
        this.adminService = adminService;
    }

    //student
    @PostMapping("/student/signup")
    public ResponseEntity<String> signupStudent(@Valid @RequestBody StudentRDTO student){
        StringBuilder sb = new StringBuilder();
        if(studentService.signup(student) != null)
            sb.append("student created");
        return new ResponseEntity<>(sb.toString(), HttpStatus.CREATED);
    }
    @PostMapping("/student/signin")
    public ResponseEntity<SignedStudentDTO> login(@Valid @RequestBody StudentLoginDTO student){
        return new ResponseEntity<>(studentService.login(student), HttpStatus.CREATED);
    }
    @GetMapping("/student/refresh/token/{refresh_token}")
    public ResponseEntity<SignedStudentDTO> refreshStudentToken(@PathVariable UUID refresh_token){
        return new ResponseEntity<>(studentService.createRefreshToken(refresh_token), HttpStatus.CREATED);
    }
    @GetMapping("/student/logout/token/{refresh_token}")
    public ResponseEntity<String> logoutStudent(@PathVariable UUID refresh_token){
        studentService.logout(refresh_token);
        return new ResponseEntity<>("logout", HttpStatus.OK);
    }

    //admin
    @PostMapping("/admin/signup")
    public ResponseEntity<String> signupAdmin(@Valid @RequestBody AdminRDTO admin){
        StringBuilder sb = new StringBuilder();
        if(adminService.signup(admin) != null)
            sb.append("admin created");
        return new ResponseEntity<>(sb.toString(), HttpStatus.CREATED);
    }
    @PostMapping("/admin/signin")
    public ResponseEntity<SignedAdminDTO> login(@Valid @RequestBody AdminLoginDTO admin){
        return new ResponseEntity<>(adminService.login(admin), HttpStatus.CREATED);
    }
    @GetMapping("/admin/refresh/token/{refresh_token}")
    public ResponseEntity<SignedAdminDTO> refreshAdminsToken(@PathVariable UUID refresh_token){
        return new ResponseEntity<>(adminService.createRefreshToken(refresh_token), HttpStatus.CREATED);
    }
    @GetMapping("/admin/logout/token/{refresh_token}")
    public ResponseEntity<String> logoutAdmin(@PathVariable UUID refresh_token){
        adminService.logout(refresh_token);
        return new ResponseEntity<>("logout", HttpStatus.OK);
    }
}
