package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.Models.DTO.Stream.StreamCreateDTO;
import com.example.demo.Models.DTO.Stream.StreamDTO;
import com.example.demo.Models.DTO.Tag.TagDTO;
import com.example.demo.Models.Entites.Stream;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Models.Entites.Tag;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.TagRepository;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
public class ValidationServiceImplTest {
    @MockBean
    private ValidationServiceImpl validationService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private StreamRepository streamRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock Authentication authentication;
    private List<Tag> tags;
    private Base64 base64;
    private Student student;
    private Stream stream;
    @Mock
    private Stream streamMock;
    private StreamDTO streamDTO;
    private List<TagDTO> tagDTOs;
    private StreamCreateDTO streamCreateDTO;
    @BeforeEach
    public void init(){
        validationService = new ValidationServiceImpl(studentRepository, passwordEncoder, streamRepository, tagRepository, modelMapper);
        tags = new ArrayList<>();
        tags.add(new Tag(0, "TAG1", null));
        base64 = new Base64();
        tagDTOs = new ArrayList<>();
        tagDTOs.add(new TagDTO(0, "TAG1"));
        streamCreateDTO = StreamCreateDTO.builder().tags(tagDTOs).build();
        student = Student.builder().id(1)
                                   .isActive(true)
                                   .role(Role.STUDENT)
                                   .firstName("student")
                                   .lastName("student")
                                   .username("student")
                                   .password("student")
                                   .streams(null)
                                   .build();
        stream = Stream.builder().id(1)
                                 .file_name(null)
                                 .viewsNbr(0)
                                 .restricted(false)
                                 .owner(student)
                                 .build();
        streamDTO = StreamDTO.builder().file_name(null)
                                       .viewsNbr(0)
                                       .restricted(false)
                                       .tags(tagDTOs)
                                       .build();
    }
    @Test
    @DisplayName("test the creation of a stream")
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    void testCreateStream() {
        var testStudent = Optional.of(student);
        when(studentRepository.findByUsername("student")).thenReturn(testStudent);
        when(streamRepository.save(any(Stream.class))).thenReturn(stream);
        when(modelMapper.map(stream, StreamDTO.class)).thenReturn(streamDTO);
        var streamResponse = validationService.createStream(streamCreateDTO);
        assertEquals(streamResponse.getStream().getFile_name(), null);
    }

    @Test
    @DisplayName("test validation from obs")
    void testValidation() {
        System.out.println(student.getFirstName());
        when(studentRepository.findByUsername("student")).thenReturn(Optional.of(student));
        when(passwordEncoder.matches("student", student.getPassword())).thenReturn(true);
        when(streamRepository.findById(1L)).thenReturn(Optional.of(stream));
        when(streamRepository.save(any(Stream.class))).thenReturn(stream);
        String rawToken = "student"+"-"+System.currentTimeMillis()+"-"+stream.getId();
        assertTrue(validationService.validation(base64.encodeBase64String(rawToken.getBytes()), "student"));
    }
}
