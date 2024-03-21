package com.example.demo.Models.DTO.Subscriber;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubscriberRDTO {
    @Min(value = 1, message = "streamer id is required and must be greater than 0")
    private long streamerId;
}
