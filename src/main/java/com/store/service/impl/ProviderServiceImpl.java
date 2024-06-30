package com.store.service.impl;

import com.store.dao.CardProviderRepository;
import com.store.dao.CashProviderRepository;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import com.store.model.entity.Provider;
import com.store.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final CardProviderRepository cardProviderRepository;
    private final CashProviderRepository cashProviderRepository;

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

}
