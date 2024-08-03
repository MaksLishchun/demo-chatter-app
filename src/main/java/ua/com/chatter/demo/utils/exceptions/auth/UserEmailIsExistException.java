package ua.com.chatter.demo.utils.exceptions.auth;


public class UserEmailIsExistException extends RuntimeException {

    public UserEmailIsExistException(String message) {
        super(message);
    }

}