package com.store.rest.controllers;

import com.store.exceptions.RequestHeaderException;
import com.store.model.dto.contract.ContractRequestDto;
import com.store.model.dto.provider.ProviderRequestDto;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import com.store.model.entity.Provider;
import com.store.model.mapper.ContractMapper;
import com.store.model.mapper.ProviderMapper;
import com.store.service.ContractService;
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

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.CASH;
import static com.store.util.ProviderConstants.TYPE;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("/conf-providerService/provider")
public class ProviderController {

    private final ProviderService providerService;
    private final ContractService contractService;
    private final ProviderMapper providerMapper;
    private final ContractMapper mapper;

    @GetMapping
    @ResponseStatus(OK)
    public List<Provider> getAllProviders(
            @RequestHeader(value = TYPE, required = false) String type) {
        if (type == null) {
            return providerService.getAllProviders();
        } else if (type.equals(CARD)) {
            return providerService.getCardProviders();
        } else if (type.equals(CASH)) {
            return providerService.getCashProviders();
        } else {
            throw new RequestHeaderException();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Provider getProvider(
            @PathVariable int id,
            @RequestHeader(TYPE) String type) {
        if (CARD.equals(type)) {
            return providerService.getProvider(id, CardProvider.class);
        } else if (CASH.equals(type)) {
            return providerService.getProvider(id, CashProvider.class);
        } else {
            throw new RequestHeaderException();
        }
    }

    @PostMapping
    @ResponseStatus(OK)
    public Provider addProvider(
            @RequestBody ProviderRequestDto providerDTO,
            @RequestHeader(TYPE) String type) {
        if (CARD.equals(type)) {
            CardProvider cardProvider = providerMapper.toCardProvider(providerDTO);
            return providerService.saveProvider(cardProvider);
        } else if (CASH.equals(type)) {
            CashProvider cashProvider = providerMapper.toCashProvider(providerDTO);
            return providerService.saveProvider(cashProvider);
        } else {
            throw new RequestHeaderException();
        }
    }

    @PostMapping("/{id}")
    @ResponseStatus(OK)
    public void createContract(
            @PathVariable int id,
            @RequestHeader(TYPE) String type,
            @RequestBody ContractRequestDto requestDto) {
        if (CARD.equals(type)) {
            var contract = mapper.contractRequestDtoToCardContract(requestDto);
            contract.setProvider((CardProvider) getProvider(id, type));
            contractService.createCardContract(contract);
        } else if (CASH.equals(type)) {
            var contract = mapper.contractRequestDtoToCashContract(requestDto);
            contract.setProvider((CashProvider) getProvider(id, type));
            contractService.createCashContract(contract);
        } else {
            throw new RequestHeaderException();
        }
    }


}
