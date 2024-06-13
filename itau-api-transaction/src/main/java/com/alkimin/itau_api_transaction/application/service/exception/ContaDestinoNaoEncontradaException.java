package com.alkimin.itau_api_transaction.application.service.exception;

public class ContaDestinoNaoEncontradaException extends RuntimeException{
    public ContaDestinoNaoEncontradaException(String msg) {
        super(msg);
    }
}
