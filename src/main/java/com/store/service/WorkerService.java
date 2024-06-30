package com.store.service;

import com.store.model.entity.Worker;

import java.util.List;

public interface WorkerService {
    List<Worker> getWorkers();

    Worker getWorkerById(int id);

    Worker saveWorker(Worker worker);
}
