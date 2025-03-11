package com.tour.booking.tyme.exception;

public class CustomerException extends RuntimeException {
    private final String errorCode;
    
    public CustomerException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
} 