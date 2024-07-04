package com.store.service.impl;

import com.store.dao.CardContractRepository;
import com.store.dao.CashContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.store.util.constants.classes.ContractTestConstants.cardContract1;
import static com.store.util.constants.classes.ContractTestConstants.cardTransaction1;
import static com.store.util.constants.classes.ContractTestConstants.cashContract1;
import static com.store.util.constants.classes.ContractTestConstants.cashTransaction1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {
    @Mock
    private CardContractRepository cardContractRepository;
    @Mock
    private CashContractRepository cashContractRepository;
    @Mock
    private TransactionServiceImpl transctionService;
    @InjectMocks
    private ContractServiceImpl service;


    @Test
    void createCardContract() {
        var expected = cardContract1;

        when(transctionService.createCardTransaction(cardContract1)).thenReturn(cardTransaction1);
        when(cardContractRepository.save(cardContract1)).thenReturn(cardContract1);
        var actual = service.createCardContract(cardContract1);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(cashContractRepository);
    }

    @Test
    void createCashContract() {
        var expected = cashContract1;

        when(transctionService.createCashTransaction(cashContract1)).thenReturn(cashTransaction1);
        when(cashContractRepository.save(cashContract1)).thenReturn(cashContract1);
        var actual = service.createCashContract(cashContract1);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(cardContractRepository);
    }
}