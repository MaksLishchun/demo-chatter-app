package ua.com.chatter.demo.model.dto;

import io.micrometer.common.lang.Nullable;

public class ChattyDefaultResponse {

    private String message;
    private Integer code;
    private ErrorType errorType;

    public ChattyDefaultResponse() {
    }


    public ChattyDefaultResponse(Integer code, String message, @Nullable ErrorType errorType) {
        this.message = message;
        this.code = code;
        this.errorType = errorType;
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }


}
