package com.andrey.application.controller;

import com.andrey.application.model.Speciality;
import com.andrey.application.repository.SpecialityRepository;
import com.andrey.application.service.SpecialityService;
import com.andrey.application.service.SpecialityServiceImpl;
import com.andrey.application.util.SpecialityBuilder;
import com.andrey.application.validators.SpecialityValidator;
import com.andrey.application.validators.ValidatingOption;

import java.util.List;

public class SpecialityControllerImpl {

    private SpecialityService specialityService;
    private SpecialityBuilder specialityBuilder = new SpecialityBuilder();

    public SpecialityControllerImpl(SpecialityRepository specialityRepository) {
        this.specialityService = new SpecialityServiceImpl(specialityRepository);
    }

    public boolean isDataValid(String data, ValidatingOption validatingOption) {
        try {
            SpecialityValidator specialityValidator = new SpecialityValidator();
            specialityValidator.validate(data, validatingOption);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Speciality save(String data) {
        if (isDataValid(data, ValidatingOption.SPECIALITY_NAME)) {
            Speciality speciality = specialityBuilder.buildSpeciality(data);
            return specialityService.save(speciality);
        }
        return null;
    }

    public Speciality update(String data) {
        if (isDataValid(data, ValidatingOption.SPECIALITY_ID_NAME)) {
            Speciality speciality = specialityBuilder.buildSpecialityForUpdate(data);
            return specialityService.update(speciality);
        }
        return null;
    }

    public void delete(String data) {
        if (isDataValid(data, ValidatingOption.ID)) {
            int specialityId = Integer.parseInt(data);
            specialityService.delete(specialityId);
        }
    }

    public Speciality find(String data) {
        Speciality speciality = null;
        if (isDataValid(data, ValidatingOption.ID)) {
            int specialityId = Integer.parseInt(data);
            speciality = specialityService.find(specialityId).orElse(null);
        }
        return speciality;
    }

    public List<Speciality> findAll() {
        return specialityService.findAll();
    }
}
