package com.alkimin.itau_api_transaction.core.usecase;

import com.alkimin.itau_api_transaction.application.entrypoint.dto.TransactionRequest;

public interface SendTransactionUseCase {


    Object execute(TransactionRequest transactionRequest);
}
