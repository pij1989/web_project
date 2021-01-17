package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByEmail(String email) throws DaoException;
    String findPasswordByEmail(String email) throws DaoException;
}
