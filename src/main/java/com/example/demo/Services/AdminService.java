package com.example.demo.Services;

import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;

public interface AdminService {
    public SignedAdminDTO login(AdminLoginDTO student);
    //public logout();
    public AdminDTO signup(AdminRDTO student);
}
