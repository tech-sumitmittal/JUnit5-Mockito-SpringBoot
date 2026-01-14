package com.sumit.service;

import com.sumit.entity.Account;
import com.sumit.entity.Customer;
import com.sumit.repository.AccountRepository;
import com.sumit.repository.CustomerRepository;
import com.sumit.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + id));
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerCustomerId(customerId);
    }

    public Account createAccount(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));

        account.setCustomer(customer);
        account.setAccountNumber(AppUtils.generate10DigitId());
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + id));

        account.setAccountType(accountDetails.getAccountType());
        account.setBranchAddress(accountDetails.getBranchAddress());

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + id));
        accountRepository.delete(account);
    }

}