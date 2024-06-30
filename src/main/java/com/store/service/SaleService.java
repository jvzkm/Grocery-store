package com.store.service;

import com.store.model.entity.Sale;
import jakarta.transaction.Transactional;

public interface SaleService {
    @Transactional
    Sale createSale(Sale sale);
}
