package com.store.dao;

import com.store.model.entity.CardContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardContractRepository extends JpaRepository<CardContract,Integer> {
}
