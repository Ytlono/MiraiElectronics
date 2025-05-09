package com.example.MiraiElectronics.service.Generic;

import java.util.List;

public interface IGenericEntityService<T, ID> {
    T save(T entity);
    T findById(ID id);
    void delete(T entity);
    List<T> findAll();
    void deleteById(ID id);
}

