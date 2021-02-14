package com.andrey.application.controller;

import com.andrey.application.model.Account;
import com.andrey.application.model.AccountStatus;
import com.andrey.application.repository.AccountRepository;
import com.andrey.application.service.AccountService;
import com.andrey.application.service.AccountServiceImpl;
import com.andrey.application.util.AccountBuilder;
import com.andrey.application.validators.AccountValidator;
import com.andrey.application.validators.ValidatingOption;

import java.util.List;

public class AccountControllerImpl {

    private AccountService accountService;
    private final AccountBuilder accountBuilder = new AccountBuilder();

    public AccountControllerImpl(AccountRepository accountRepository) {
        accountService = new AccountServiceImpl(accountRepository);
    }

    public boolean isDataValid(String data, ValidatingOption validatingOption) throws IllegalArgumentException {
        try {
            AccountValidator accountValidator = new AccountValidator();
            accountValidator.validate(data, validatingOption);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //TODO не используется, потому что отдельно аккаунт не сохраняется
    public Account save(String data) {
        if (isDataValid(data,ValidatingOption.ACCOUNT_NAME)) {
            Account account = accountBuilder.buildAccount(data);
            return accountService.save(account);
        }
        return null;
    }

    public Account update(String data, AccountStatus newStatus) {
        String newData = data + "," + newStatus.name();
        if (isDataValid(newData,ValidatingOption.ACCOUNT_ID_NAME)) {
            Account newValue = accountBuilder.buildAccountForUpdate(newData);
            return accountService.update(newValue);
        }
        return null;
    }

    public Account update(String data) {
        if (isDataValid(data,ValidatingOption.ACCOUNT_ID_NAME)) {
            Account newValue = accountBuilder.buildAccountForUpdate(data);
            return accountService.update(newValue);
        }
        return null;
    }

    public void delete(String data) {
        if (isDataValid(data, ValidatingOption.ID)) {
            int accountId = Integer.parseInt(data.trim());
            accountService.delete(accountId);
        }
    }

    public Account find(String data) {
        Account account = null;
        if (isDataValid(data, ValidatingOption.ID)) {
            int accountId = Integer.parseInt(data.trim());
            account = accountService.find(accountId).orElse(null);
        }
        return account;
    }

    public List<Account> findAll() {
        return accountService.findAll();
    }

}
