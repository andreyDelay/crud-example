package com.andrey.application.validators;

public class CustomerValidator implements Validator {

    @Override
    public void validate(String inputData, ValidatingOption validatingOption) throws IllegalArgumentException {
        if (isStringEmpty(inputData))
            throw new IllegalArgumentException("Please, input required data for new customer!");

        switch (validatingOption) {
            case CUSTOMER_SAVE              -> validateSaveData(inputData);
            case CUSTOMER_UPDATE            -> validateUpdateData(inputData);
            case CUSTOMER_ADD_SPECIALITY    -> validateAddSpeciality(inputData);
            case ID                         -> validateId(inputData);
        }
    }

    private void validateSaveData(String data) throws IllegalArgumentException {
        String[] fields = data.split(",");
        validateFields(fields);

        AccountValidator accountValidator = new AccountValidator();

        String customerName = fields[0];
        String customerSurname = fields[1];
        if (isStringEmpty(customerName) || isStringEmpty(customerSurname)) {
            throw new IllegalArgumentException("Incorrect name or surname!");
        }

        String accountName = fields[3];
        String age = fields[2].trim();
        accountValidator.validate(accountName, ValidatingOption.ACCOUNT_NAME);
        validateAge(age);
    }

    private void validateFields(String [] fields) throws IllegalArgumentException {
        if (fields.length != 4) {
            throw new IllegalArgumentException("Not enough data for operation!");
        }
    }

    private void validateUpdateData(String data) throws IllegalArgumentException {
        String[] fields = data.split(",");
        validateFields(fields);

        String id = fields[0].trim();
        String name = fields[1];
        String surname = fields[2];
        String age = fields[3].trim();

        if (isStringEmpty(name) || isStringEmpty(surname)) {
            throw new IllegalArgumentException("Incorrect name or surname!");
        }
        validateAge(age);
        validateId(id);
    }

    private void validateAddSpeciality(String data) throws IllegalArgumentException {
        String[] fields = data.split(",");
        if (fields.length != 2)
            throw new IllegalArgumentException("Incorrect input data!");

        SpecialityValidator specialityValidator = new SpecialityValidator();
        String id = fields[0].trim();
        String specialityName = fields[1];
        validateId(id);
        specialityValidator.validate(specialityName, ValidatingOption.SPECIALITY_NAME);
    }

    private void validateAge(String age) throws IllegalArgumentException {
        try {
            int result = Integer.parseInt(age);
            if (result < 6) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age is not correct");
        }
    }

}
