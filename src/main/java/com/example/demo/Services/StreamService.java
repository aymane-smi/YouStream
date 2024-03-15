package com.example.demo.Services;

import java.util.List;

import com.example.demo.Models.DTO.Stream.StreamTopDTO;

public interface StreamService {
    public List<StreamTopDTO> getTopTenStream();
    public List<StreamTopDTO> getAllStream(int offset);
}
