package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Token;

import java.util.Optional;

public interface TokenService {
    Optional<Token> confirmToken(String tokenValue) throws ServiceException;

    Optional<Token> findTokenByValue(String tokenValue) throws ServiceException;

    Optional<Token> findTokenByUserEmail(String email) throws ServiceException;
}
