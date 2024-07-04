package com.store.service.impl;

import com.store.dao.WorkerRepository;
import com.store.exceptions.WorkerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.workers;
import static com.store.util.constants.classes.WorkerTestConstants.worker1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest {

    @Mock
    private WorkerRepository repository;

    @InjectMocks
    private WorkerServiceImpl service;

    @Test
    void getAllWorkers() {
        var expected = workers;

        when(repository.findAll()).thenReturn(workers);
        var actual = service.getWorkers();

        assertEquals(expected, actual);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldFindWorkerById() {
        var expected = worker1;

        when(repository.findById(ID_ONE)).thenReturn(Optional.of(worker1));
        var actual = service.getWorkerById(ID_ONE);

        assertEquals(expected, actual);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldNotFindWorkerById() {
        when(repository.findById(ID_ONE)).thenThrow(new WorkerNotFoundException());

        assertThrows(WorkerNotFoundException.class,()->{
           service.getWorkerById(ID_ONE);
        });
        verifyNoMoreInteractions(repository);
    }

    @Test
    void saveWorker() {
        var expected = worker1;

        when(repository.save(worker1)).thenReturn(worker1);
        var actual = service.saveWorker(worker1);

        assertEquals(expected,actual);
        verifyNoMoreInteractions(repository);
    }
}