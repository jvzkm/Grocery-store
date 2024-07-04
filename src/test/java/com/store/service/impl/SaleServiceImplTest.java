package com.store.service.impl;

import com.store.dao.SaleRepository;
import com.store.dao.WorkerRepository;
import com.store.exceptions.IllegalSaleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.store.util.constants.TestConstants.workers;
import static com.store.util.constants.classes.SaleTestConstants.sale1;
import static com.store.util.constants.classes.SaleTestConstants.sale2;
import static com.store.util.constants.classes.WorkerTestConstants.worker1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private SaleServiceImpl service;

    @Test
    void createSuccessfulSale() {
        var expected = sale1;

        when(saleRepository.save(sale1)).thenReturn(sale1);
        when(workerRepository.findAll()).thenReturn(workers);
        when(saleRepository.findAll()).thenReturn(List.of(sale1));
        when(workerRepository.save(worker1)).thenReturn(worker1);

        var actual = service.createSale(sale1);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(saleRepository);
    }

    @Test
    void shouldFailToSaveSale() {
        assertThrows(IllegalSaleException.class, () -> {
            service.createSale(sale2);
        });
        verifyNoMoreInteractions(saleRepository, workerRepository);
    }
}