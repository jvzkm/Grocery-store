package com.store.model.dto.account;

import com.store.model.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BankAccountRequestDto {
    @NotNull
    private String pin;

    @NotNull
    private double amount;

    @NotNull
    private Status status;

    @NotNull
    private int bankId;
}
