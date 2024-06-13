package com.alkimin.itau_api_transaction.application.dataprovider.feignClient.dto;

import java.math.BigDecimal;

public record AccountResponse(String id, BigDecimal saldo, boolean ativo, BigDecimal limiteDiario) {
}
