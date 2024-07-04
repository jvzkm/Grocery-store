package com.store.util.constants;

import com.store.model.entity.BankAccount;
import com.store.model.entity.CardProvider;
import com.store.model.entity.CashProvider;
import com.store.model.entity.Discount;
import com.store.model.entity.Provider;
import com.store.model.entity.Worker;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.constants.classes.WorkerTestConstants.worker1;
import static com.store.util.constants.classes.WorkerTestConstants.worker2;

@UtilityClass
public  class TestConstants {
    public static final String APP_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String MALFORMED = "}{";
    public static final String EMPTY = "{}";

    public static final List<BankAccount> bankAccounts = new ArrayList<>();
    public static final List<Discount> discounts = new ArrayList<>();
    public static final List<Worker> workers = List.of(worker1, worker2);
    public static final List<CashProvider> cashProviders = new ArrayList<>();
    public static final List<CardProvider> cardProviders = new ArrayList<>();
    public static final List<Provider> providers = new ArrayList<>();


    public static final int ID_ONE = 1;
    public static final int ID_TWO = 2;
    public static final int ID_THREE = 3;
    public static final int ID_FOUR = 4;
    public static final int ID_FIVE = 5;

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int ERROR = 500;

    public static final String RANDOM = "random";




}
