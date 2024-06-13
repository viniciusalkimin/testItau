package com.alkimin.itau_api_transaction.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID idTransaction;
    private String idCliente;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;
}
