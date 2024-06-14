package com.alkimin.itau_api_transaction.application.service.exception;

public class LimiteDiarioException extends RuntimeException {
    public LimiteDiarioException(String msg) {
        super(msg);
    }
}
