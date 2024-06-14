package com.alkimin.itau_api_transaction.application.entrypoint.controller;

import com.alkimin.itau_api_transaction.application.entrypoint.dto.TransactionRequest;
import com.alkimin.itau_api_transaction.application.service.TransactionValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("transferencia")
public class TransactionController {

    //private SendTransactionUseCase sendTransactionUseCase;

    private TransactionValidatorService transactionValidatorService;

    @PostMapping
    public ResponseEntity<?> sendTransaction(@RequestBody TransactionRequest transactionRequest){
        transactionValidatorService.valid(transactionRequest);
        return ResponseEntity.ok(/*sendTransactionUseCase.execute(transactionRequest)*/).build();
    }
}
