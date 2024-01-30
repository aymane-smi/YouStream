package com.example.demo.Service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.Service.IValidation;

@Service
public class Validation implements IValidation{

    @Override
    public boolean validation(String key) {
        if(key.isEmpty() || key == null || !key.equals("test@123"))
            return false;
        return true;
    }
    
}
