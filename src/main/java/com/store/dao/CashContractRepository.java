package com.store.dao;

import com.store.model.entity.CashContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashContractRepository extends JpaRepository<CashContract,Integer> {
}
