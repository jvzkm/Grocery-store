package com.store.model.entity;

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

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private double salary;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @NotNull
    private Job job;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @NotNull
    private Store store;

}
