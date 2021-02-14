package com.andrey.application.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface View {

    void startAction();

    default void mainCommands() {
        System.out.println("1 - customer manager");
        System.out.println("2 - account manager");
        System.out.println("3 - speciality manager");
        System.out.println();
        System.out.println("menu - to show menu");
        System.out.println("0 - exit application");
    }

    default String backToMainMenu() {
            return "back";
    }

    default void back() {
        if (!(this instanceof MainBaseViewImpl)) {
            mainCommands();
        } else {
            System.out.println("You already in the main menu!");
        }
    }

    default void showMenu() {
        Class clazz = this.getClass();
        try {
            Method commands = clazz.getMethod("commands", null);
            commands.invoke(this, null);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println("Error. Menu cannot be shown, try later!");
        }
    }

    default String exit() {
        return "0";
    }

    default void stopApp() {
        System.exit(0);
    }

}
