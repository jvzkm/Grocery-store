package com.store.dao;

import com.store.model.entity.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransactionRepository  extends JpaRepository<CardTransaction,Integer> {
}
