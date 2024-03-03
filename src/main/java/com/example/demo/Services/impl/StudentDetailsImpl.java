package com.example.demo.Services.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.StudentRepository;



@Service("StudentDetails")
public class StudentDetailsImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentDetailsImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username).orElseThrow(()->{
            throw new UsernameNotFoundException("Student not found");
        });
        return new User(
            student.getUsername(),
            student.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.name()))
        );
    }
    
}
