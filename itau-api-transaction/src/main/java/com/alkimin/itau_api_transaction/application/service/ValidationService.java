package com.alkimin.itau_api_transaction.application.service;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.ValidationCallsFeignClient;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.dto.AccountResponse;
import com.alkimin.itau_api_transaction.application.entrypoint.dto.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.exception.ClienteNaoEncontradoException;
import com.alkimin.itau_api_transaction.application.service.exception.ContaDestinoDesativadaException;
import com.alkimin.itau_api_transaction.application.service.exception.ContaDestinoNaoEncontradaException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidationService {

    private ValidationCallsFeignClient validationCallsFeignClient;

    public void valid(TransactionRequest transactionRequest) {
        validarCliente(transactionRequest.idCliente());
        validarContaCliente(transactionRequest.conta().idDestino());
    }

    private void validarCliente(String idCliente) {
        try{
            validationCallsFeignClient.getUser(idCliente);
        } catch (Exception exception) {
            throw new ClienteNaoEncontradoException(new StringBuilder("Não encontramos nenhum usuário com o Id:" + idCliente).toString());
        }
    }
    private void validarContaCliente(String idContaDestino) {
        AccountResponse account;
        try {
            account = validationCallsFeignClient.getAccount(idContaDestino).getBody();
        }catch (Exception exception) {
            throw new ContaDestinoNaoEncontradaException(new StringBuilder("Não encontramos a conta destino com o id:" + idContaDestino).toString());
        }
        if(!account.ativo()){
            throw new ContaDestinoDesativadaException(new StringBuilder("A conta destino esta desativada, id:" + idContaDestino).toString());
        }
    }
}
