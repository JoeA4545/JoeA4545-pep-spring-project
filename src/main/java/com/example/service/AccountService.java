package com.example.service;

import java.util.*;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank())
            return null;
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return null;

        Account existing = accountRepository.findByUsername(account.getUsername());
        if (existing != null) {
            Account duplicate = new Account();
            duplicate.setAccountId(0);
            return duplicate;
        }

        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password))
            return account;
        return null;
    }


}
