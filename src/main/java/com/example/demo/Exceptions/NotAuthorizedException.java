package com.example.demo.Exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(){

    }
    public NotAuthorizedException(String message){
        super(message);
    }
}
