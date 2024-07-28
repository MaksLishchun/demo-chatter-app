package ua.com.chatter.demo.utils.exceptions;

public class MessagesNotFoundException extends RuntimeException {
    
   public MessagesNotFoundException(String message) {
        super(message);
   }
}
