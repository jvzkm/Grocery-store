package com.store.model.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "sale_items", joinColumns = @JoinColumn(name = "sale_id"))
    @Column(name = "item_id")
    @NotNull
    private List<Item> items;

    @ManyToOne
    @NotNull
    private BankAccount storeBankAccount;

    @ManyToOne
    @NotNull
    private Worker worker;

    @NotNull
    private double sum;


}
