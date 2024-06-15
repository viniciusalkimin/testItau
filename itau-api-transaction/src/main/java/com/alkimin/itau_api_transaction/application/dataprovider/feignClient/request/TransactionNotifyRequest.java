package com.alkimin.itau_api_transaction.application.dataprovider.feignClient.request;

import java.math.BigDecimal;

public record TransactionNotifyRequest(BigDecimal valor, ContaNotifyRequest contaNotifyRequest) {
}
