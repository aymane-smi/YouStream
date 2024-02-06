package com.example.demo.Controllers;

import com.example.demo.Services.impl.Validation;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ValidationController {
    @Autowired
    private Validation ValidationService;
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> validate(@RequestParam MultiValueMap body){
        //System.out.println("body===>"+body.getFirst("password"));
        if(ValidationService.validation(body.getFirst("password").toString()))
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    }
    
}
