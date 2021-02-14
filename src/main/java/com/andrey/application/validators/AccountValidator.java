package com.andrey.application.validators;

public class AccountValidator implements Validator {

    private String firstPattern = "[^a-zA-Zа-яА-Я0-1,]";
    private String secondPattern = "[^a-zA-Zа-яА-Я0-1]";

    @Override
    public void validate(String inputData, ValidatingOption validatingOption) throws IllegalArgumentException {
        if  (isStringEmpty(inputData))
            throw new IllegalArgumentException("Incorrect account input data!");

        switch (validatingOption) {
            case ACCOUNT_NAME       -> validateAccountName(inputData);
            case ACCOUNT_ID_NAME    -> validateAccountFields(inputData);
            case ID                 -> validateId(inputData);
        }
    }

    private void validateAccountName(String name) throws IllegalArgumentException {
        if (isStringContainsForbiddenChars(name, secondPattern) || isStringEmpty(name)) {
            throw new IllegalArgumentException("Account name contains forbidden characters!");
        }
    }

    private void validateAccountFields(String data) throws IllegalArgumentException {
        String[] fields = data.split(",");
        if (fields.length != 2) {
            throw new IllegalArgumentException("Not enough parameters!");
        }
        String accountId = fields[0].trim();
        String accountName = fields[1];

        validateId(accountId);
        validateAccountName(accountName);
    }

}
