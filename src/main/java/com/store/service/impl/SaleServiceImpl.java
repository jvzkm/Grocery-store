package com.store.service.impl;

import com.store.dao.SaleRepository;
import com.store.dao.WorkerRepository;
import com.store.exceptions.IllegalSaleException;
import com.store.model.entity.Item;
import com.store.model.entity.Sale;
import com.store.model.entity.Worker;
import com.store.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static com.store.model.entity.Condition.NEW;
import static com.store.model.entity.Condition.SOLD;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final WorkerRepository workerRepository;

    @Override
    @Transactional
    public Sale createSale(Sale sale) {
        validateSale(sale);
        sale.getItems().forEach(i -> i.setItemCondition(SOLD));
        sale.getStoreBankAccount().setAmount(sale.getStoreBankAccount().getAmount() + sale.getSum());
        Sale saved = saleRepository.save(sale);
        workerRepository.findAll().forEach(this::updateSalary);
        return saved;
    }

    private static void validateSale(Sale sale) {
        boolean ok = sale.getItems().stream()
                .map(Item::getItemCondition)
                .allMatch(it -> it.equals(NEW));

        if (!ok) throw new IllegalSaleException();
    }

    private void updateSalary(Worker worker) {
        if (worker.getJob().getName().equals("Manager")) {
            worker.setSalary(
                    calculateSale(s -> s.getStoreBankAccount().equals(worker.getStore().getBankAccount()))
                            * worker.getJob().getPercentage());

        } else {
            worker.setSalary(
                    calculateSale(s -> s.getWorker().equals(worker))
                            * worker.getJob().getPercentage());
        }
        workerRepository.save(worker);
    }

    private double calculateSale(Predicate<Sale> salePredicate) {
        return saleRepository
                .findAll().
                stream().
                filter(salePredicate)
                .mapToDouble(Sale::getSum).sum();
    }

}
