package ua.com.chatter.demo.utils.exceptions;

public class ChatIsExistException extends  RuntimeException {
    public ChatIsExistException(String message) {
        super(message);
    }
}
