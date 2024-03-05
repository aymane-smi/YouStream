package com.example.demo.Helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Security {
    public static String retriveUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}
