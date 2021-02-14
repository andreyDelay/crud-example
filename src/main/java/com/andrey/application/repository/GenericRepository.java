package com.andrey.application.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {

    T save(T objectToSave);

    T update(T t);

    void delete(ID id);

    Optional<T> find(ID id);

    List<T> findAll();
}
