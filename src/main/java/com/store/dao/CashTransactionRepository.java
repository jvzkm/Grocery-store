package com.store.dao;

import com.store.model.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashTransactionRepository extends JpaRepository<CashTransaction,Integer> {
}
