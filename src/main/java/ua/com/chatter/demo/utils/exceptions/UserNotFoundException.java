package ua.com.chatter.demo.utils.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
         super(message);
    }
 }
