package com.alkimin.itau_api_transaction.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID idTransaction;
    private String idCliente;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;
    private LocalDateTime transactionTimeStamp;
}
