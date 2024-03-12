package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.Models.DTO.Notification.NotificationResponseDTO;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Repositories.NotificationRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.SubscriberRepository;
import com.example.demo.Services.NotificationService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @MockBean
    private NotificationService notificationService;
    private Student streamer; 

    @BeforeEach
    public void init(){
        notificationService = new NotificationServiceImpl(studentRepository, subscriberRepository, notificationRepository);
        streamer = Student.builder().claimers(null)
                                    .id(1)
                                    .comments(null)
                                    .firstName("fn")
                                    .lastName("ln")
                                    .followings(null)
                                    .username("student1")
                                    .password("123")
                                    .build();
    }
    
    @Test
    @DisplayName("testing the functionality of batch notification while creating a stream")
    @WithMockUser(username = "student1", authorities = {"STUDENT"})
    void testBatchNotificationForStudent() {
        Map<Long, NotificationResponseDTO> notifiMap = new HashMap<>();
        when(studentRepository.findByUsername("student1")).thenReturn(Optional.of(streamer));
        when(subscriberRepository.findSubscribers(1)).thenReturn(null);
        var result = notificationService.BatchNotificationForStudent("stream_id");
        assertEquals(result.size(), 0);
    }
}
