package com.andrey.application.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    T save(T objectToSave);

    T update(T newValue);

    void delete(int id);

    Optional<T> find(int id);

    List<T> findAll();
}
