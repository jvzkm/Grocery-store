package com.store.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "t_cashTransaction")
public class CashTransaction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @OneToOne(mappedBy = "cashTransaction")
    @NotNull
    private CashContract cashContract;

    @ManyToOne
    @NotNull
    private BankAccount receivingBankAccount;

    @NotNull
    private double sum;
}