package com.store.service.impl;

import com.store.dao.WorkerRepository;
import com.store.exceptions.WorkerNotFoundException;
import com.store.model.entity.Worker;
import com.store.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    public List<Worker> getWorkers(){
        return workerRepository.findAll()
                .stream()
                .sorted(comparing(Worker::getName))
                .toList();
    }
    @Override
    public Worker getWorkerById(int id){
        return workerRepository.findById(id).orElseThrow(WorkerNotFoundException::new);
    }
    @Override
    public Worker saveWorker(Worker worker){
        return workerRepository.save(worker);
    }
}
