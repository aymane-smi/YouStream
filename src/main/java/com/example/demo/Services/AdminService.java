package com.example.demo.Services;

import java.util.UUID;

import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;

public interface AdminService {
    public SignedAdminDTO login(AdminLoginDTO student);
    public void logout(UUID refresh_token);
    public AdminDTO signup(AdminRDTO student);
    public SignedAdminDTO createRefreshToken(UUID refresh_token);
}
