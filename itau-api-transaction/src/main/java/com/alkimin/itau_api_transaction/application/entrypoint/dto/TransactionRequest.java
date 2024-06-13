package com.alkimin.itau_api_transaction.application.entrypoint.dto;

import java.math.BigDecimal;

public record TransactionRequest(String idCliente, BigDecimal valor, ContaTransaction conta) {
}
