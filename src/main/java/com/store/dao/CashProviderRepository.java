package com.store.dao;

import com.store.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashProviderRepository extends JpaRepository<Provider,Integer> {
}
