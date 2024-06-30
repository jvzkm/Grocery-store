package com.store.service.impl;

import com.store.dao.SaleRepository;
import com.store.exceptions.IllegalSaleException;
import com.store.model.entity.Item;
import com.store.model.entity.Sale;
import com.store.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.store.model.entity.Condition.NEW;
import static com.store.model.entity.Condition.SOLD;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Override
    @Transactional
    public Sale createSale(Sale sale) {
        validateSale(sale);
        sale.getItems().forEach(i -> i.setItemCondition(SOLD));
        sale.getStoreBankAccount().setAmount(sale.getStoreBankAccount().getAmount() + sale.getSum());
        return saleRepository.save(sale);
    }

    private static void validateSale(Sale sale) {
        boolean ok = sale.getItems().stream()
                .map(Item::getItemCondition)
                .allMatch(it -> it.equals(NEW));

        if (!ok) throw new IllegalSaleException();
    }

}
