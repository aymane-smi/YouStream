package com.example.demo.Services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final StudentDetailsImpl studentDetailsImpl;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(
        PasswordEncoder passwordEncoder,
        StudentRepository studentRepository,
        StudentDetailsImpl studentDetailsImpl,
        JwtService jwtService,
        ModelMapper modelMapper
    ){
        this.passwordEncoder = passwordEncoder;
        this.studentDetailsImpl = studentDetailsImpl;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SignedStudentDTO login(StudentLoginDTO student) {
        var user = studentDetailsImpl.loadUserByUsername(student.getUsername());
        if(passwordEncoder.matches(student.getPassword(), user.getPassword())){
            String token = jwtService.generateToken(user);
            return SignedStudentDTO.builder().id(
                studentRepository.findIdByUsername(student.getUsername()).get()
            )
            .username(student.getUsername())
            .token(token)
            .build();
        }
        throw new InsufficientAuthenticationException("Unauthorized");
    }

    @Override
    public StudentDTO signup(StudentRDTO student) {
        student.setPassword(
            passwordEncoder.encode(student.getPassword())
        );
        var Entity = modelMapper.map(student, Student.class);
        Entity.setRole(Role.STUDENT);
        return modelMapper.map(
            studentRepository.save(Entity),
            StudentDTO.class
        );
    }
    
}
