package com.store.rest.controllers;

import com.store.model.entity.BankAccount;
import com.store.service.impl.BankServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("conf-service/bank")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankServiceImpl bankService;

    @GetMapping
    public List<BankAccount> bankAccounts() {
        return bankService.getBankAllAccounts();
    }

    @GetMapping("/{id}")
    public BankAccount bankAccount(
            @PathVariable int id) {
        return bankService.getBankAllAccounts(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public BankAccount addNewBankAccount(
            @RequestBody BankAccount account) {
        return bankService.addBankAccount(account);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAccount(
            @PathVariable int id) {
        bankService.delete(id);
    }

}
