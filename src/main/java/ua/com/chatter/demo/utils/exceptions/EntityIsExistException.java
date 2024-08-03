package ua.com.chatter.demo.utils.exceptions;


public class EntityIsExistException extends RuntimeException {

    public EntityIsExistException(String message) {
        super(message);
    }

}
