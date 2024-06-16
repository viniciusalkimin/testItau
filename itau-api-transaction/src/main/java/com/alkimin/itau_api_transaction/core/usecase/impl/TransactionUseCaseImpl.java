package com.alkimin.itau_api_transaction.core.usecase.impl;

import com.alkimin.itau_api_transaction.application.dataprovider.repository.TransactionRepository;
import com.alkimin.itau_api_transaction.application.dataprovider.repository.entity.TransactionEntity;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.BacenNotificationService;
import com.alkimin.itau_api_transaction.core.domain.Transaction;
import com.alkimin.itau_api_transaction.core.usecase.TransactionUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionUseCaseImpl implements TransactionUseCase {

    private TransactionRepository transactionRepository;

    private BacenNotificationService bacenNotificationService;

    @Override
    @Transactional
    public String execute(TransactionRequest transactionRequest) {
        log.info("Status = início, TransactionUseCase.execute().");

        var transaction = Transaction.builder().valor(transactionRequest.valor())
                .idCliente(transactionRequest.idCliente()).idContaOrigem(transactionRequest.conta().idOrigem())
                .idContaDestino(transactionRequest.conta().idDestino()).transactionTimeStamp(LocalDateTime.now()).build();

        var transactionEntity = transactionRepository.save(new TransactionEntity(transaction));
        bacenNotificationService.notifyTransaction(transactionEntity);
        log.info("Status = fim, TransactionUseCase.execute().");

        return transactionEntity.getIdTransaction().toString();
    }
}
