package com.example.demo.Services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DTO.Strike.StrikeDTO;
import com.example.demo.Models.Entites.Strike;
import com.example.demo.Repositories.StrikeRepository;
import com.example.demo.Services.StrikeService;

@Service
public class StrikeServiceImpl implements StrikeService{

    private StrikeRepository strikeRepository;
    private ModelMapper modelMapper;

    public StrikeServiceImpl(
        StrikeRepository strikeRepository,
        ModelMapper modelMapper
    ){
        this.strikeRepository = strikeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StrikeDTO> getAllStrikes() {
        List<Strike> result = strikeRepository.findAll();
        return List.of(modelMapper.map(result, StrikeDTO[].class));
    }
    
}
