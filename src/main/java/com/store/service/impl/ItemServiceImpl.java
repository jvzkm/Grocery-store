package com.store.service.impl;

import com.store.dao.ItemRepository;
import com.store.exceptions.ItemNotFoundException;
import com.store.model.entity.Item;
import com.store.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll()
                .stream()
                .sorted(comparing(Item::getProdDate))
                .toList();
    }

    @Override
    public Item getItemById(int id) {
        return itemRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

}
