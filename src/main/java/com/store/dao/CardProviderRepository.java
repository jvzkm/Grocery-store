package com.store.dao;

import com.store.model.entity.CardProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardProviderRepository extends JpaRepository<CardProvider,Integer> {
}
