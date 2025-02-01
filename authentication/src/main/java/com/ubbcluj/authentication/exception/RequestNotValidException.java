package com.ubbcluj.authentication.exception;

public class RequestNotValidException extends BaseException{
    public RequestNotValidException() {
        super(ServiceErrorCodes.INVALID_REQUEST.errorCode(),
                ServiceErrorCodes.INVALID_REQUEST.errorName(),
                ServiceErrorCodes.INVALID_REQUEST.errorMessage());
    }
}
