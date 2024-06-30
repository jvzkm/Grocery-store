package com.store.model.dto.sale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleResponseDto {

    private int id;

    @NotNull
    private int workerId;

    @NotNull
    private double sum;

}
