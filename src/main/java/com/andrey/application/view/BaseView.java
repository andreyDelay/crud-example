package com.andrey.application.view;

import com.andrey.application.model.Account;
import com.andrey.application.model.Customer;
import com.andrey.application.model.Speciality;
import com.andrey.application.util.ViewRequestUserForData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BaseView {

    private View view;
    private Scanner scanner;

    public void viewHandler(View view) {
        this.view = view;
        scanner = new Scanner(System.in);

        while (true) {
            System.out.print("~");
            String input = scanner.nextLine();

            if (input.equals(view.exit())) {
                stopApplication();
            } else if (input.equals(view.backToMainMenu())) {
                view.back();
                if (!(view instanceof MainBaseViewImpl)) {
                    return;
                } else {
                    ((MainBaseViewImpl) view).commands();
                }
            } else if (input.equalsIgnoreCase("menu")) {
                view.showMenu();
            }

            Class clazz = view.getClass();
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(ConsoleBind.class)) {
                    ConsoleBind annotation = method.getAnnotation(ConsoleBind.class);
                    String command = annotation.command();

                    if (command.equals(input)) {
                        try {
                            method.invoke(view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public String getInputDataForValidating(ViewRequestUserForData massageToUser) {
        System.out.println(massageToUser.getMessage());
        System.out.println("To stop the operation - input \"exit\"");

        while (true) {
            System.out.print("~");
            String data = scanner.nextLine();


            if (data.equals(view.exit())) {
                stopApplication();
            }

            if (isDataEmpty(data)) {
                System.out.println("Input data cannot be empty");
            }
            return data;
        }
    }

    public void printEntities(List<?> entityList) {
        if (entityList != null && entityList.size() != 0) {
            printSeparator();
            entityList.forEach(entity ->{
                    if (entity instanceof Customer) {
                        printCustomer(entity);
                    } else if (entity instanceof Speciality) {
                        printSpeciality(entity);
                    } else if (entity instanceof Account) {
                        printAccount(entity);
                    }
            });
            printSeparator();
        } else {
            System.out.println("There is no any data in DB!");
        }
    }

    public void printCustomer(Object entity) {
        Customer customer = (Customer) entity;
        if (customer != null) {
            printSeparator();
            System.out.println(customer.toString());
            Set<Speciality> specialitySet = customer.getSpecialities();
            printSpecialities(specialitySet);
        } else {
            System.out.println("Operation failed!");
        }
    }

    public void printAccount(Object entity) {
        Account account = (Account) entity;
        if (account != null) {
            System.out.println(account.toString());
        } else {
            System.out.println("Not found!");
        }
    }

    public void printSpeciality(Object entity) {
        Speciality speciality = (Speciality) entity;
        if (speciality != null) {
            System.out.println(speciality.toString());
        } else {
            System.out.println("Not found!");
        }
    }

    private void printSpecialities(Set<Speciality> specialitySet) {
        if (specialitySet != null && specialitySet.size() != 0) {
            System.out.println("Specialities: ");
            for (Speciality speciality : specialitySet) {
                System.out.println(speciality.toString());
            }
        } else {
            System.out.println("Current customer has no any specialities!");
        }
    }

    private void printSeparator() {
        System.out.println("============================================================");
    }

    private void stopApplication() {
        view.stopApp();
    }

    private boolean isDataEmpty(String data) {
        return data.length() == 0;
    }

}
