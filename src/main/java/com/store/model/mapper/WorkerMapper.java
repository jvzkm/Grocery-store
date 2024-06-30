package com.store.model.mapper;

import com.store.dao.JobRepository;
import com.store.dao.StoreRepository;
import com.store.model.dto.worker.WorkerRequestDto;
import com.store.model.dto.worker.WorkerResposeDto;
import com.store.model.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {

    @Autowired
    protected  StoreRepository storeRepository;
    @Autowired
    protected  JobRepository jobRepository;

    public abstract WorkerResposeDto workerToWorkerResponseDto(Worker worker);

    @Mapping(target = "job", expression = "java(jobRepository.findById(workerRequestDto.getJobId()).get())")
    @Mapping(target = "store", expression = "java(storeRepository.findById(workerRequestDto.getStoreId()).get())")
    public  abstract Worker workerRequestDtoToWorker(WorkerRequestDto workerRequestDto);
}
