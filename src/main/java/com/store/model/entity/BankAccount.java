package com.store.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.exceptions.InsuficientFundsException;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "t_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NotNull
    private String pin;

    @NotNull
    private double amount;

    @Enumerated(value = STRING)
    @NotNull
    private Status status;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @NotNull
    private Bank bank;

    @OneToOne(mappedBy = "bankAccount")
    @JsonIgnore
    private CardProvider person;

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new InsuficientFundsException();
        } else {
            this.amount = amount;
        }
    }
}
