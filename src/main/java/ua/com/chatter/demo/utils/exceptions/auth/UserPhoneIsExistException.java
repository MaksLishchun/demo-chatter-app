package ua.com.chatter.demo.utils.exceptions.auth;

public class UserPhoneIsExistException extends RuntimeException {
    public UserPhoneIsExistException(String message) {
        super(message);
    }

}