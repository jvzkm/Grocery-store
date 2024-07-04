package com.store.model.dto.item;

import com.store.model.entity.Condition;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
