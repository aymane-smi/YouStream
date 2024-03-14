package com.example.demo.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Models.DTO.Student.SignedStudentDTO;
import com.example.demo.Models.DTO.Student.StudentDTO;
import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Models.DTO.Student.StudentLoginDTO;
import com.example.demo.Models.DTO.Student.StudentRDTO;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Entites.StudentRefreshToken;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.StudentRefreshRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final StudentDetailsImpl studentDetailsImpl;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final StudentRefreshRepository studentRefreshRepository;

    public StudentServiceImpl(
        PasswordEncoder passwordEncoder,
        StudentRepository studentRepository,
        StudentDetailsImpl studentDetailsImpl,
        JwtService jwtService,
        ModelMapper modelMapper,
        StudentRefreshRepository studentRefreshRepository
    ){
        this.passwordEncoder = passwordEncoder;
        this.studentDetailsImpl = studentDetailsImpl;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.studentRefreshRepository = studentRefreshRepository;
    }

    @Override
    public SignedStudentDTO login(StudentLoginDTO student) {
        var user = studentDetailsImpl.loadUserByUsername(student.getUsername());
        UUID uuid = UUID.randomUUID();
        var newRefresh = new StudentRefreshToken(
            uuid,
            studentRepository.findByUsername(student.getUsername()).get()
        );
        if(passwordEncoder.matches(student.getPassword(), user.getPassword())){
            String token = jwtService.generateToken(user);
            return SignedStudentDTO.builder().id(
                studentRepository.findIdByUsername(student.getUsername()).get()
            )
            .username(student.getUsername())
            .token(token)
            .refresh_token(newRefresh.getId().toString())
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
    @PreAuthorize("hasAuthority('STUDENT')")
    @Override
    public void logout(UUID refresh_token) {
        studentRefreshRepository.deleteById(refresh_token);
    }

    @Override
    public SignedStudentDTO createRefreshToken(UUID refresh_token) {
        var refresh = studentRefreshRepository.findById(refresh_token).get();
        var student = studentDetailsImpl.loadUserByUsername(refresh.getStudent().getUsername());
        UUID uuid = UUID.randomUUID();
        var newRefresh = new StudentRefreshToken(
            uuid,
            studentRepository.findByUsername(student.getUsername()).get()
        );
        String token = jwtService.generateToken(student);
        return SignedStudentDTO.builder().id(
                studentRepository.findIdByUsername(student.getUsername()).get()
            )
            .username(student.getUsername())
            .token(token)
            .refresh_token(newRefresh.getId().toString())
            .build();
    }

    @Override
    public List<StudentListDTO> getListStudent() {
        List<Student> students = studentRepository.findAll();
        List<StudentListDTO> list = new ArrayList<>();
        students.forEach(student ->{
            var tmp = modelMapper.map(student, StudentListDTO.class);
            tmp.setFollowersNbr(student.getSubscribers().size());
            list.add(tmp);
        });
       return list;
    }
    
}
