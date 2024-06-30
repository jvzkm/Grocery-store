package com.store.model.mapper;

import com.store.model.entity.CashContract;
import com.store.model.entity.CashTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CashContractMapper {

    @Mapping(target = "receivingBankAccount", source = "store.bankAccount")
    @Mapping(target = "sum", expression = "java(contract.getProduct().getPrice() * contract.getAmount())")
    CashTransaction mapToCashTransaction(CashContract contract) ;

}
