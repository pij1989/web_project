package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    boolean create(User user, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    String findPasswordByEmail(String email) throws DaoException;
}
