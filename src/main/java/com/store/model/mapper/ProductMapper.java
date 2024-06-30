package com.store.model.mapper;

import com.store.model.dto.product.ProductRequestDto;
import com.store.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product mapToProduct(ProductRequestDto requestDto);
}
