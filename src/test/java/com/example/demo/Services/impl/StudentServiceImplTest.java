package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Entites.Subscriber;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.StudentService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @MockBean
    private StudentService studentService;
    private Student student;
    private StudentListDTO studentList;

    @BeforeEach
    public void init(){
        studentService = new StudentServiceImpl(passwordEncoder, studentRepository, null, null, modelMapper, null);
        List<Subscriber> fakeList = new ArrayList<>();
        student = Student.builder().id(1)
                                   .username("student")
                                   .firstName("fn")
                                   .lastName("ln")
                                   .subscribers(fakeList)
                                   .password("student")
                                   .build();
        studentList = StudentListDTO.builder().id(1)
                                              .firstName("fn")
                                              .lastName("ln")
                                              .username("student")
                                              .followersNbr(1)
                                              .build();
    }
    @Test
    @Description("get all student with all there info + nbr of subscribers")
    void testGetListStudent() {
        List<Student> list = new ArrayList<>();
        List<Subscriber> fakeList = new ArrayList<>();
        student.setSubscribers(fakeList);
        list.add(student);
        when(studentRepository.findAll()).thenReturn(list);
        when(modelMapper.map(list.get(0), StudentListDTO.class)).thenReturn(studentList);
        var result = studentService.getListStudent();
        assertEquals(result.size(), 1);
    }

    @Test
    @Description("mocking edit username for student")
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void testEditUsername(){
        when(studentRepository.findByUsername("student")).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);
        assertEquals(studentService.editUsername("student"), "student");
    }

    @Test
    @Description("mocking edit username for student")
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void testEditPassword(){
        when(studentRepository.findByUsername("student")).thenReturn(Optional.of(student));
        when(passwordEncoder.encode("student")).thenReturn("student");
        when(studentRepository.save(any())).thenReturn(student);
        assertEquals(studentService.editPassword("student"), true);
    }

}
