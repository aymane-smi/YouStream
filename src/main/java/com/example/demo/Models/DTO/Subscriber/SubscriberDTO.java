package com.example.demo.Models.DTO.Subscriber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SubscriberDTO {
    private long id;
    private long streamerId;
    private long subscriberId;
}
