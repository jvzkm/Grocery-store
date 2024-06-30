package com.store.service;

import com.store.model.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems();

    Item getItemById(int id);

    Item saveItem(Item item);
}
