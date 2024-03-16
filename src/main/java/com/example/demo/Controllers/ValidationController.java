package com.example.demo.Controllers;

import com.example.demo.Models.DTO.Stream.StreamCreateDTO;
import com.example.demo.Models.DTO.Stream.StreamResponseDTO;
import com.example.demo.Services.ValidationService;

import jakarta.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stream")
@CrossOrigin(origins = "http://localhost:3000")
public class ValidationController {
    private final ValidationService ValidationService;
    public ValidationController(
        ValidationService validationService
    ){
        this.ValidationService = validationService;
    }
    @PostMapping
    public ResponseEntity<StreamResponseDTO> createStream(@Valid @RequestBody StreamCreateDTO streamDTO){
        var stream = ValidationService.createStream(streamDTO);
        return new ResponseEntity<>(stream, HttpStatus.CREATED);
    }
    @PostMapping(path = "/validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> validate(@RequestParam MultiValueMap body){
        if(ValidationService.validation(body.getFirst("name").toString(), body.getFirst("password").toString()))
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    }
    
}
