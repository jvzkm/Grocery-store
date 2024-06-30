package com.store.rest.filters;


import com.store.dao.ItemRepository;
import com.store.model.entity.Item;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;

import static com.store.model.entity.Condition.EXPIRED;

@RequiredArgsConstructor
public class FoodExpirationFilter extends OncePerRequestFilter {

    private final ItemRepository itemRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        itemRepository.findAll().forEach(this::updateCondition);
        itemRepository.findAll().forEach(Item::applyExpirationDiscountIfEligible);


        filterChain.doFilter(request,response);

    }

    public void updateCondition(Item item) {
        if(item.getExpDate().isBefore(LocalDate.now())){
            item.setItemCondition(EXPIRED);
        }
    }
}
