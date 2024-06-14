package com.alkimin.itau_api_transaction.application.service;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.FeignClient;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.AccountResponse;
import com.alkimin.itau_api_transaction.application.entrypoint.dto.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionValidatorService {

    private FeignClient feignClient;

    public void valid(TransactionRequest transactionRequest) {
        validateCostumer(transactionRequest.idCliente());
        validateAccountReceiver(transactionRequest.conta().idDestino());
        validateAccountPayer(transactionRequest.conta().idOrigem(), transactionRequest.valor());
    }

    private void validateCostumer(String idCliente) {
        try{
            feignClient.getUser(idCliente);
        } catch (Exception exception) {
            throw new ClienteNaoEncontradoException(new StringBuilder("Não encontramos nenhum usuário com o Id: " + idCliente).toString());
        }
    }
    private void validateAccountReceiver(String idContaDestino) {
        AccountResponse receiverAccount = null;
        try {
            receiverAccount = feignClient.getAccount(idContaDestino).getBody();
        }catch (Exception exception) {
            throw new ContaDestinoNaoEncontradaException(new StringBuilder("Não encontramos a conta destino com o id: " + idContaDestino).toString());
        }
        if(!receiverAccount.ativo()){
            throw new ContaDestinoDesativadaException(new StringBuilder("A conta destino está desativada, id: " + idContaDestino).toString());
        }
    }
    private void validateAccountPayer(String idContaOrigem, BigDecimal valor) {
        AccountResponse payerAccount = null;
        try {
            payerAccount = feignClient.getAccount(idContaOrigem).getBody();
        }catch (Exception exception) {
            throw new ContaOrigemNaoEncontradaException(new StringBuilder("Não encontramos a conta origem com o id: " + idContaOrigem).toString());
        }
        if(payerAccount.saldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("O saldo da conta é inferior ao valor da transação.");
        }
        if(payerAccount.limiteDiario().compareTo(valor) < 0) {
            throw new LimiteDiarioException("O limite diário da conta é inferior ao valor da transação.");
        }
    }
}
