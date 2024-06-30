package com.store.model.dto.sale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SaleRequestDto {
    @NotNull
    private List<Integer> itemsId;

}
