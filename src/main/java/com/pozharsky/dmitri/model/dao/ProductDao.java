package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Product;

public interface ProductDao extends BaseDao<Product> {
    boolean create(Product product) throws DaoException;
}
