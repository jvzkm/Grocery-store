package com.store.service;

import com.store.model.dto.contract.ContractRequestDto;
import com.store.model.dto.provider.ProviderRequestDto;
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

    List<Provider> getProvidersByType(String type);

    Provider getProviderByType(int id, String type);

    Provider saveProviderByType(ProviderRequestDto providerDTO, String type);

    void createContractByType(int id, String type, ContractRequestDto requestDto);
}
