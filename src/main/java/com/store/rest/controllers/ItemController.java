package com.store.rest.controllers;

import com.store.model.dto.item.ItemRequestDto;
import com.store.model.dto.item.ItemResponseDto;
import com.store.model.mapper.ItemMapper;
import com.store.service.ItemService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("conf-service/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper mapper;

    @GetMapping
    @ResponseStatus(OK)
    public List<ItemResponseDto> getAllWorkers() {
        return itemService.getItems().stream()
                .map(mapper::itemToItemResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ItemResponseDto getWorker(@PathVariable int id) {
        return mapper.itemToItemResponseDto(itemService.getItemById(id));
    }

    @PostMapping
    @ResponseStatus(OK)
    public ItemResponseDto save(@RequestBody ItemRequestDto itemRequestDto) {
        var item = itemService.saveItem(
                mapper.itemRequestDtoToItem(itemRequestDto));
        return mapper.itemToItemResponseDto(item);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public void update(@PathVariable int id,
                       @RequestBody ItemRequestDto itemRequestDto) {
        var item = itemService.getItemById(id);
        var updated = mapper.itemRequestDtoToItem(itemRequestDto);
        updated.setId(item.getId());

        itemService.saveItem(updated);

    }

}
