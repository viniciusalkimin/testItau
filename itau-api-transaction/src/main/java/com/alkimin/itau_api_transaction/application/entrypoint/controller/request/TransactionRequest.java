package com.alkimin.itau_api_transaction.application.entrypoint.controller.request;

import java.math.BigDecimal;

public record TransactionRequest(String idCliente, BigDecimal valor, ContaTransaction conta) {
}
