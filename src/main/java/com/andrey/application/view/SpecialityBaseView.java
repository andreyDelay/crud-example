package com.andrey.application.view;

import com.andrey.application.controller.SpecialityControllerImpl;
import com.andrey.application.repository.ProjectRepositoryFactory;
import com.andrey.application.util.ViewRequestUserForData;

public class SpecialityBaseView extends BaseView implements View {

    private final SpecialityControllerImpl specialityController;

    public SpecialityBaseView(ProjectRepositoryFactory repositoryFactory) {
        this.specialityController = new SpecialityControllerImpl(repositoryFactory.getSpecialityRepository());
    }

    @Override
    public void startAction() {
        System.out.println("\t\t* * * Welcome to speciality manager * * *\n");
        commands();
        viewHandler(this);
    }

    public void commands() {
        System.out.println("1 - show all specialities");
        System.out.println("2 - add new speciality");
        System.out.println("3 - show speciality by id");
        System.out.println("4 - update speciality");
        System.out.println("5 - delete speciality");
        System.out.println("back - return to main menu");
        System.out.println("0 - exit application");
    }

    @ConsoleBind(command = "1")
    public void showAllSpecialities() {
        printEntities(specialityController.findAll());
        commands();
    }

    @ConsoleBind(command = "2")
    public void addNewSpeciality() {
        String newSpeciality = getInputDataForValidating(ViewRequestUserForData.NEW_SPECIALITY);
        printSpeciality(specialityController.save(newSpeciality));
    }

    @ConsoleBind(command = "3")
    public void showSpecialityById() {
        String specialityId = getInputDataForValidating(ViewRequestUserForData.ID);
        printSpeciality(specialityController.find(specialityId));
    }

    @ConsoleBind(command = "4")
    public void updateSpeciality() {
        String newSpecialityData = getInputDataForValidating(ViewRequestUserForData.UPDATE_SPECIALITY);
        printSpeciality(specialityController.update(newSpecialityData));
    }

    @ConsoleBind(command = "5")
    public void deleteSpeciality() {
        String specialityId = getInputDataForValidating(ViewRequestUserForData.ID);
        specialityController.delete(specialityId);
    }
}
