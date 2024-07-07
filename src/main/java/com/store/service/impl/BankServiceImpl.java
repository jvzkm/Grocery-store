package com.store.service.impl;

import com.store.dao.BankAccountRepository;
import com.store.exceptions.BankAccountNotFound;
import com.store.model.entity.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl {

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccount> getBankAllAccounts(){
        return bankAccountRepository.findAll();
    }
    public BankAccount getBankAllAccounts(int id){
        return bankAccountRepository.findById(id)
                .orElseThrow(BankAccountNotFound::new);
    }
    public BankAccount addBankAccount(BankAccount bankAccount){
        return  bankAccountRepository.save(bankAccount);
    }

    public void delete(int id) {
        bankAccountRepository
                .delete(bankAccountRepository
                        .findById(id).orElseThrow(BankAccountNotFound::new));
    }
}
