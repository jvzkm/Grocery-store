package com.store.model.mapper;

import com.store.model.entity.CardContract;
import com.store.model.entity.CardTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardContractMapper {

    @Mapping(target = "givingBankAccount", source = "provider.bankAccount")
    @Mapping(target = "receivingBankAccount", source = "store.bankAccount")
    @Mapping(target = "sum", expression = "java(contract.getProduct().getPrice() * contract.getAmount())")
    public CardTransaction mapToCardTransaction(CardContract contract) ;

}
