package com.alkimin.itau_api_transaction.application.dataprovider.feignClient;

import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.AccountResponse;
import com.alkimin.itau_api_transaction.application.dataprovider.feignClient.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@org.springframework.cloud.openfeign.FeignClient(value = "validCall", url = "http://localhost:9090")
public interface FeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/clientes/{userId}", produces = "application/json")
    ResponseEntity<UserResponse> getUser(@PathVariable String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/contas/{accountId}", produces = "application/json")
    ResponseEntity<AccountResponse> getAccount(@PathVariable String accountId);
}
