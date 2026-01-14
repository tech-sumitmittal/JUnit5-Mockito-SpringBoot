package com.sumit.controller;

import com.sumit.entity.Account;
import com.sumit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }

    @PostMapping("/customer/{customerId}")
    public Account createAccount(@PathVariable Long customerId, @RequestBody Account account) {
        return accountService.createAccount(customerId, account);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

}