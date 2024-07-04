package com.store.util.constants.classes;

import com.store.model.dto.item.ItemRequestDto;
import com.store.model.dto.item.ItemResponseDto;
import com.store.model.dto.product.ProductRequestDto;
import com.store.model.entity.Item;
import com.store.model.entity.Product;
import com.store.util.mapper.JsonUtil;

import java.util.List;

import static com.store.model.entity.Category.BEVERAGE;
import static com.store.model.entity.Category.SPICE;
import static com.store.model.entity.Condition.NEW;
import static com.store.model.entity.Condition.SOLD;
import static com.store.util.constants.classes.StoreTestConstants.store1;
import static com.store.util.constants.TestConstants.*;
import static java.time.LocalDate.now;

public class ItemTestConstants {

    public static final String PRODUCT_CHEFIR = "Chefir";
    public static final int ITEM_ONE = 1;

    public static final Product product1 =
            Product.builder()
                    .id(ID_ONE)
                    .category(BEVERAGE)
                    .name("Coca-Cola")
                    .price(20.5)
                    .build();

    public static final Product product2 =
            Product.builder()
                    .id(ID_FIVE)
                    .category(SPICE)
                    .name("NEW PROD")
                    .price(999)
                    .build();

    public static final ProductRequestDto prodRequest =
            ProductRequestDto.builder()
                    .name("NEW PROD")
                    .category(SPICE)
                    .price(999)
                    .build();


    public static Item item1 =
            Item.builder()
                    .id(ID_ONE)
                    .product(product1)
                    .store(store1)
                    .price(11.99)
                    .itemCondition(NEW)
                    .discounts(discounts)
                    .prodDate(now())
                    .expDate(now().plusWeeks(1))
                    .build();

    public static ItemResponseDto itemResponseDto1 =
            ItemResponseDto.builder()
                    .id(ID_THREE)
                    .price(1.99)
                    .itemCondition(NEW)
                    .productId(ID_ONE)
                    .storeId(ID_ONE)
                    .build();

    public static final ItemRequestDto itemRequestDto1 =
            ItemRequestDto.builder()
                    .productId(ID_ONE)
                    .price(1.99)
                    .storeId(ID_ONE)
                    .prodDate(now())
                    .expDate(now().plusWeeks(1))
                    .build();


    public static Item item2 =
            Item.builder()
                    .id(ID_TWO)
                    .product(product1)
                    .store(store1)
                    .price(22.41)
                    .itemCondition(SOLD)
                    .discounts(discounts)
                    .prodDate(now())
                    .expDate(now().plusWeeks(1))
                    .build();

    public static final List<Item> items = List.of(item1);
    public static final List<Item> itemsSold = List.of(item2);


}
