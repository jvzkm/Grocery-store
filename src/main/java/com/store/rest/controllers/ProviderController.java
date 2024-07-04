package com.store.rest.controllers;

import com.store.model.dto.contract.ContractRequestDto;
import com.store.model.dto.provider.ProviderRequestDto;
import com.store.model.entity.Provider;
import com.store.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.store.util.ProviderConstants.TYPE;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("/conf-providerService/provider")
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Provider> getAllProviders(
            @RequestHeader(value = TYPE, required = false) String type) {
        return providerService.getProvidersByType(type);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Provider getProvider(
            @PathVariable int id,
            @RequestHeader(TYPE) String type) {
        return providerService.getProviderByType(id, type);
    }


    @PostMapping
    @ResponseStatus(OK)
    public Provider addProvider(
            @RequestBody ProviderRequestDto providerDTO,
            @RequestHeader(TYPE) String type) {
        return providerService.saveProviderByType(providerDTO, type);
    }


    @PostMapping("/{id}")
    @ResponseStatus(OK)
    public void createContract(
            @PathVariable int id,
            @RequestHeader(TYPE) String type,
            @RequestBody ContractRequestDto requestDto) {
        providerService.createContractByType(id, type, requestDto);
    }


}
