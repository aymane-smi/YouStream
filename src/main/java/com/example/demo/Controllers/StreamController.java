package com.example.demo.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Services.StreamService;

@RestController
@RequestMapping("/api/stream/features")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/page")
    public ResponseEntity<List<StreamTopDTO>> getStreamsPagination(@RequestParam int offset){
        return new ResponseEntity<>(streamService.getAllStream(offset), HttpStatus.OK);
    }
}
