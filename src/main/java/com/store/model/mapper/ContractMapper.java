package com.store.model.mapper;

import com.store.dao.ProductRepository;
import com.store.dao.StoreRepository;
import com.store.model.dto.contract.ContractRequestDto;
import com.store.model.entity.CardContract;
import com.store.model.entity.CashContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public  abstract class ContractMapper {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ProductRepository prodRepository;

    @Mapping(target = "product", expression = "java(prodRepository.findById(contractRequestDto.getProductId()).get())")
    @Mapping(target = "store", expression = "java(storeRepository.findById(contractRequestDto.getStoreId()).get())")
    public abstract CashContract contractRequestDtoToCashContract(ContractRequestDto contractRequestDto);
    @Mapping(target = "product", expression = "java(prodRepository.findById(contractRequestDto.getProductId()).get())")
    @Mapping(target = "store", expression = "java(storeRepository.findById(contractRequestDto.getStoreId()).get())")
    public abstract CardContract contractRequestDtoToCardContract(ContractRequestDto contractRequestDto);


}
