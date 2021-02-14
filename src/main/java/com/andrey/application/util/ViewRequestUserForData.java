package com.andrey.application.util;

public enum ViewRequestUserForData {
    //This class used by UI, when user chose an option one of massages below will be shown
    //to user to let him know what to do next. These options specified in UI methods. ConsoleImpl class
    ID("Please, input id"),
    ADD_NEW_CUSTOMER("Please, input data separated by commons\nname,surname,age,account"),
    ADD_SPECIALITY_TO_CUSTOMER("Please, input customer id and name of speciality you want to add," +
                                                            "\ncustomer id,speciality name"),
    DELETE_CUSTOMER("Please, input id of customer you want to delete"),
    UPDATE_CUSTOMER("Please, input data separated by commons" +
                                                    "\nid,name,surname,age"),

    UPDATE_ACCOUNT("Please, input data separated by commons - account id and account name\n" +
            "id,name"),
    NEW_SPECIALITY("Please, input speciality name that you want to add!"),
    UPDATE_SPECIALITY("Please, input data separated by commons - speciality id and name\n" +
            "id,name");


    private String message;

    ViewRequestUserForData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
