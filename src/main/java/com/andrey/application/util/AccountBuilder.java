package com.andrey.application.util;

import com.andrey.application.model.Account;
import com.andrey.application.model.AccountStatus;

public class AccountBuilder extends Builder {

    public Account buildAccount(String data) {
        Account account = new Account();
        if (!data.contains(",")) {
            String clearData = data.trim();
            account.setName(clearData);
        } else {
            String[] fields = data.split(",");
            String accountStatus = fields[1].trim();
            String accountName = fields[0].trim();

            setAccountStatus(account, accountStatus);
            account.setName(accountName);
        }
        return account;
    }

    public Account buildAccountForUpdate(String data) {
        Account account = new Account();
        String[] fields = data.split(",");
        int accountId = Integer.parseInt(fields[0].trim());
        String updatedField = fields[1].trim();

        account.setId(accountId);
        if (!(updatedField.equalsIgnoreCase("DELETED") ||
                updatedField.equalsIgnoreCase("ACTIVE") ||
                updatedField.equalsIgnoreCase("BANNED"))) {
            account.setName(updatedField);
        } else {
            account.setStatus(AccountStatus.valueOf(updatedField));
        }
        return account;
    }

    private void setAccountStatus(Account account, String status) {
        switch (status) {
            case "DELETED":
                account.setStatus(AccountStatus.DELETED);
                break;
            case "BANNED":
                account.setStatus(AccountStatus.BANNED);
                break;
            case "ACTIVE":
                account.setStatus(AccountStatus.ACTIVE);
                break;
        }
    }
}
