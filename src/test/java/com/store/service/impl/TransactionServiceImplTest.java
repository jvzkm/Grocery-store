package com.store.service.impl;

import com.store.dao.BankAccountRepository;
import com.store.dao.CardTransactionRepository;
import com.store.dao.CashTransactionRepository;
import com.store.dao.ItemRepository;
import com.store.exceptions.IllegalTransactionException;
import com.store.model.mapper.CardContractMapper;
import com.store.model.mapper.CashContractMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.classes.BankTestConstants.account2;
import static com.store.util.constants.classes.ContractTestConstants.cardContract1;
import static com.store.util.constants.classes.ContractTestConstants.cardContract2;
import static com.store.util.constants.classes.ContractTestConstants.cardTransaction1;
import static com.store.util.constants.classes.ContractTestConstants.cashContract1;
import static com.store.util.constants.classes.ContractTestConstants.cashContract2;
import static com.store.util.constants.classes.ContractTestConstants.cashTransaction1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private CardTransactionRepository cardTransactionRepository;
    @Mock
    private CashTransactionRepository cashTransactionRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private CardContractMapper cardContractMapper;
    @Mock
    private CashContractMapper cashContractMapper;

    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    void shouldCreateSuccessfulCardTransaction() {
        var expected = cardTransaction1;

        when(cardContractMapper.mapToCardTransaction(cardContract1)).thenReturn(cardTransaction1);
        when(bankAccountRepository.save(account1)).thenReturn(account1);
        when(bankAccountRepository.save(account2)).thenReturn(account2);
        when(itemRepository.save(any())).thenReturn(any());
        when(cardTransactionRepository.save(cardTransaction1)).thenReturn(cardTransaction1);
        var actual = service.createCardTransaction(cardContract1);

        assertEquals(expected, actual);
        verifyNoInteractions(cashTransactionRepository);
    }

    @Test
    void shouldNotCreateSuccessfulCardTransaction() {
        when(cardContractMapper.mapToCardTransaction(cardContract2)).thenReturn(any());

        assertThrows(IllegalTransactionException.class, () -> {
            service.createCardTransaction(cardContract2);
        });

        verifyNoInteractions(cashTransactionRepository);
    }

    @Test
    void shouldCreateSuccessfulCashTransaction() {
        var expected = cashTransaction1;

        when(cashContractMapper.mapToCashTransaction(cashContract1)).thenReturn(cashTransaction1);
        when(bankAccountRepository.save(account1)).thenReturn(account1);
        when(itemRepository.save(any())).thenReturn(any());
        when(cashTransactionRepository.save(cashTransaction1)).thenReturn(cashTransaction1);
        var actual = service.createCashTransaction(cashContract1);

        assertEquals(expected, actual);
        verifyNoInteractions(cardTransactionRepository);
    }

    @Test
    void shouldNotCreateSuccessfulCashTransaction() {
        when(cashContractMapper.mapToCashTransaction(cashContract2)).thenReturn(any());

        assertThrows(IllegalTransactionException.class, () -> {
            service.createCashTransaction(cashContract2);
        });

        verifyNoInteractions(cardTransactionRepository);
    }
}