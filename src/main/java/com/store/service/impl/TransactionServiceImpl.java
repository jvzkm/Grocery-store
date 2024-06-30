package com.store.service.impl;

import com.store.dao.BankAccountRepository;
import com.store.dao.CardTransactionRepository;
import com.store.dao.CashTransactionRepository;
import com.store.dao.ItemRepository;
import com.store.model.entity.CardContract;
import com.store.model.entity.CardTransaction;
import com.store.model.entity.CashContract;
import com.store.model.entity.CashTransaction;
import com.store.model.entity.Item;
import com.store.model.entity.Product;
import com.store.model.entity.Store;
import com.store.model.mapper.CardContractMapper;
import com.store.model.mapper.CashContractMapper;
import com.store.service.TransctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.store.model.entity.Condition.NEW;
import static com.store.util.TransactionConstants.EXP;
import static com.store.util.TransactionConstants.TAX;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransctionService {

    private final CardTransactionRepository cardTransactionRepository;
    private final CashTransactionRepository cashTransactionRepository;
    private final ItemRepository itemRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CardContractMapper cardContractMapper;
    private final CashContractMapper cashContractMapper;

    @Override
    @Transactional
    public CardTransaction createCardTransaction(CardContract contract) {
        CardTransaction cardTransaction = cardContractMapper.mapToCardTransaction(contract);

        updateBankAccountsAndItems(contract);

        return cardTransactionRepository.save(cardTransaction);
    }

    @Override
    @Transactional
    public CashTransaction createCashTransaction(CashContract contract) {
        CashTransaction cashTransaction = cashContractMapper.mapToCashTransaction(contract);

        updateBankAccountsAndItems(contract);

        return cashTransactionRepository.save(cashTransaction);
    }

    private void updateBankAccountsAndItems(CardContract contract) {
        var provBank = contract.getProvider().getBankAccount();
        var storeBank = contract.getStore().getBankAccount();

        provBank.setAmount(provBank.getAmount() - contract.getProduct().getPrice() * contract.getAmount());
        storeBank.setAmount(storeBank.getAmount() + contract.getProduct().getPrice() * contract.getAmount());

        bankAccountRepository.save(provBank);
        bankAccountRepository.save(storeBank);

        saveItems(contract.getProduct(), contract.getAmount(), contract.getStore());
    }

    private void updateBankAccountsAndItems(CashContract contract) {
        var storeBank = contract.getStore().getBankAccount();
        storeBank.setAmount(storeBank.getAmount() + contract.getProduct().getPrice() * contract.getAmount());

        bankAccountRepository.save(storeBank);

        saveItems(contract.getProduct(), contract.getAmount(), contract.getStore());
    }

    private void saveItems(Product product, int amount, Store store) {
        for (int i = 0; i < amount; i++) {
            Item temp = getItem(product, store);
            itemRepository.save(temp);
        }
    }

    private Item getItem(Product product, Store store) {
        Item temp = new Item();
        temp.setPrice(product.getPrice() * TAX);
        temp.setProdDate(LocalDate.now());
        temp.setExpDate(LocalDate.now().plusDays(EXP));
        temp.setStore(store);
        temp.setProduct(product);
        temp.setItemCondition(NEW);
        return temp;
    }
}