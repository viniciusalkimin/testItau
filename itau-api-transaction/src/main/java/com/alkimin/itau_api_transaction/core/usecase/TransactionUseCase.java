package com.alkimin.itau_api_transaction.core.usecase;

import com.alkimin.itau_api_transaction.application.entrypoint.controller.request.TransactionRequest;

public interface TransactionUseCase {


    String execute(TransactionRequest transactionRequest);
}
