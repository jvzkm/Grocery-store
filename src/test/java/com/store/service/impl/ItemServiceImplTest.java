package com.store.service.impl;

import com.store.dao.ItemRepository;
import com.store.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.store.util.constants.classes.ItemTestConstants.item1;
import static com.store.util.constants.classes.ItemTestConstants.items;
import static com.store.util.constants.TestConstants.ID_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    ItemRepository repository;

    @InjectMocks
    ItemServiceImpl service;

    @Test
    void getItems() {
        var expected = items;

        when(repository.findAll()).thenReturn(items);
        var actual = service.getItems();

        assertEquals(expected, actual);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetItemById() {
        var expected = item1;

        when(repository.findById(ID_ONE)).thenReturn(Optional.ofNullable(item1));
        var actual = service.getItemById(ID_ONE);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(repository);

    }

    @Test
    void shouldNotGetItemById() {
        when(repository.findById(ID_ONE)).thenThrow(new ItemNotFoundException());

        assertThrows(ItemNotFoundException.class, () -> {
            service.getItemById(ID_ONE);
        });

        verifyNoMoreInteractions(repository);
    }

    @Test
    void saveItem() {
        var expected = item1;

        when(repository.save(item1)).thenReturn(item1);
        var actual = service.saveItem(item1);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(repository);
    }
}