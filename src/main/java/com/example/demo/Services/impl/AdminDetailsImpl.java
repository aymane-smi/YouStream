package com.example.demo.Services.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.Models.Entites.Admin;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.AdminRepository;
import com.example.demo.Repositories.StudentRepository;

@Component
public class AdminDetailsImpl implements UserDetailsService{

    private final AdminRepository adminRepository;

    public AdminDetailsImpl(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUser(username).orElseThrow(()->{
            throw new UsernameNotFoundException("Admin not found");
        });
        return new User(
            admin.getUsername(),
            admin.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.name()))
        );
    }
    
}
