package com.store.model.dto.item;

import com.store.model.entity.Condition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemResponseDto {
    @NotNull
    private  int id;

    @NotNull
    private int productId;

    @NotNull
    private int storeId;

    @NotNull
    private double price;

    @NotNull
    private Condition itemCondition;

}
