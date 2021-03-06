package com.andrey.application.view;


public class MainBaseViewImpl extends BaseView implements View {

    private final AccountBaseView accountView = new AccountBaseView();
    private final SpecialityBaseView specialityBaseView = new SpecialityBaseView();
    private final CustomerBaseView customerView = new CustomerBaseView();

    @Override
    public void startAction() {
        startMassage();
        start();
        viewHandler(this);
    }

    public void startMassage() {
        System.out.println("\t\t* * * Welcome to a new CRUD Application * * *");
        System.out.println("\nTo start the app write \"start\" and press enter");
    }

    @ConsoleBind(command = "start")
    public void start() {
        mainMenu();
    }

    public void mainMenu() {
        System.out.println("Command list below by using that you can run the app: \n");
        commands();
    }

    public void commands() {
        System.out.println("1 - customer manager");
        System.out.println("2 - account manager");
        System.out.println("3 - speciality manager");
        System.out.println();
        System.out.println("menu - to show menu");
        System.out.println("0 - exit application");
    }

    @ConsoleBind(command = "1")
    public void startCustomerManager() {
        customerView.startAction();
    }

    @ConsoleBind(command = "2")
    public void startAccountManager() {
        accountView.startAction();
    }

    @ConsoleBind(command = "3")
    public void startSpecialityManager() {
        specialityBaseView.startAction();
    }
}
