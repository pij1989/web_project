package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Token;

import java.util.Optional;

public interface TokenDao extends BaseDao<Token> {
    Optional<Token> findTokenByValue(String tokenValue) throws DaoException;

    Optional<Token> findTokenByUserEmail(String email) throws DaoException;
}
