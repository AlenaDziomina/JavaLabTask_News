package com.epam.testapp.dao;

import java.util.List;

public interface CommonDao<T, ID> {
    List<T> findAll();
    T findById(ID entityId);
    ID save(T entity);
    void update(T entity);
    void delete(List<ID> ids);
}
