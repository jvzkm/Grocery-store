package com.store.util.constants.classes;

import com.store.model.entity.Store;

import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;
import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.classes.BankTestConstants.account3;
import static com.store.util.constants.classes.BankTestConstants.account4;

public class StoreTestConstants {
    public static final Store store1 =
            Store.builder()
                    .id(ID_ONE)
                    .bankAccount(account1)
                    .build();

    public static final Store store2 =
            Store.builder()
                    .id(ID_TWO)
                    .bankAccount(account3)
                    .build();
    public static final Store store3 =
            Store.builder()
                    .id(ID_THREE)
                    .bankAccount(account4)
                    .build();


}
