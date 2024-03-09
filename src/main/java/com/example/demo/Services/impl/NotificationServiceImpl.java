package com.example.demo.Services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.Helper.Security;
import com.example.demo.Models.DTO.Notification.NotificationResponseDTO;
import com.example.demo.Models.Entites.Notification;
import com.example.demo.Models.Entites.Student;
import com.example.demo.Repositories.NotificationRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.SubscriberRepository;
import com.example.demo.Services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final StudentRepository studentRepository;
    private final SubscriberRepository subscriberRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(
        StudentRepository studentRepository,
        SubscriberRepository subscriberRepository,
        NotificationRepository notificationRepository
        ){
        this.studentRepository = studentRepository;
        this.subscriberRepository = subscriberRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Map<Long, NotificationResponseDTO> BatchNotificationForStudent(String streamId) {
        Map<Long, NotificationResponseDTO> notifiMap = new HashMap<>();
        Student streamer = studentRepository.findByUsername(Security.retriveUsername()).get();
        List<Student> subscribers = subscriberRepository.findSubscribers(streamer.getId());
        for(Student subscriber: subscribers){
            StringBuilder sb = new StringBuilder();
            sb.append("join the stream launched by "+streamer.getUsername());
            notificationRepository.save(Notification.builder()
                                                    .streamer(streamer)
                                                    .message(sb.toString())
                                                    .student(subscriber)
                                                    .build());
            notifiMap.put(subscriber.getId(), NotificationResponseDTO.builder()
                                                             .streamId(streamId)
                                                             .streamer(streamer.getUsername())
                                                             .message(sb.toString())
                                                             .build()
            );
        }
        return notifiMap;
    }

}
