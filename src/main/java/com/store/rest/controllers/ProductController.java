package com.store.rest.controllers;

import com.store.dao.ProductRepository;
import com.store.model.dto.product.ProductRequestDto;
import com.store.model.entity.Product;
import com.store.model.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conf-service/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @GetMapping
    @ResponseStatus(OK)
    public List<Product> getAllProducts() {
        return repository.findAll();
    }


    @GetMapping("/{name}")
    @ResponseStatus(OK)
    public List<Product> getAllProductsByName(
            @PathVariable String name) {
        return repository.findAllByName(name);
    }

    @PostMapping
    @ResponseStatus(OK)
    public Product addProduct(
            @RequestBody ProductRequestDto product) {
        return repository.save(mapper.mapToProduct(product));
    }
}
