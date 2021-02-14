package com.andrey.application.util;

import com.andrey.application.model.Speciality;

public class SpecialityBuilder extends Builder {

    public Speciality buildSpeciality(String data) {
        Speciality speciality = new Speciality();
        speciality.setName(data);
        return speciality;
    }

    public Speciality buildSpecialityForUpdate(String data) {
        Speciality speciality = new Speciality();

        String[] fields = data.split(",");
        int id = Integer.parseInt(fields[0].trim());
        String newName = fields[1];

        speciality.setId(id);
        speciality.setName(newName);
        return speciality;
    }

}
