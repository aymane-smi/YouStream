package com.example.demo.Controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Notification.NotificationRequestDTO;
import com.example.demo.Models.DTO.Notification.NotificationResponseDTO;
import com.example.demo.Services.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    public NotificationController(
        NotificationService notificationService,
        SimpMessagingTemplate simpMessagingTemplate
    ){
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @GetMapping("/publish")
    public ResponseEntity<String> publishStream(@Valid @RequestBody NotificationRequestDTO notification){
        Map<Long, NotificationResponseDTO> result = notificationService.BatchNotificationForStudent(notification.getStreamId());
        result.forEach((k, v)->{
            StringBuilder sb = new StringBuilder();
            sb.append("/student/"+k);
            simpMessagingTemplate.convertAndSend( sb.toString(), v);
        });
        return new ResponseEntity<>("stream published", HttpStatus.OK);
    }
}
