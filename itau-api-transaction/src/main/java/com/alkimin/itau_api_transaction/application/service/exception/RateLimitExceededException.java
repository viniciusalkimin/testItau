package com.alkimin.itau_api_transaction.application.service.exception;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String msg) {
        super(msg);
    }
}
