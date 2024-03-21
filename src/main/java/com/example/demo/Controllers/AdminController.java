package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Admin.AdminEditPwdDTO;
import com.example.demo.Models.DTO.Admin.AdminEditUserDTO;
import com.example.demo.Services.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(
        AdminService adminService
    ){
        this.adminService = adminService;
    }

    @PostMapping("/editUsername")
    public ResponseEntity<Map<String, String>> editUsername(@Valid @RequestBody AdminEditUserDTO username) {
        Map<String, String> map = new HashMap<>();
        map.put("message", adminService.editUsername(username.getUsername()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PostMapping("/editPassword")
    public ResponseEntity<Map<String, Boolean>> editPassword(@Valid @RequestBody AdminEditPwdDTO password) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("changed", adminService.editPassword(password.getPassword()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}
