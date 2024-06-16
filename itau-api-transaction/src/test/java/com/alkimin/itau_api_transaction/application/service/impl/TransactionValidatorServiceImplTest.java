package com.alkimin.itau_api_transaction.application.service.impl;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.ApiFeignClient;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.AccountResponse;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.UserResponse;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.ContaTransaction;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorServiceImplTest {

    @InjectMocks
    TransactionValidatorServiceImpl transactionValidatorService;

    @Mock
    ApiFeignClient apiFeignClient;

    @Test
    void shouldReturnClienteNaoEncontradoException() {
        var costumerUser = new ContaTransaction("123","123");
        var request = new TransactionRequest("123",new BigDecimal("50"),costumerUser);

        Mockito.when(apiFeignClient.getUser(request.idCliente())).thenThrow(RuntimeException.class);

        assertThrows(ClienteNaoEncontradoException.class, () -> transactionValidatorService.validate(request));
    }

    @Test
    void shouldReturnContaDestinoNaoEncontradaExceptio() {
        var costumerUser = new ContaTransaction("123","123");
        var request = new TransactionRequest("123",new BigDecimal("50"),costumerUser);
        var costumerResponse = new UserResponse("123","123", "1166558877","FISICA");

        Mockito.when(apiFeignClient.getUser(request.idCliente())).thenReturn(ResponseEntity.ok(costumerResponse));
        Mockito.when(apiFeignClient.getAccount(request.conta().idDestino())).thenThrow(RuntimeException.class);

        assertThrows(ContaDestinoNaoEncontradaException.class, () -> transactionValidatorService.validate(request));
    }

    @Test
    void shouldReturnContaDestinoDesativadaException() {
        var costumerUser = new ContaTransaction("123","123");
        var request = new TransactionRequest("123",new BigDecimal("50"),costumerUser);
        var costumerResponse = new UserResponse("123","123", "1166558877","FISICA");
        var accountResponse = new AccountResponse("123", new BigDecimal("1500"),false, new BigDecimal("1500"));

        Mockito.when(apiFeignClient.getUser(request.idCliente())).thenReturn(ResponseEntity.ok(costumerResponse));
        Mockito.when(apiFeignClient.getAccount(request.conta().idDestino())).thenReturn(ResponseEntity.ok(accountResponse));

        assertThrows(ContaDestinoDesativadaException.class, () -> transactionValidatorService.validate(request));
    }

    @Test
    void shouldReturnSaldoInsuficienteException() {
        var costumerUser = new ContaTransaction("123","123");
        var request = new TransactionRequest("123",new BigDecimal("50"),costumerUser);
        var costumerResponse = new UserResponse("123","123", "1166558877","FISICA");
        var accountResponse = new AccountResponse("123", new BigDecimal("30"),true, new BigDecimal("500"));

        Mockito.when(apiFeignClient.getUser(any())).thenReturn(ResponseEntity.ok(costumerResponse));
        Mockito.when(apiFeignClient.getAccount(any())).thenReturn(ResponseEntity.ok(accountResponse));

        assertThrows(SaldoInsuficienteException.class, () -> transactionValidatorService.validate(request));
    }

    @Test
    void shouldReturnLimiteDiarioException() {
        var costumerUser = new ContaTransaction("123","123");
        var request = new TransactionRequest("123",new BigDecimal("1500"),costumerUser);
        var costumerResponse = new UserResponse("123","123", "1166558877","FISICA");
        var accountResponse = new AccountResponse("123", new BigDecimal("2000"),true, new BigDecimal("500"));

        Mockito.when(apiFeignClient.getUser(any())).thenReturn(ResponseEntity.ok(costumerResponse));
        Mockito.when(apiFeignClient.getAccount(any())).thenReturn(ResponseEntity.ok(accountResponse));

        assertThrows(LimiteDiarioException.class, () -> transactionValidatorService.validate(request));
    }



}