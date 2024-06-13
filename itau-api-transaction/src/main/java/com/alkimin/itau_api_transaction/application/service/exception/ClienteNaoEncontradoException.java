package com.alkimin.itau_api_transaction.application.service.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(String msg) {
        super(msg);
    }
}
