package com.store.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "t_provider")
public class CashProvider implements Provider {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NotNull
    private String name;
}
