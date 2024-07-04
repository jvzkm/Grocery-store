package com.store.util.constants.classes;

import com.store.model.dto.contract.ContractRequestDto;
import com.store.model.entity.CardContract;
import com.store.model.entity.CardTransaction;
import com.store.model.entity.CashContract;
import com.store.model.entity.CashTransaction;

import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;
import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.classes.BankTestConstants.account2;
import static com.store.util.constants.classes.ItemTestConstants.product1;
import static com.store.util.constants.classes.ProviderTestConstants.cardProvider1;
import static com.store.util.constants.classes.ProviderTestConstants.cardProvider2;
import static com.store.util.constants.classes.ProviderTestConstants.cashProvider1;
import static com.store.util.constants.classes.StoreTestConstants.store1;
import static com.store.util.constants.classes.StoreTestConstants.store2;
import static java.time.LocalDate.now;

public class ContractTestConstants {

    public static final CardContract cardContract1 =
            CardContract.builder()
                    .id(ID_ONE)
                    .product(product1)
                    .amount(5)
                    .provider(cardProvider1)
                    .signedDate(now())
                    .store(store1)
                    .build();

    public static final CardContract cardContract2 =
            CardContract.builder()
                    .id(ID_TWO)
                    .product(product1)
                    .amount(5)
                    .provider(cardProvider2)
                    .signedDate(now())
                    .store(store1)
                    .build();


    public static final CardTransaction cardTransaction1 =
            CardTransaction.builder()
                    .id(ID_ONE)
                    .givingBankAccount(account2)
                    .receivingBankAccount(account1)
                    .sum(1000)
                    .build();

    public static final CashContract cashContract1 =
            CashContract.builder()
                    .id(ID_ONE)
                    .product(product1)
                    .amount(5)
                    .provider(cashProvider1)
                    .signedDate(now())
                    .store(store1)
                    .build();

    public static final CashContract cashContract2 =
            CashContract.builder()
                    .id(ID_TWO)
                    .product(product1)
                    .amount(5)
                    .provider(cashProvider1)
                    .signedDate(now())
                    .store(store2)
                    .build();

    public static final CashTransaction cashTransaction1 =
            CashTransaction.builder()
                    .id(ID_ONE)
                    .receivingBankAccount(account1)
                    .sum(123.123)
                    .build();

    public static final ContractRequestDto requestDto1 =
            ContractRequestDto.builder()
                    .storeId(ID_ONE)
                    .productId(ID_ONE)
                    .amount(2)
                    .signedDate(now())
                    .build();

    public static final ContractRequestDto requestDto2 =
            ContractRequestDto.builder()
                    .storeId(ID_THREE)
                    .productId(ID_ONE)
                    .amount(5)
                    .signedDate(now())
                    .build();

    public static final ContractRequestDto requestDto3 =
            ContractRequestDto.builder()
                    .storeId(ID_ONE)
                    .productId(ID_ONE)
                    .amount(200)
                    .signedDate(now())
                    .build();



}
