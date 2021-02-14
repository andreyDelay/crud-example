package com.andrey.application.validators;


public interface Validator {

    void validate(String inputData, ValidatingOption validatingOption) throws IllegalArgumentException;

    default boolean isStringEmpty(String inputData) {
        String result = inputData.trim().replace(" ", "");
        return result.length() == 0;
    }

    default boolean isStringContainsForbiddenChars(String inputData, String pattern) {
        int stringLengthBefore = inputData.length();
        String result = inputData.replace(pattern,"");
        int stringLengthAfter = result.length();

        return stringLengthBefore != stringLengthAfter;
    }

    default void validateId(String stringId) throws IllegalArgumentException {
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        if (id < 1)
            throw new IllegalArgumentException("Id cannot be less than 1!");
    }
}
