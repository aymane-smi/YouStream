package com.example.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DTO.Strike.StrikeDTO;
import com.example.demo.Services.StrikeService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/strike")
public class StrikeController {

    private StrikeService strikeService;

    public StrikeController(StrikeService strikeService){
        this.strikeService = strikeService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<StrikeDTO>> getAllStrike() {
        return new ResponseEntity<>(strikeService.getAllStrikes(), HttpStatus.OK);
    }
    
}
