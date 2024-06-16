package com.alkimin.itau_api_transaction.application.entrypoint.controller;

import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;
import com.alkimin.itau_api_transaction.application.entrypoint.controller.response.TransactionResponse;
import com.alkimin.itau_api_transaction.application.service.TransactionValidatorService;
import com.alkimin.itau_api_transaction.core.usecase.TransactionUseCase;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("transferencia")
@RateLimiter(name = "myRateLimiter")
public class TransactionController {

    private TransactionUseCase transactionUseCase;

    private TransactionValidatorService transactionValidatorService;

    @PostMapping
    public ResponseEntity<TransactionResponse> sendTransaction(@RequestBody TransactionRequest transactionRequest){
        transactionValidatorService.validate(transactionRequest);
        var transacationId = transactionUseCase.execute(transactionRequest);
        return ResponseEntity.ok().body(new TransactionResponse(transacationId));
    }
}
