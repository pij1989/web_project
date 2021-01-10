package com.pozharsky.dmitri.dao;

import com.pozharsky.dmitri.entity.User;
import com.pozharsky.dmitri.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByEmail(String email) throws DaoException;
}
