package com.store.model.mapper;

import com.store.dao.BankRepository;
import com.store.model.dto.account.BankAccountRequestDto;
import com.store.model.dto.account.BankAccountResponseDto;
import com.store.model.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BankAccountMapper {

    @Autowired
    protected BankRepository repository;

    @Mapping(target = "bank", expression = "java(repository.findById(requestDto.getBankId()).get())")
    public abstract BankAccount toCardProvider(BankAccountRequestDto requestDto);

    public abstract BankAccountResponseDto toBankAccountResponseDto(BankAccount account);


}
