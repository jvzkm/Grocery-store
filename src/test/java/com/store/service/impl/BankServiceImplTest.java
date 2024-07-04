package com.store.service.impl;

import com.store.dao.BankAccountRepository;
import com.store.exceptions.BankAccountNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.bankAccounts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @Mock
    private BankAccountRepository accountRepository;

    @InjectMocks
    private BankServiceImpl service;

    @Test
    void getBankAllAccounts() {
        var expected = bankAccounts;

        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        var actual = service.getBankAllAccounts();

        assertEquals(expected, actual);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldGetValidBankAccount() {
        var expected = account1;

        when(accountRepository.findById(ID_ONE)).thenReturn(Optional.of(account1));
        var actual = service.getBankAllAccounts(1);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(accountRepository);

    }

    @Test
    void shouldNotFindValidBankAccount() {
        when(accountRepository.findById(ID_ONE)).thenThrow(new BankAccountNotFound());

        assertThrows(BankAccountNotFound.class, () -> {
            service.getBankAllAccounts(ID_ONE);
        });

        verifyNoMoreInteractions(accountRepository);

    }

    @Test
    void addBankAccount() {
        var expected = account1;

        when(accountRepository.save(account1)).thenReturn(account1);
        var actual = service.addBankAccount(account1);

        assertEquals(expected,actual);
    }



}