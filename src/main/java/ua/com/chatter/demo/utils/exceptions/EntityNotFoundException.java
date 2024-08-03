package ua.com.chatter.demo.utils.exceptions;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
         super(message);
    }
 }
