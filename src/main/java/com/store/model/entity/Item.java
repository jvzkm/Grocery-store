package com.store.model.entity;

import com.store.dao.DiscountRepository;
import com.store.dao.ItemRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.store.model.entity.Condition.DISCOUNTED;
import static com.store.model.entity.Condition.EXPIRED;
import static com.store.model.entity.Condition.SOLD;
import static com.store.model.entity.Type.EXPIRATION;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_item")
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @NotNull
    private Product product;

    @ManyToOne
    @NotNull
    private Store store;

    @NotNull
    private double price;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Condition itemCondition;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<Discount> discounts = new ArrayList<>();

    @NotNull
    private LocalDate prodDate;

    @NotNull
    private LocalDate expDate;

    public void applyExpirationDiscountIfEligible(ItemRepository repository, DiscountRepository discountRepository) {
        LocalDate today = LocalDate.now();
        if (today.plusDays(3).isAfter(expDate) && noneMatch()) {

            double discountPercentage = product.getCategory().getExpirationTax();
            double discountAmount = price * discountPercentage;
            price -= discountAmount;
            itemCondition = DISCOUNTED;

            Discount discount = new Discount();
            discount.setItem(this);
            discount.setDiscountPercentage(discountPercentage);
            discount.setDiscountAppliedDate(LocalDate.now());
            discount.setType(EXPIRATION);

            discountRepository.save(discount);

            repository.save(this);

        }
    }

    private boolean noneMatch() {
        return discounts.stream()
                .noneMatch(d -> d.getType().equals(EXPIRATION))
                && this.itemCondition != SOLD
                && this.itemCondition != EXPIRED;
    }
}
