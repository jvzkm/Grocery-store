package com.store.util.constants.classes;

import com.store.model.dto.provider.ProviderRequestDto;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;

import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;
import static com.store.util.constants.classes.BankTestConstants.account2;
import static com.store.util.constants.classes.BankTestConstants.account3;
import static com.store.util.constants.classes.BankTestConstants.account4;

public class ProviderTestConstants {

    public static final int CASH_PROVIDER_1 = 1;
    public static final int CARD_PROVIDER_2 = 2;
    public static final int CARD_PROVIDER_3 = 3;

    public static final CardProvider cardProvider1 =
            CardProvider.builder()
                    .id(ID_ONE)
                    .bankAccount(account2)
                    .name("CARD PROVIDER1")
                    .build();

    public static final CardProvider cardProvider2 =
            CardProvider.builder()
                    .id(ID_TWO)
                    .bankAccount(account3)
                    .name("INC B")
                    .build();

    public static final CardProvider cardProvider3 =
            CardProvider.builder()
                    .id(ID_THREE)
                    .bankAccount(account4)
                    .name("INC C")
                    .build();

    public static final ProviderRequestDto cardProviderDto1 =
            ProviderRequestDto.builder()
                    .bankAccountId(ID_ONE)
                    .name("NEW PROV")
                    .build();

    public static final ProviderRequestDto cardProviderDto2 =
            ProviderRequestDto.builder()
                    .bankAccountId(ID_THREE)
                    .name("NEW PROV BAD BANK")
                    .build();


    public static final CashProvider cashProvider1 =
            CashProvider.builder()
                    .id(ID_ONE)
                    .name("Mr. A")
                    .build();

    public static final ProviderRequestDto cashProviderDto1 =
            ProviderRequestDto.builder()
                    .name("NEW PROV")
                    .build();

}
