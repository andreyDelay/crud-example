package com.andrey.application.service;

import com.andrey.application.model.Account;
import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.connection.ConnectionUtil;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountServiceImpl() {
        accountRepository = ConnectionUtil.defineRepository()
                            .getAccountRepository();
    }

    @Override
    public Account save(Account newAccount) {
        return accountRepository.save(newAccount);
    }

    @Override
    public Account update(Account updatedAccount) {
        return accountRepository.update(updatedAccount);
    }

    @Override
    public void delete(int id) {
        accountRepository.delete(id);
    }

    @Override
    public Optional<Account> find(int id) {
        return accountRepository.find(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
