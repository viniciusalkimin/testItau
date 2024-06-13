package com.alkimin.itau_api_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ItauApiTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItauApiTransactionApplication.class, args);
	}

}
