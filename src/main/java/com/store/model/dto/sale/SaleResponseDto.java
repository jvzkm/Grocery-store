package com.store.model.dto.sale;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDto {

    private int id;

    @NotNull
    private int workerId;

    @NotNull
    private double sum;

}
