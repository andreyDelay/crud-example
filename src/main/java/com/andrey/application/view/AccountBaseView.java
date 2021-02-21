package com.andrey.application.view;

import com.andrey.application.controller.AccountControllerImpl;
import com.andrey.application.model.Account;
import com.andrey.application.model.AccountStatus;
import com.andrey.application.util.ViewRequestUserForData;

import java.util.List;
import java.util.stream.Collectors;

public class AccountBaseView extends BaseView implements View {

    private final AccountControllerImpl accountController = new AccountControllerImpl();


    @Override
    public void startAction() {
        System.out.println("\t\t* * * Welcome to account manager * * *\n");
        commands();
        viewHandler(this);
    }

    public void commands() {
        System.out.println("1 - show all accounts");
        System.out.println("2 - show active");
        System.out.println("3 - show banned");
        System.out.println("4 - show deleted");
        System.out.println("5 - mark account as deleted");
        System.out.println("6 - mark account as banned");
        System.out.println("7 - activate account");
        System.out.println("8 - show by id");
        System.out.println("9 - update account");
        System.out.println();
        System.out.println("menu - to show menu");
        System.out.println("back - return to main menu");
        System.out.println("0 - exit application");
    }

    @ConsoleBind(command = "1")
    public void showAllAccounts() {
        printEntities(accountController.findAll());
        commands();
    }

    @ConsoleBind(command = "2")
    public void showActive() {
        printEntities(sortAccountListByStatus(
                        accountController.findAll(),
                        AccountStatus.ACTIVE));
        commands();
    }

    @ConsoleBind(command = "3")
    public void showBanned() {
        printEntities(sortAccountListByStatus(
                accountController.findAll(),
                AccountStatus.BANNED));
        commands();
    }

    @ConsoleBind(command = "4")
    public void showDeleted() {
        printEntities(sortAccountListByStatus(
                accountController.findAll(),
                AccountStatus.DELETED));
        commands();
    }

    @ConsoleBind(command = "5")
    public void deleteAccount() {
        String accountId = getInputDataForValidating(ViewRequestUserForData.ID);
        printAccount(accountController.update(accountId, AccountStatus.DELETED));
    }

    @ConsoleBind(command = "6")
    public void banAccount() {
        String accountId = getInputDataForValidating(ViewRequestUserForData.ID);
        printAccount(accountController.update(accountId, AccountStatus.BANNED));
    }

    @ConsoleBind(command = "7")
    public void activateAccount() {
        String accountId = getInputDataForValidating(ViewRequestUserForData.ID);
        printAccount(accountController.update(accountId, AccountStatus.ACTIVE));
    }

    @ConsoleBind(command = "8")
    public void showById() {
        String accountId = getInputDataForValidating(ViewRequestUserForData.ID);
        printAccount(accountController.find(accountId));
    }

    @ConsoleBind(command = "9")
    public void updateAccount() {
        String newData = getInputDataForValidating(ViewRequestUserForData.UPDATE_ACCOUNT);
        printAccount(accountController.update(newData));
    }

    private List<Account> sortAccountListByStatus(List<Account> accountList, AccountStatus status) {
        return accountList.stream().filter(account -> account.getStatus().equals(status))
                                    .collect(Collectors.toList());
    }

}
