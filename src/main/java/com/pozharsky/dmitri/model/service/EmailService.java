package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;

/**
 * Interface provides actions on email.
 *
 * @author Dmitri Pozharsky
 */
public interface EmailService {
    void sendActivationEmail(String sendToEmail, String token) throws ServiceException;

    void sendUnblockingEmail(String sendToEmail, String token) throws ServiceException;
}
