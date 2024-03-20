package com.example.demo.Services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Helper.Security;
import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;
import com.example.demo.Models.Entites.Admin;
import com.example.demo.Models.Entites.AdminRefreshToken;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.AdminRefreshRepository;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final AdminDetailsImpl adminDetailsImpl;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final AdminRefreshRepository adminRefreshRepository;

    public AdminServiceImpl(
        PasswordEncoder passwordEncoder,
        AdminRepository adminRepository,
        AdminDetailsImpl adminDetailsImpl,
        JwtService jwtService,
        ModelMapper modelMapper,
        AdminRefreshRepository adminRefreshRepository
    ){
        this.passwordEncoder = passwordEncoder;
        this.adminDetailsImpl = adminDetailsImpl;
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.adminRefreshRepository = adminRefreshRepository;
    }
    @Override
    public SignedAdminDTO login(AdminLoginDTO student) {
        var user = adminDetailsImpl.loadUserByUsername(student.getUsername());
        UUID uuid = UUID.randomUUID();
        var newRefresh = new AdminRefreshToken(
            uuid,
            adminRepository.findByUsername(student.getUsername()).get()
        );
        var refresh = adminRefreshRepository.save(newRefresh);
        if(passwordEncoder.matches(student.getPassword(), user.getPassword())){
            String token = jwtService.generateToken(user);
            return SignedAdminDTO.builder().id(
                adminRepository.findIdByUsername(student.getUsername()).get()
            )
            .username(student.getUsername())
            .token(token)
            .refresh_token(refresh.getId().toString())
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void logout(UUID refresh_token) {
        adminRefreshRepository.deleteById(refresh_token);
    }
    @Override
    public SignedAdminDTO createRefreshToken(UUID refresh_token) {
        var refresh = adminRefreshRepository.findById(refresh_token).get();
        var admin = adminDetailsImpl.loadUserByUsername(refresh.getAdmin().getUsername());
        UUID uuid = UUID.randomUUID();
        var newRefresh = new AdminRefreshToken(
            uuid,
            adminRepository.findByUsername(admin.getUsername()).get()
        );
        String token = jwtService.generateToken(admin);
        return SignedAdminDTO.builder().id(
                adminRepository.findIdByUsername(admin.getUsername()).get()
            )
            .username(admin.getUsername())
            .token(token)
            .refresh_token(newRefresh.getId().toString())
            .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public String editUsername(String username) {
        var user = adminRepository.findByUsername(Security.retriveUsername()).get();
        user.setUsername(username);
        adminRepository.save(user);
        return username;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public boolean editPassword(String password) {
        try{
            var user = adminRepository.findByUsername(Security.retriveUsername()).get();
            user.setPassword(
                passwordEncoder.encode(password)
            );
            adminRepository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
