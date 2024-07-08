package com.store.model.dto.account;

import com.store.model.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class BankAccountRequestDto {

    private String pin;

    private double amount;

    private Status status;

    private int bankId;
}
