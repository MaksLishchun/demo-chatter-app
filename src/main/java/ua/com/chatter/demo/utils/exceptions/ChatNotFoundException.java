package ua.com.chatter.demo.utils.exceptions;

public class ChatNotFoundException extends RuntimeException {
    
    public ChatNotFoundException(String message) {
         super(message);
    }
 }
