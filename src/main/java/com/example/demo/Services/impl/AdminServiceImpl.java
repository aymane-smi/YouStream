package com.example.demo.Services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;
import com.example.demo.Models.Entites.Admin;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final AdminDetailsImpl adminDetailsImpl;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(
        PasswordEncoder passwordEncoder,
        AdminRepository adminRepository,
        AdminDetailsImpl adminDetailsImpl,
        JwtService jwtService,
        ModelMapper modelMapper
    ){
        this.passwordEncoder = passwordEncoder;
        this.adminDetailsImpl = adminDetailsImpl;
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }
    @Override
    public SignedAdminDTO login(AdminLoginDTO student) {
        var user = adminDetailsImpl.loadUserByUsername(student.getUsername());
        if(passwordEncoder.matches(student.getPassword(), user.getPassword())){
            String token = jwtService.generateToken(user);
            return SignedAdminDTO.builder().id(
                adminRepository.findIdByUsername(student.getUsername()).get()
            )
            .username(student.getUsername())
            .token(token)
            .build();
        }
        throw new InsufficientAuthenticationException("Unauthorized");
    }

    @Override
    public AdminDTO signup(AdminRDTO admin) {
        admin.setPassword(
            passwordEncoder.encode(admin.getPassword())
        );
        
        var Entity = modelMapper.map(admin, Admin.class);
        Entity.setRole(Role.ADMIN);
        return modelMapper.map(
            adminRepository.save(Entity),
            AdminDTO.class
        );
    }
    
}
