package com.example.demo.Services;

import com.example.demo.Models.DTO.Stream.StreamCreateDTO;
import com.example.demo.Models.DTO.Stream.StreamResponseDTO;

public interface ValidationService{
    public boolean validation(String token, String password);
    public StreamResponseDTO createStream(StreamCreateDTO streamDTO);
}