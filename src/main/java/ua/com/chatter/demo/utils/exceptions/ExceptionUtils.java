package ua.com.chatter.demo.utils.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.utils.exceptions.auth.JwtGenerationException;
import ua.com.chatter.demo.utils.exceptions.auth.UserEmailIsExistException;
import ua.com.chatter.demo.utils.exceptions.auth.UserPhoneIsExistException;

public class ExceptionUtils {
    public static ErrorType getErrorType(RuntimeException exp, ErrorType defaultErrorType) {
        if (exp instanceof UserPhoneIsExistException) {
            defaultErrorType = ErrorType.USER_PHONE_IS_EXIST;
        }
        if (exp instanceof UserEmailIsExistException) {
            defaultErrorType = ErrorType.USER_EMAIL_IS_EXIST;
        }
        if (exp instanceof EntityIsExistException) {
            defaultErrorType = ErrorType.ENTITY_IS_EXIST;
        }
        if (exp instanceof EntityIsNullException) {
            defaultErrorType = ErrorType.ENTITY_IS_NULL;
        }
        if (exp instanceof UsernameNotFoundException) {
            defaultErrorType = ErrorType.ENTITY_NOT_FOUND;
        }
        if (exp instanceof JwtGenerationException) {
            defaultErrorType = ErrorType.JWT_GENERATION;
        }
        return defaultErrorType;
    }
}
