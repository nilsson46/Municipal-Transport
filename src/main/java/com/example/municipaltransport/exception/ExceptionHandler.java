package com.example.municipaltransport.exception;

public class ExceptionHandler extends RuntimeException{
    public ExceptionHandler (Long id){
        super("Rout not found with id" + id);
    }
}
