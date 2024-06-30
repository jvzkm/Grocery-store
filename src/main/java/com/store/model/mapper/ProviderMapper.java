package com.store.model.mapper;

import com.store.dao.BankAccountRepository;
import com.store.model.dto.provider.ProviderRequestDto;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProviderMapper {

    @Autowired
    protected BankAccountRepository repository;

    public abstract CashProvider toCashProvider(ProviderRequestDto providerDTO);

    @Mapping(target = "bankAccount", expression = "java(repository.findById(providerDTO.getBankAccountId()).get())")
    public abstract CardProvider toCardProvider(ProviderRequestDto providerDTO);

    public abstract ProviderRequestDto toProviderDTO(CashProvider cashProvider);

    public abstract ProviderRequestDto toProviderDTO(CardProvider cardProvider);
}
