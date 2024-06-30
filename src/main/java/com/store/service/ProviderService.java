package com.store.service;

import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import com.store.model.entity.Provider;

import java.util.List;

public interface ProviderService {
    List<Provider> getAllProviders();

    List<Provider> getCashProviders();

    List<Provider> getCardProviders();

    <T> Provider getProvider(int id, Class<T> clazz);

    <T> Provider saveProvider(T provider);
}
