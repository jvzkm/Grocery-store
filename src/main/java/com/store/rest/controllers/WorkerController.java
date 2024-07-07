package com.store.rest.controllers;

import com.store.model.dto.sale.SaleRequestDto;
import com.store.model.dto.sale.SaleResponseDto;
import com.store.model.dto.worker.WorkerRequestDto;
import com.store.model.dto.worker.WorkerResposeDto;
import com.store.model.entity.Item;
import com.store.model.entity.Sale;
import com.store.model.entity.Worker;
import com.store.model.mapper.SaleMapper;
import com.store.model.mapper.WorkerMapper;
import com.store.service.SaleService;
import com.store.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conf-service/workers")
public class WorkerController {

    private final WorkerService workerService;
    private final SaleService saleService;
    private final WorkerMapper mapper;
    private final SaleMapper saleMapper;

    @GetMapping
    @ResponseStatus(OK)
    public List<WorkerResposeDto> getAllWorkers() {
        return workerService.getWorkers()
                .stream()
                .map(mapper::workerToWorkerResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public WorkerResposeDto getWorker(@PathVariable int id) {
        return mapper.workerToWorkerResponseDto(workerService.getWorkerById(id));
    }

    @PostMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public WorkerResposeDto save(@RequestBody WorkerRequestDto workerRequestDto) {
        var worker = workerService.saveWorker(
                mapper.workerRequestDtoToWorker(workerRequestDto));
        return mapper.workerToWorkerResponseDto(worker);
    }

    @PostMapping("/{id}")
    @ResponseStatus(OK)
    public SaleResponseDto createSale(
            @PathVariable int id,
            @RequestBody SaleRequestDto saleRequestDto) {
        var sale = saleMapper.toSale(saleRequestDto);
        Worker workerById = workerService.getWorkerById(id);
        sale.setWorker(workerById);
        sale.setStoreBankAccount(workerById.getStore().getBankAccount());
        sale.setSum(sale.getItems().stream().mapToDouble(Item::getPrice).sum());
        return saleMapper.toResponseDto(saleService.createSale(sale));
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable int id,
                       @RequestBody WorkerRequestDto workerRequestDto) {
        var worker = workerService.getWorkerById(id);
        var updated = mapper.workerRequestDtoToWorker(workerRequestDto);
        updated.setId(worker.getId());

        workerService.saveWorker(updated);
    }

}
