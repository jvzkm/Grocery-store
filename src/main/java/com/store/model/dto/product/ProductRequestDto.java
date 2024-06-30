package com.store.model.dto.product;

import com.store.model.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotNull
    private String name;

    @NotNull
    private Category category;

    @NotNull
    private double price;
}
