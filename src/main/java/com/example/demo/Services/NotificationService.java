package com.example.demo.Services;

import java.util.Map;

import com.example.demo.Models.DTO.Notification.NotificationResponseDTO;

public interface NotificationService {
    public Map<Long, NotificationResponseDTO> BatchNotificationForStudent(String streamId);
}
