package com.alkimin.itau_api_transaction.application.service;

import com.alkimin.itau_api_transaction.application.dataprovider.repository.entity.TransactionEntity;

public interface BacenNotificationService {

    void notifyTransaction(TransactionEntity transaction);
}
