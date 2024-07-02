package com.store.service;

import com.store.model.entity.CardContract;
import com.store.model.entity.CardTransaction;
import com.store.model.entity.CashContract;
import com.store.model.entity.CashTransaction;

public interface TransactionService {
    CardTransaction createCardTransaction(CardContract contract);

    CashTransaction createCashTransaction(CashContract contract);
}
