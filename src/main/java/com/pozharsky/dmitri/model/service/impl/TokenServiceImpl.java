package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.TokenDao;
import com.pozharsky.dmitri.model.dao.impl.TokenDaoImpl;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.service.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Optional;

public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LogManager.getLogger(TokenServiceImpl.class);
    private static final TokenServiceImpl instance = new TokenServiceImpl();

    public static TokenServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Token> confirmToken(String tokenValue) throws ServiceException {
        try {
            TokenDao tokenDao = TokenDaoImpl.getInstance();
            Optional<Token> optionalToken = tokenDao.findTokenByValue(tokenValue);
            if (optionalToken.isPresent()) {
                Token token = optionalToken.get();
                LocalDateTime timeExpire = token.getTimeExpire();
                LocalDateTime timeNow = LocalDateTime.now();
                return timeExpire.compareTo(timeNow) > 0 ? optionalToken : Optional.empty();
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Can not confirm token: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Token> findTokenByValue(String tokenValue) throws ServiceException {
        try {
            TokenDao tokenDao = TokenDaoImpl.getInstance();
            return tokenDao.findTokenByValue(tokenValue);
        } catch (DaoException e) {
            logger.error("Can not find token:" + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Token> findTokenByUserEmail(String email) throws ServiceException {
        try {
            TokenDao tokenDao = TokenDaoImpl.getInstance();
            return tokenDao.findTokenByUserEmail(email);
        } catch (DaoException e) {
            logger.error("Can not find token by user email:" + e);
            throw new ServiceException(e);
        }
    }
}
