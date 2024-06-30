package com.store.model.mapper;

import com.store.dao.ItemRepository;
import com.store.model.dto.sale.SaleRequestDto;
import com.store.model.dto.sale.SaleResponseDto;
import com.store.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SaleMapper {

    @Autowired
    protected ItemRepository itemRepository;

    @Mapping(target = "items", expression = "java(itemRepository.findAllById(requestDto.getItemsId()))")
    public abstract Sale toSale(SaleRequestDto requestDto);

    @Mapping(target = "workerId" , expression = "java(sale.getWorker().getId())")
    public abstract SaleResponseDto toResponseDto(Sale sale);

}
