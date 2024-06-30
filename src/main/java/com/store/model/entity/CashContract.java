package com.store.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "t_cashContract")
public class CashContract {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @NotNull
    private CashProvider provider;

    @ManyToOne
    @NotNull
    private Store store;

    @ManyToOne
    @NotNull
    private Product product;

    @NotNull
    private int amount;

    @NotNull
    private LocalDate signedDate;

    @ManyToOne
    @NotNull
    private CashTransaction cashTransaction;
}
