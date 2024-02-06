package com.example.demo.Services.impl;

import org.springframework.stereotype.Service;

import com.example.demo.Services.IValidation;

@Service
public class Validation implements IValidation{

    @Override
    public boolean validation(String key) {
        if(key.isEmpty() || key == null || !key.equals("test@123"))
            return false;
        return true;
    }
    
}
