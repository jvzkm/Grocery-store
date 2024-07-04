package com.store.util.constants.classes;

import com.store.model.dto.sale.SaleRequestDto;
import com.store.model.dto.sale.SaleResponseDto;
import com.store.model.entity.Item;
import com.store.model.entity.Sale;

import java.util.List;

import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;
import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.classes.ItemTestConstants.items;
import static com.store.util.constants.classes.ItemTestConstants.itemsSold;
import static com.store.util.constants.classes.WorkerTestConstants.worker1;

public class SaleTestConstants {

    public static final Sale sale1 =
            Sale.builder()
                    .id(ID_ONE)
                    .items(items)
                    .storeBankAccount(account1)
                    .worker(worker1)
                    .sum(100)
                    .build();

    public static final Sale sale2 =
            Sale.builder()
                    .id(ID_TWO)
                    .items(itemsSold)
                    .storeBankAccount(account1)
                    .worker(worker1)
                    .sum(100)
                    .build();

    public static final SaleRequestDto saleRequest1 =
            SaleRequestDto.builder()
                    .itemsId(getList(items))
                    .build();

    public static final SaleRequestDto saleRequest2 =
            SaleRequestDto.builder()
                    .itemsId(getList(itemsSold))
                    .build();

    public static final SaleResponseDto saleResponseDto =
            SaleResponseDto.builder()
                    .id(ID_ONE)
                    .workerId(ID_THREE)
                    .sum(11.99)
                    .build();

    private static List<Integer> getList(List<Item> items) {
        List<Integer> list = items.stream().mapToInt(Item::getId).boxed().toList();
        return list;
    }
}
