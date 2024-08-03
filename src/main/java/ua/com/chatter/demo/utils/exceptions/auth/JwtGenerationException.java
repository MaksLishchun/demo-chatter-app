package ua.com.chatter.demo.utils.exceptions.auth;


public class JwtGenerationException extends RuntimeException {

    public JwtGenerationException(String message) {
        super(message);
    }

}
