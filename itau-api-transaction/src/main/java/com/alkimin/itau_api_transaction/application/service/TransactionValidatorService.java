package com.alkimin.itau_api_transaction.application.service;

import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;

public interface TransactionValidatorService {

    void validate(TransactionRequest transactionRequest);

}
