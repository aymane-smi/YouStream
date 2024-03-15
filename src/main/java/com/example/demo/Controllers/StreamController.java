package com.example.demo.Controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Services.StreamService;

@RestController
@RequestMapping("/api/streamtest")
public class StreamController {
    private final StreamService streamService;

    public StreamController(
        StreamService streamService
    ){
        this.streamService = streamService;
    }

    @GetMapping
    public ResponseEntity<List<StreamTopDTO>> getTopStream(){
        return new ResponseEntity<>(streamService.getTopTenStream(), HttpStatus.OK);
    }
}
