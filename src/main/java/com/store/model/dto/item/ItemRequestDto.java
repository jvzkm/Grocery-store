package com.store.model.dto.item;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemRequestDto {
    @NotNull
    private int productId;

    @NotNull
    private int storeId;

    @NotNull
    private double price;

    @NotNull
    private LocalDate prodDate;

    @NotNull
    private LocalDate expDate;
}
