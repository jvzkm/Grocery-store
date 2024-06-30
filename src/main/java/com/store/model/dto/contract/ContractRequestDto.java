package com.store.model.dto.contract;

import com.store.model.entity.Product;
import com.store.model.entity.Store;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractRequestDto {

    @NotNull
    private int storeId;

    @NotNull
    private int productId;

    @NotNull
    private int amount;

    @NotNull
    private LocalDate signedDate;

}
