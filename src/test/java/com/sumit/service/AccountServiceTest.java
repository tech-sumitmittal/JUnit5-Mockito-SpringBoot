package com.sumit.service;

import com.sumit.entity.Account;
import com.sumit.entity.Customer;
import com.sumit.repository.AccountRepository;
import com.sumit.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);

        account = new Account();
        account.setAccountNumber(10L);
        account.setAccountType("SAVINGS");
        account.setBranchAddress("London");
        account.setCustomer(customer);
    }

    // ================= getAllAccounts =================

    @Test
    void getAllAccounts_success() {
        when(accountRepository.findAll()).thenReturn(Collections.singletonList(account));

        List<Account> accounts = accountService.getAllAccounts();

        assertEquals(1, accounts.size());
        verify(accountRepository, times(1)).findAll();
    }

    // ================= getAccountById =================

    @Test
    void getAccountById_success() {
        when(accountRepository.findById(10L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(10L);

        assertNotNull(result);
        assertEquals("SAVINGS", result.getAccountType());
    }

    @Test
    void getAccountById_notFound() {
        // mocking the repository behaviour
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // execute lambda, catches service class exception
        RuntimeException ex = assertThrows(RuntimeException.class,     () -> accountService.getAccountById(99L));

        // assert (means verify the response)
        assertEquals("Account not found with id 99", ex.getMessage());
    }

    // ================= getAccountsByCustomerId =================

    @Test
    void getAccountsByCustomerId_success() {
        when(accountRepository.findByCustomerCustomerId(1L))
                .thenReturn(Collections.singletonList(account));

        List<Account> accounts = accountService.getAccountsByCustomerId(1L);

        assertEquals(1, accounts.size());
        Mockito.verify(accountRepository).findByCustomerCustomerId(1L);
    }

    // ================= createAccount =================

    @Test
    void createAccount_success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(1L, account);

        assertNotNull(result);
        assertNotNull(result.getAccountNumber());
        assertEquals(customer, result.getCustomer());
    }

    @Test
    void createAccount_customerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> accountService.createAccount(1L, account)
        );

        assertEquals("Customer not found with id 1", exception.getMessage());
    }

    // ================= updateAccount =================

    @Test
    void updateAccount_success() {
        Account updatedDetails = new Account();
        updatedDetails.setAccountType("CURRENT");
        updatedDetails.setBranchAddress("Manchester");

        when(accountRepository.findById(10L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.updateAccount(10L, updatedDetails);

        assertEquals("CURRENT", result.getAccountType());
        assertEquals("Manchester", result.getBranchAddress());
    }

    @Test
    void updateAccount_notFound() {
        when(accountRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> accountService.updateAccount(10L, account)
        );

        assertEquals("Account not found with id 10", exception.getMessage());
    }

    // ================= deleteAccount =================

    @Test
    void deleteAccount_success() {
        when(accountRepository.findById(10L)).thenReturn(Optional.of(account));
        doNothing().when(accountRepository).delete(account);

        accountService.deleteAccount(10L);

        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void deleteAccount_notFound() {
        when(accountRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> accountService.deleteAccount(10L)
        );

        assertEquals("Account not found with id 10", exception.getMessage());
    }
}