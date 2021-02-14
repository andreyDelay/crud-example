package com.andrey.application.view;

import com.andrey.application.controller.CustomerControllerImpl;
import com.andrey.application.repository.ProjectRepositoryFactory;
import com.andrey.application.util.ViewRequestUserForData;

public class CustomerBaseView extends BaseView implements View {

    private final CustomerControllerImpl customerController;

    public CustomerBaseView(ProjectRepositoryFactory repositoryFactory) {
        customerController = new CustomerControllerImpl(repositoryFactory.getCustomerRepository());
    }

    @Override
    public void startAction() {
        System.out.println("\t\t* * * Welcome to customer manager * * *\n");
        commands();
        viewHandler(this);
    }

    public void commands() {
        System.out.println("1 - add customer");
        System.out.println("2 - delete customer");
        System.out.println("3 - update customer");
        System.out.println("4 - get customer by id");
        System.out.println("5 - show all customers");
        System.out.println("6 - add speciality to customer");
        System.out.println();
        System.out.println("menu - to show menu");
        System.out.println("back - return to main menu");
        System.out.println("0 - exit application");
    }

    @ConsoleBind(command = "1")
    public void addCustomer() {
        String newCustomer = getInputDataForValidating(ViewRequestUserForData.ADD_NEW_CUSTOMER);
        printCustomer(customerController.save(newCustomer));
    }

    @ConsoleBind(command = "2")
    public void deleteCustomer() {
        String customerId = getInputDataForValidating(ViewRequestUserForData.DELETE_CUSTOMER);
        customerController.delete(customerId);
    }

    @ConsoleBind(command = "3")
    public void updateCustomer() {
        String newValue = getInputDataForValidating(ViewRequestUserForData.UPDATE_CUSTOMER);
        printCustomer(customerController.update(newValue));
    }

    @ConsoleBind(command = "4")
    public void getCustomerById() {
        String customerId = getInputDataForValidating(ViewRequestUserForData.ID);
        printCustomer(customerController.find(customerId));
    }

    @ConsoleBind(command = "5")
    public void showAllData() {
        printEntities(customerController.findAll());
        commands();
    }

    @ConsoleBind(command = "6")
    public void addSpecialityToCustomer() {
        String newSpeciality =
                getInputDataForValidating(ViewRequestUserForData.ADD_SPECIALITY_TO_CUSTOMER);
        printCustomer(customerController.addSpeciality(newSpeciality));
    }

}
