package com.store.dao;

import com.store.model.entity.CashProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashProviderRepository extends JpaRepository<CashProvider,Integer> {
}
