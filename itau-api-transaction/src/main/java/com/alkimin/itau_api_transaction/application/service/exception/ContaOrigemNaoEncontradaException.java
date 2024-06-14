package com.alkimin.itau_api_transaction.application.service.exception;

public class ContaOrigemNaoEncontradaException extends RuntimeException {
    public ContaOrigemNaoEncontradaException(String msg) {
        super(msg);
    }
}
