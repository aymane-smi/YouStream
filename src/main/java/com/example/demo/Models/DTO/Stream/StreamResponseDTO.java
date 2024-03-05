package com.example.demo.Models.DTO.Stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamResponseDTO {
    private StreamDTO stream;
    private String StreamToken;
}
