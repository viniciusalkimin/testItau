package com.alkimin.itau_api_transaction.application.service.impl;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.ApiFeignClient;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.AccountResponse;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.TransactionValidatorService;
import com.alkimin.itau_api_transaction.application.service.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionValidatorServiceImpl implements TransactionValidatorService {

    private ApiFeignClient apiFeignClient;

    @Override
    public void validate(TransactionRequest transactionRequest) {
        log.info("Status = início, TransactionValidatorService.validate().");

        validateCostumer(transactionRequest.idCliente());
        validateAccountReceiver(transactionRequest.conta().idDestino());
        validateAccountPayer(transactionRequest.conta().idOrigem(), transactionRequest.valor());

        log.info("Status = fim, TransactionValidatorService.validate().");
    }

    private void validateCostumer(String idCliente) {
        log.info("Status = início, TransactionValidatorService.validateCostumer().");
        try{
            apiFeignClient.getUser(idCliente);
        } catch (Exception exception) {
            throw new ClienteNaoEncontradoException(new StringBuilder("Não encontramos nenhum usuário com o Id: " + idCliente).toString());
        }
        log.info("Status = fim, TransactionValidatorService.validateCostumer().");
    }
    private void validateAccountReceiver(String idContaDestino) {
        log.info("Status = início, TransactionValidatorService.validateAccountReceiver().");
        AccountResponse receiverAccount = null;
        try {
            receiverAccount = apiFeignClient.getAccount(idContaDestino).getBody();
        }catch (Exception exception) {
            throw new ContaDestinoNaoEncontradaException(new StringBuilder("Não encontramos a conta destino com o id: " + idContaDestino).toString());
        }
        if(!receiverAccount.ativo()){
            throw new ContaDestinoDesativadaException(new StringBuilder("A conta destino está desativada, id: " + idContaDestino).toString());
        }
        log.info("Status = fim, TransactionValidatorService.validateAccountReceiver().");
    }
    private void validateAccountPayer(String idContaOrigem, BigDecimal valor) {
        log.info("Status = início, TransactionValidatorService.validateAccountPayer().");
        AccountResponse payerAccount = null;
        try {
            payerAccount = apiFeignClient.getAccount(idContaOrigem).getBody();
        }catch (Exception exception) {
            throw new ContaOrigemNaoEncontradaException(new StringBuilder("Não encontramos a conta origem com o id: " + idContaOrigem).toString());
        }
        if(payerAccount.saldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("O saldo da conta é inferior ao valor da transação.");
        }
        if(payerAccount.limiteDiario().compareTo(valor) < 0) {
            throw new LimiteDiarioException("O limite diário da conta é inferior ao valor da transação.");
        }
        log.info("Status = fim, TransactionValidatorService.validateAccountPayer().");
    }
}
