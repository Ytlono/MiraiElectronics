package com.example.MiraiElectronics.service.Generic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class GenericEntityService <T, ID> implements IGenericEntityService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public GenericEntityService (JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(ID id){
        repository.deleteById(id);
    }
}

