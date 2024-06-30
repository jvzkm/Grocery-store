package com.store.model.mapper;

import com.store.dao.ProductRepository;
import com.store.dao.StoreRepository;
import com.store.model.dto.item.ItemRequestDto;
import com.store.model.dto.item.ItemResponseDto;
import com.store.model.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ProductRepository prodRepository;

    @Mapping(target = "productId", expression = "java(item.getProduct().getId())")
    @Mapping(target = "storeId", expression = "java(item.getStore().getId())")
    @Mapping(target = "itemCondition", source = "itemCondition")
    public abstract ItemResponseDto itemToItemResponseDto(Item item);

    @Mapping(target = "product", expression = "java(prodRepository.findById(itemRequestDto.getProductId()).get())")
    @Mapping(target = "store", expression = "java(storeRepository.findById(itemRequestDto.getStoreId()).get())")
    @Mapping(target = "price", source = "price")
    public abstract Item itemRequestDtoToItem(ItemRequestDto itemRequestDto);
}
