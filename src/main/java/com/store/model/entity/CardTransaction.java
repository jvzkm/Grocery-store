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
@Table(name = "t_cardTransaction")
public class CardTransaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @OneToOne(mappedBy = "cardTransaction")
    @NotNull
    private CardContract cardContract;

    @ManyToOne
    @NotNull
    private BankAccount givingBankAccount;

    @ManyToOne
    @NotNull
    private BankAccount receivingBankAccount;

    @NotNull
    private double sum;
}
