package ua.com.chatter.demo.model.dto;

import io.micrometer.common.lang.Nullable;

public class ChatterDefaultResponse {

    private String message;
    private Integer code;
    private @Nullable ErrorType errorType;

    public ChatterDefaultResponse() {
    }

    public ChatterDefaultResponse(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public ChatterDefaultResponse(Integer code, String message, @Nullable ErrorType errorType) {
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
