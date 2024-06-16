package com.alkimin.itau_api_transaction.application.service.impl;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.ApiFeignClient;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.request.ContaNotifyRequest;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.request.TransactionNotifyRequest;
import com.alkimin.itau_api_transaction.application.dataprovider.repository.entity.TransactionEntity;
import com.alkimin.itau_api_transaction.application.service.BacenNotificationService;
import com.alkimin.itau_api_transaction.application.service.exception.RateLimitExceededException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
@AllArgsConstructor
public class BacenNotificationServiceImpl implements BacenNotificationService {

    private ApiFeignClient apiFeignClient;
    @Override
    @Retry(name = "apiRetry", fallbackMethod = "fallback")
    public void notifyTransaction(TransactionEntity transaction) {
        log.info("Status = início, BacenNotificationService.notifyTransaction().");

        var account = new ContaNotifyRequest(transaction.getIdContaOrigem(), transaction.getIdContaDestino());
        var transactionNotify = new TransactionNotifyRequest(transaction.getValor(), account);
        try {
            apiFeignClient.notify(transactionNotify);
        }
        catch (HttpClientErrorException.TooManyRequests exception){
            log.error("Status = error, BacenNotificationService.notifyTransaction().");
            throw new RateLimitExceededException("Falha na comunicação com Bacen.");
        }
        log.info("Status = fim, BacenNotificationService.notifyTransaction().");
    }
    public void fallback(Throwable t) {
        throw new RateLimitExceededException("Erro ao se comunicar com Bacen: " + t.getMessage());
    }
}
