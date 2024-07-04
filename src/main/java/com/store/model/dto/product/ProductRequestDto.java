package com.store.model.dto.product;

import com.store.model.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotNull
    private String name;

    @NotNull
    private Category category;

    @NotNull
    private double price;
}
