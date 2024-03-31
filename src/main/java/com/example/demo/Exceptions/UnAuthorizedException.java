package com.example.demo.Exceptions;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(){}

    public UnAuthorizedException(String message){
        super(message);
    }
}
