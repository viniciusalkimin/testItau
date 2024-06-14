package com.alkimin.itau_api_transaction.application.service.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String msg) {
        super((msg));
    }
}
