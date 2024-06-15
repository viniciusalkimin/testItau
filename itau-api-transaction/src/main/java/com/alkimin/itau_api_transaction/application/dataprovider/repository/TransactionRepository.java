package com.alkimin.itau_api_transaction.application.dataprovider.repository;

import com.alkimin.itau_api_transaction.application.dataprovider.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}
