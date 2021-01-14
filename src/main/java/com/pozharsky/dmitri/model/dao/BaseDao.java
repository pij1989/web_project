package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.model.entity.Entity;
import com.pozharsky.dmitri.exception.DaoException;

public interface BaseDao<T extends Entity> {
    boolean create(T entity) throws DaoException;
    T findById(long id);
}
