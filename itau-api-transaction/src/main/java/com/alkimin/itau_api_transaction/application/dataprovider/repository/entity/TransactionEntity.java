package com.alkimin.itau_api_transaction.application.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_TRANSACTION")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTransaction;
    private String idCliente;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;
    private LocalDateTime transactionTimeStamp;
}
