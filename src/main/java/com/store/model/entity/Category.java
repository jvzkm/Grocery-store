package com.store.model.entity;

import lombok.Getter;

public enum Category {
    FRUIT(0.5),
    VEGETABLE(0.6),
    MEAT(0.2),
    DAIRY(0.8),
    GRAIN(0.1),
    SEAFOOD(0.2),
    SNACK(0.1),
    DESSERT(0.4),
    BEVERAGE(0.1),
    SAUCE(0.3),
    SPICE(0.1);

    @Getter
    private final double expirationTax;

    Category(double expirationTax) {
        this.expirationTax = expirationTax;
    }
}
