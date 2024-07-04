package com.store.service.impl;

import com.store.dao.CardProviderRepository;
import com.store.dao.CashProviderRepository;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.CASH;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final CardProviderRepository cardProviderRepository;
    private final CashProviderRepository cashProviderRepository;
    private final ProviderMapper providerMapper;
    private final ContractService contractService;
    private final ContractMapper mapper;

    @Override
    public List<Provider> getAllProviders() {
        List<Provider> providers = new ArrayList<>(cardProviderRepository.findAll());
        providers.addAll(cashProviderRepository.findAll());
        return providers;
    }

    @Override
    public List<Provider> getCashProviders() {
        return new ArrayList<>(cashProviderRepository.findAll());
    }

    @Override
    public List<Provider> getCardProviders() {
        return new ArrayList<>(cardProviderRepository.findAll());
    }

    @Override
    public <T> Provider getProvider(int id, Class<T> clazz) {
        if (clazz == CashProvider.class) {
            return cashProviderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("CashProvider not found"));
        } else if (clazz == CardProvider.class) {
            return cardProviderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("CardProvider not found"));
        } else {
            throw new IllegalArgumentException("Unsupported provider type: " + clazz.getName());
        }
    }

    @Override
    public <T> Provider saveProvider(T provider) {
        if (provider instanceof CashProvider prov) {
            return cashProviderRepository.save(prov);
        } else if (provider instanceof CardProvider prov) {
            return cardProviderRepository.save(prov);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Provider> getProvidersByType(String type) {
        if (type == null) {
            return getAllProviders();
        } else if (type.equals(CARD)) {
            return getCardProviders();
        } else if (type.equals(CASH)) {
            return getCashProviders();
        } else {
            throw new RequestHeaderException();
        }
    }

    @Override
    public Provider getProviderByType(int id, String type) {
        if (CARD.equals(type)) {
            return getProvider(id, CardProvider.class);
        } else if (CASH.equals(type)) {
            return getProvider(id, CashProvider.class);
        } else {
            throw new RequestHeaderException();
        }
    }

    @Override
    public Provider saveProviderByType(ProviderRequestDto providerDTO, String type) {
        if (CARD.equals(type)) {
            CardProvider cardProvider = providerMapper.toCardProvider(providerDTO);
            return saveProvider(cardProvider);
        } else if (CASH.equals(type)) {
            CashProvider cashProvider = providerMapper.toCashProvider(providerDTO);
            return saveProvider(cashProvider);
        } else {
            throw new RequestHeaderException();
        }
    }

    @Override
    public void createContractByType(int id, String type, ContractRequestDto requestDto) {
        if (CARD.equals(type)) {
            var contract = mapper.contractRequestDtoToCardContract(requestDto);
            contract.setProvider((CardProvider) getProvider(id, CardProvider.class));
            contractService.createCardContract(contract);
        } else if (CASH.equals(type)) {
            var contract = mapper.contractRequestDtoToCashContract(requestDto);
            contract.setProvider((CashProvider) getProvider(id, CashProvider.class));
            contractService.createCashContract(contract);
        } else {
            throw new RequestHeaderException();
        }
    }

}
