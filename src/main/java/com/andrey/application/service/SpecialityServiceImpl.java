package com.andrey.application.service;

import com.andrey.application.model.Speciality;
import com.andrey.application.repository.SpecialityRepository;
import com.andrey.application.repository.connection.ConnectionUtil;

import java.util.List;
import java.util.Optional;

public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public SpecialityServiceImpl() {
        this.specialityRepository = ConnectionUtil.defineRepository()
                                        .getSpecialityRepository();
    }

    @Override
    public Speciality save(Speciality newSpeciality) {
        return specialityRepository.save(newSpeciality);
    }

    @Override
    public Speciality update(Speciality newValue) {
        return specialityRepository.update(newValue);
    }

    @Override
    public void delete(int id) {
        specialityRepository.delete(id);
    }

    @Override
    public Optional<Speciality> find(int id) {
        return specialityRepository.find(id);
    }

    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }
}
