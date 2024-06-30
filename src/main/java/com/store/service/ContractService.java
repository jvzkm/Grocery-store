package com.store.service;

import com.store.model.entity.CardContract;
import com.store.model.entity.CashContract;

public interface ContractService {

    CardContract createCardContract(CardContract cardContract);

    CashContract createCashContract(CashContract cashContract);
}
