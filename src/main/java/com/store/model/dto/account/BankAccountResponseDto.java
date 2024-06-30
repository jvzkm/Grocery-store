package com.store.model.dto.account;

import com.store.model.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BankAccountResponseDto {
    @NotNull
    private int id;

    @NotNull
    private String pin;

    @NotNull
    private double amount;

    @NotNull
    private Status status;
}
