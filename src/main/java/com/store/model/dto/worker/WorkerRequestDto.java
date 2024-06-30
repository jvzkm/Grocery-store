package com.store.model.dto.worker;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkerRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private double salary;

    @NotNull
    private int jobId;

    @NotNull
    private int storeId;
}
