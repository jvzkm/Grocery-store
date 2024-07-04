package com.store.model.dto.worker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
