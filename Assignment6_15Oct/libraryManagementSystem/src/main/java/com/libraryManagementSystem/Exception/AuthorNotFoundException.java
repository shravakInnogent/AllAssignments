package com.libraryManagementSystem.Exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(){
        super("No author found");
    }
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
