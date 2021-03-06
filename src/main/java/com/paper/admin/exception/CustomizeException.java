package com.paper.admin.exception;

public class CustomizeException extends RuntimeException {
    public String message;
    public Integer code;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
