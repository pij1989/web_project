package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Entity;

import java.util.List;

public interface BaseDao<T extends Entity> {
    boolean create(T entity) throws DaoException;

    T findById(long id) throws DaoException;

    List<T> findAll() throws DaoException;

    T update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;
}
