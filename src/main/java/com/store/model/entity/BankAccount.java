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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NotNull
    @Length(min = 4,max = 4)
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
    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", pin='" + pin + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", bank=" + bank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount that)) return false;
        return id == that.id && Double.compare(amount, that.amount) == 0 && Objects.equals(pin, that.pin) && status == that.status && Objects.equals(bank, that.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pin, amount, status, bank);
    }
}
