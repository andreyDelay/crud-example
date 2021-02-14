package com.andrey.application.validators;

public class SpecialityValidator implements Validator {

    @Override
    public void validate(String data, ValidatingOption validatingOption) throws IllegalArgumentException {
        if (isStringEmpty(data))
            throw new IllegalArgumentException("Incorrect speciality name!");

        switch (validatingOption) {
            case ACCOUNT_NAME       -> validateName(data);
            case ACCOUNT_ID_NAME    -> validateFields(data);
            case ID                 -> validateId(data);
        }
    }

    private void validateName(String data) throws IllegalArgumentException {
            if (data.length() > 128 ||
                data.replaceAll("[\\,\\.]","").length() == 0)
            {
                throw new IllegalArgumentException("Too long speciality name!");
            }
    }

    private void validateFields(String data) throws IllegalArgumentException {
        String[] fields = data.split(",");
        if (fields.length != 2)
            throw new IllegalArgumentException("Not enough parameters!");

        String specialityId = fields[0];
        String specialityName = fields[1];
        validateId(specialityId);
        validateName(specialityName);
    }

}
