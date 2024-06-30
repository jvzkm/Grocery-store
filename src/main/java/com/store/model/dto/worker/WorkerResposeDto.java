package com.store.model.dto.worker;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkerResposeDto {
    @NotNull
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private double salary;
}
