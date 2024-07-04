package com.store.service.impl;

import com.store.dao.CardProviderRepository;
import com.store.dao.CashProviderRepository;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.store.util.constants.classes.ProviderTestConstants.cardProvider1;
import static com.store.util.constants.classes.ProviderTestConstants.cashProvider1;
import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.cardProviders;
import static com.store.util.constants.TestConstants.cashProviders;
import static com.store.util.constants.TestConstants.providers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {
    @Mock
    private CardProviderRepository cardProviderRepository;
    @Mock
    private CashProviderRepository cashProviderRepository;
    @InjectMocks
    private ProviderServiceImpl service;


    @Test
    void getAllProviders() {
        var expected = providers;

        when(cardProviderRepository.findAll()).thenReturn(cardProviders);
        when(cashProviderRepository.findAll()).thenReturn(cashProviders);
        var actual = service.getAllProviders();

        assertEquals(expected, actual);
        verifyNoMoreInteractions(cardProviderRepository, cashProviderRepository);


    }

    @Test
    void getCashProviders() {
        var expected = cashProviders;

        when(cashProviderRepository.findAll()).thenReturn(cashProviders);
        var actual = service.getCashProviders();

        assertEquals(expected, actual);
        verifyNoInteractions(cardProviderRepository);


    }

    @Test
    void getCardProviders() {
        var expected = cardProviders;

        when(cardProviderRepository.findAll()).thenReturn(cardProviders);
        var actual = service.getCardProviders();

        assertEquals(expected, actual);
        verifyNoInteractions(cashProviderRepository);


    }

    @Test
    void getCashProvider() {
        var expected = cashProvider1;

        when(cashProviderRepository.findById(ID_ONE)).thenReturn(Optional.of(cashProvider1));
        var actual = service.getProvider(ID_ONE, CashProvider.class);

        assertEquals(expected, actual);
        verifyNoInteractions(cardProviderRepository);
    }

    @Test
    void getCashProviderButNotFound() {
        when(cashProviderRepository.findById(ID_ONE)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
                () -> service.getProvider(ID_ONE, CashProvider.class)
        );
        verifyNoInteractions(cardProviderRepository);
    }

    @Test
    void getCardProvider() {
        var expected = cardProvider1;

        when(cardProviderRepository.findById(ID_ONE)).thenReturn(Optional.of(cardProvider1));
        var actual = service.getProvider(ID_ONE, CardProvider.class);

        assertEquals(expected, actual);
        verifyNoInteractions(cashProviderRepository);
    }

    @Test
    void getCardProviderButNotFound() {
        when(cardProviderRepository.findById(ID_ONE)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
                () -> service.getProvider(ID_ONE, CardProvider.class)
        );
        verifyNoInteractions(cashProviderRepository);
    }

    @Test
    void getNoProvider() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getProvider(ID_ONE, Exception.class)
        );
        verifyNoInteractions(cashProviderRepository,cardProviderRepository);
    }

    @Test
    void saveCashProvider() {
        var expected = cashProvider1;

        when(cashProviderRepository.save(cashProvider1)).thenReturn(cashProvider1);
        var actual = (CashProvider) service.saveProvider(cashProvider1);

        assertEquals(expected,actual);
        verifyNoInteractions(cardProviderRepository);
    }

    @Test
    void saveCardProvider() {
        var expected = cardProvider1;

        when(cardProviderRepository.save(cardProvider1)).thenReturn(cardProvider1);
        var actual = (CardProvider) service.saveProvider(cardProvider1);

        assertEquals(expected,actual);
        verifyNoInteractions(cashProviderRepository);
    }

    @Test
    void saveNoProvider() {
        assertThrows(IllegalArgumentException.class,
                () -> service.saveProvider(null)
        );
        verifyNoInteractions(cashProviderRepository,cardProviderRepository);
    }
}