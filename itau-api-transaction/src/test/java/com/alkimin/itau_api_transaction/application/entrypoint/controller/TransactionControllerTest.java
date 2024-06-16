package com.alkimin.itau_api_transaction.application.entrypoint.controller;

import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.ContaTransaction;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.response.TransactionResponse;
import com.alkimin.itau_api_transaction.application.service.TransactionValidatorService;
import com.alkimin.itau_api_transaction.core.usecase.TransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionUseCase transactionUseCase;

    @Mock
    private TransactionValidatorService transactionValidatorService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendTransaction_ValidRequest_ReturnsTransactionResponse() {

        String transactionId = "transactionId123";
        TransactionRequest request = createTransactionRequest("clienteId", "contaDestino", "contaOrigem", BigDecimal.TEN);
        when(transactionUseCase.execute(request)).thenReturn(transactionId);

        ResponseEntity<TransactionResponse> response = transactionController.sendTransaction(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionId, response.getBody().id_transferencia());
        verify(transactionValidatorService).validate(request);
        verify(transactionUseCase).execute(request);
    }

    @Test
    void sendTransaction_InvalidRequest_ThrowsException() {

        TransactionRequest request = createTransactionRequest("clienteId", "contaDestino", "contaOrigem", BigDecimal.TEN);
        doThrow(new RuntimeException("Validation failed")).when(transactionValidatorService).validate(request);

        assertThrows(RuntimeException.class, () -> transactionController.sendTransaction(request));
        verify(transactionValidatorService).validate(request);
        verify(transactionUseCase, never()).execute(request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    private TransactionRequest createTransactionRequest(String idCliente, String idContaDestino, String idContaOrigem, BigDecimal valor) {
        var account = new ContaTransaction(idContaDestino, idContaOrigem);
        return new TransactionRequest(idCliente, valor, account);
    }
}