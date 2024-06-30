package com.store.service.impl;

import com.store.dao.CardContractRepository;
import com.store.dao.CashContractRepository;
import com.store.model.entity.CardContract;
import com.store.model.entity.CashContract;
import com.store.service.ContractService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final CardContractRepository cardContractRepository;
    private final CashContractRepository cashContractRepository;
    private final TransactionServiceImpl transctionService;



    public CardContract createCardContract(CardContract cardContract) {
        var transaction = transctionService.createCardTransaction(cardContract);
        cardContract.setCardTransaction(transaction);
        return cardContractRepository.save(cardContract);
    }


    public CashContract createCashContract(CashContract cashContract) {
        var transaction = transctionService.createCashTransaction(cashContract);
        cashContract.setCashTransaction(transaction);
        return cashContractRepository.save(cashContract);
    }
}
