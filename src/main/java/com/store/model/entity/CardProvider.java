package com.store.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "t_cardProvider")
public class CardProvider implements Provider {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NotNull
    private String name;

    @OneToOne
    @JoinColumn(name = "bankAccount_id",
                referencedColumnName = "id")
    @NotNull
    private BankAccount bankAccount;
}
