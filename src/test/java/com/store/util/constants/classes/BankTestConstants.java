package com.store.util.constants.classes;

import com.store.model.entity.Bank;
import com.store.model.entity.BankAccount;

import static com.store.model.entity.Status.ACTIVE;
import static com.store.model.entity.Status.INACTIVE;
import static com.store.util.constants.TestConstants.ID_FOUR;
import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;

public class BankTestConstants {

    public static final int ACCOUNT_ONE = 1;
    public static final int NOT_FND = 30;


    public static final Bank bank1 =
            Bank.builder()
                    .id(ID_ONE)
                    .name("Bank A")
                    .build();

    public static final Bank bank2 =
            Bank.builder()
                    .id(ID_TWO)
                    .name("Bank B")
                    .build();

    public static final Bank bank3 =
            Bank.builder()
                    .id(ID_THREE)
                    .name("Bank C")
                    .build();

    public static final BankAccount account1 =
            BankAccount.builder()
                    .id(ID_ONE)
                    .pin("1234")
                    .amount(100000.00)
                    .status(ACTIVE)
                    .bank(bank1)
                    .build();

    public static final BankAccount account2 =
            BankAccount.builder()
                    .id(ID_TWO)
                    .pin("2341")
                    .amount(5555.55)
                    .status(ACTIVE)
                    .bank(bank2)
                    .build();

    public static final BankAccount account3 =
            BankAccount.builder()
                    .id(ID_THREE)
                    .pin("4442")
                    .amount(100.32)
                    .status(ACTIVE)
                    .bank(bank1)
                    .build();

    public static final BankAccount account4 =
            BankAccount.builder()
                    .id(ID_FOUR)
                    .pin("6632")
                    .amount(4521.33)
                    .status(INACTIVE)
                    .bank(bank3)
                    .build();


}
