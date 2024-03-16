package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
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

import com.example.demo.Models.DTO.Student.StudentListDTO;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Entites.Subscriber;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.StudentService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ModelMapper modelMapper;
    @MockBean
    private StudentService studentService;
    private Student student;
    private StudentListDTO studentList;

    @BeforeEach
    public void init(){
        studentService = new StudentServiceImpl(null, studentRepository, null, null, modelMapper, null);
        List<Subscriber> fakeList = new ArrayList<>();
        student = Student.builder().id(1)
                                   .username("student")
                                   .firstName("fn")
                                   .lastName("ln")
                                   .subscribers(fakeList)
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
}
